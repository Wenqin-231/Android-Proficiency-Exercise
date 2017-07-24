package com.test.gank.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.test.gank.R;
import com.test.gank.db.DaoSession;
import com.test.gank.model.GankItem;
import com.test.gank.ui.adapter.ItemListAdapter;
import com.test.gank.ui.adapter.base.OnChlidViewClickListener;
import com.test.gank.ui.baseview.LazyLoadFragment;
import com.test.gank.ui.baseview.WebFragment;
import com.test.gank.utils.App;
import com.test.gank.utils.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wenqin on 2017/7/22.
 * 列表Fragment
 */

public class ItemListFragment extends LazyLoadFragment implements HomeView,
        SwipeRefreshLayout.OnRefreshListener,
        OnChlidViewClickListener {

    private static final String KEY_TYPE = "key_status";
    private static final int PAGE_SIZE = 20;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.loading_view)
    RelativeLayout mLoadingView;

    private String mType; // 表示Tab类型
    private int mPageIndex = 1;

    private HomePresenter mHomePresenter;
    private List<GankItem> mItemList;
    private ItemListAdapter mListAdapter;

    private boolean isOnLoadMore = false;

    public static ItemListFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(KEY_TYPE, type);
        ItemListFragment fragment = new ItemListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(KEY_TYPE);
        }
        DaoSession daoSession = ((App) (mActivity.getApplication())).getDaoSession();
        mHomePresenter = new HomePresenterImpl(this, this, daoSession);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * LazyLoadFragment回调方法，把onActivityResult在这里处理
     */
    @Override
    public void fetchData() {
        initView();
        onRefresh();
    }

    private void initView() {
        mItemList = new ArrayList<>();
        mListAdapter = new ItemListAdapter(this, mActivity, mItemList);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new ListItemDecoration());
        mRecyclerView.setAdapter(mListAdapter);

        mRefreshLayout.setOnRefreshListener(this);
        mListAdapter.setOnChlidViewClickListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                // 滚动到底部
                if (lastVisibleItemPosition == mItemList.size() - 1) {

                    boolean isRefreshing = mRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        return;
                    }
                    // 不在下拉刷新状态的时候调用OnLoadMore
                    if (!isOnLoadMore) {
                        isOnLoadMore = true;
                        onLoadMoreData();
                    }

                }
            }
        });
    }

    private void requestData() {
        mHomePresenter.requestData(mType, PAGE_SIZE, mPageIndex);
    }

    @Override
    public void showLoadingView() {
        // 当数据为空并且不在下拉刷新的时候显示LoadingView
        if (mListAdapter.getItemCount() == 0 && !mRefreshLayout.isRefreshing()) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissLoadingView() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(App.getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestSuccess(List<GankItem> itemList) {
        isOnLoadMore = false;
        mRefreshLayout.setRefreshing(false);
        mHomePresenter.insertData(itemList);
        // updateView
        updateView(itemList);
        // add pageIndex when request success
        mPageIndex++;
    }


    private void updateView(List<GankItem> itemList) {
        // handle load more view
        if (itemList == null || itemList.isEmpty()) {
            // show no data when data is empty
            mListAdapter.showLoadMore(false);
            return;
        }
        mListAdapter.hideLoadMore();

        // onRefresh or first load
        int lastPosition = mItemList.size() - 1;
        if (mPageIndex == 1) {
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        if (mPageIndex == 1) {
            mListAdapter.notifyDataSetChanged();
        }
        mListAdapter.notifyItemChanged(lastPosition + 1, mItemList.size() - 1);
    }

    @Override
    public void onRequestError() {
        mRefreshLayout.setRefreshing(false);
        mListAdapter.showLoadMore(false);
        isOnLoadMore = false;
        // query data when load data fail
        // when query first page fail, query all database data
        GankItem gankItem = (mPageIndex == 1) ? null : mItemList.get(mItemList.size() - 2);
        mHomePresenter.queryDbData(mType, gankItem);
    }

    @Override
    public void onQueryData(List<GankItem> data) {
        updateView(data);
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        requestData();
    }

    private void onLoadMoreData() {
        mListAdapter.showLoadMore(true);
        requestData();
    }

    @Override
    public void onChildViewClick(View view, int position, int viewId) {
        // to WebView
        switchFragment(this, WebFragment.newInstance(mItemList.get(position).getUrl(),
                mItemList.get(position).getDesc()));
    }
}
