package com.test.gank.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.test.gank.R;
import com.test.gank.model.GankItem;
import com.test.gank.ui.adapter.ItemListAdapter;
import com.test.gank.ui.adapter.base.MultiItemTypeAdapter;
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
        SwipeRefreshLayout.OnRefreshListener, MultiItemTypeAdapter.OnItemClickListener,
        OnChlidViewClickListener {

    private static final String KEY_STATUS = "key_status";
    private static final int PAGE_SIZE = 20;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.loading_view)
    LinearLayout mLoadingView;

    private int mStatus;
    private int mPageIndex = 1;

    private HomePresenter mHomePresenter;
    private List<GankItem> mItemList;
    private ItemListAdapter mListAdapter;

    private boolean isOnLoadMore = false;

    public static ItemListFragment newInstance(int status) {

        Bundle args = new Bundle();
        args.putInt(KEY_STATUS, status);
        ItemListFragment fragment = new ItemListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStatus = getArguments().getInt(KEY_STATUS);
        }
        mHomePresenter = new HomePresenterImpl(this, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        mItemList = new ArrayList<>();
        mListAdapter = new ItemListAdapter(this, mActivity, mItemList);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new ListItemDecoration());
        mRecyclerView.setAdapter(mListAdapter);

        mRefreshLayout.setOnRefreshListener(this);
//        mListAdapter.setOnItemClickListener(this);
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
                if (lastVisibleItemPosition == mItemList.size() - 1) {

                    boolean isRefreshing = mRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        return;
                    }
                    if (!isOnLoadMore) {
                        isOnLoadMore = true;
                        onLoadMoreData();
                    }

                }
            }
        });
    }

    @Override
    public void fetchData() {
        initView();
        onRefresh();
    }

    private void requestData() {
        mHomePresenter.requestData(HomeFragment.TAB_TITLES[mStatus], PAGE_SIZE, mPageIndex);
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
        if (itemList == null || itemList.isEmpty()) {
            mListAdapter.setLoadMoreEnable(false);
            return;
        }
        mListAdapter.hideLoadMore();

        // updateView
        int lastPosition = mItemList.size() - 1;
        // onRefresh or first load
        if (mPageIndex == 1) {
            mItemList.clear();
        }
        mItemList.addAll(itemList);
        if (lastPosition < 0) {
            lastPosition = 0;
        }
        mListAdapter.notifyItemRangeInserted(lastPosition + 1, mItemList.size() - 1);

        // add pageIndex when request success
        mPageIndex++;
    }

    @Override
    public void onRequestError() {
        mRefreshLayout.setRefreshing(false);
        mListAdapter.setLoadMoreEnable(false);
        isOnLoadMore = false;
    }

    @Override
    public void onRefresh() {
        mPageIndex = 1;
        requestData();
    }

    private void onLoadMoreData() {
        mListAdapter.setLoadMoreEnable(true);
        requestData();
    }


    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        // to WebView
        switchFragment(this, WebFragment.newInstance(mItemList.get(position).getUrl(),
                mItemList.get(position).getDesc()));
    }

    @Override
    public void onChildViewClick(View view, int position, int viewId) {
        // to WebView
        switchFragment(this, WebFragment.newInstance(mItemList.get(position).getUrl(),
                mItemList.get(position).getDesc()));
    }
}
