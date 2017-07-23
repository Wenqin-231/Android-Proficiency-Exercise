package com.test.gank.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.gank.R;
import com.test.gank.model.GankItem;
import com.test.gank.ui.adapter.ItemListAdapter;
import com.test.gank.ui.adapter.base.OnChlidViewClickListener;
import com.test.gank.ui.baseview.BaseFragment;
import com.test.gank.ui.baseview.WebFragment;
import com.test.gank.utils.App;
import com.test.gank.utils.SoftInputUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wenqin on 2017/7/23.
 */

public class SearchFragment extends BaseFragment implements Toolbar.OnMenuItemClickListener, SearchView, OnChlidViewClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tips_text)
    TextView mTipsText;
    @BindView(R.id.loading_view)
    RelativeLayout mLoadingView;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;

    private ItemListAdapter mListAdapter;
    private List<GankItem> mItemList;
    private int pageIndex = 1;
    private boolean isOnLoadMore = false;
    private SearchPresenter mSearchPresenter;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchPresenter = new SearchPresenterImpl(this, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mItemList = new ArrayList<>();
        mListAdapter = new ItemListAdapter(this, mActivity, mItemList);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);

        mToolbar.inflateMenu(R.menu.menu_home);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        mToolbar.setOnMenuItemClickListener(this);

        showDefaultView();
        mSearchEdit.requestFocus();
        SoftInputUtils.showSoftInput(mActivity);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == mItemList.size() - 1) {

                    if (!isOnLoadMore) {
                        isOnLoadMore = true;
                        onLoadMoreData();
                    }

                }
            }
        });
        mListAdapter.setOnChlidViewClickListener(this);

    }

    private void onLoadMoreData() {
        mSearchPresenter.search(mSearchEdit.getText().toString(), 20, pageIndex);
    }


    private void showDefaultView() {
        mProgressBar.setVisibility(View.GONE);
        mTipsText.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
        mTipsText.setText("请搜索");
    }

    private void showListView() {
        mLoadingView.setVisibility(View.GONE);
    }

    private void showNoDataView() {
        mProgressBar.setVisibility(View.GONE);
        mTipsText.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
        mTipsText.setText("暂无数据，换个关键字看看");
    }

    @Override
    public void showLoadingView() {
        if (pageIndex == 1) {
            mProgressBar.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.VISIBLE);
            mTipsText.setVisibility(View.GONE);
        } else {
            mListAdapter.showLoadMore(true);
        }
    }

    @Override
    public void dismissLoadingView() {
        showListView();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(App.getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SoftInputUtils.hideSoftInput(mActivity);
        unbinder.unbind();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                toSearch(mSearchEdit.getText().toString());
                SoftInputUtils.hideSoftInput(mActivity);
                break;
        }
        return false;
    }

    private void toSearch(String s) {
        pageIndex = 1;
        mSearchPresenter.search(s, 20, pageIndex);
    }

    @Override
    public void onRequestSuccess(List<GankItem> data) {
        isOnLoadMore = false;
        if (data == null || data.isEmpty()) {
            if (pageIndex == 1) {
                showNoDataView();
            } else {
                // show no more data
                mListAdapter.showLoadMore(false);
            }
            return;
        }
        mListAdapter.hideLoadMore();

        showListView();

        // updateView
        int lastPosition = mItemList.size() - 1;
        // onRefresh or first load
        if (pageIndex == 1) {
            mItemList.clear();
        }
        mItemList.addAll(data);
        if (lastPosition < 0) {
            lastPosition = 0;
        }
        mListAdapter.notifyItemRangeInserted(lastPosition + 1, mItemList.size() - 1);

        pageIndex++;
    }

    @Override
    public void onRequestError() {
        isOnLoadMore = false;
        Toast.makeText(getContext(), "网络访问异常", Toast.LENGTH_SHORT).show();
        mListAdapter.hideLoadMore();
    }

    @Override
    public void onChildViewClick(View view, int position, int viewId) {
        // to WebView
        switchFragment(this, WebFragment.newInstance(mItemList.get(position).getUrl(),
                mItemList.get(position).getDesc()));
    }
}
