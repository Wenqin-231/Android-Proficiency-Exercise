package com.test.gank.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.gank.R;
import com.test.gank.ui.base.LazyLoadFragment;
import com.test.gank.utils.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wenqin on 2017/7/22.
 */

public class ItemListFragment extends LazyLoadFragment implements HomeView {

    private static final String KEY_STATUS = "key_status";
    public static final int STATUS_ANDROID = 0;
    public static final int STATUS_IOS = 1;
    public static final int STATUS_WEB = 2;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    private int mStatus;

    private HomePresenter mHomePresenter;

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
    public void fetchData() {
        switch (mStatus) {
            case STATUS_ANDROID:
                mHomePresenter.requestAndroidData(10, 1);
                break;
            case STATUS_IOS:
                mHomePresenter.requestIOSData(10, 1);
                break;
            case STATUS_WEB:
                mHomePresenter.requestWebData(10, 1);
                break;
        }
    }

    @Override
    public void showLoadingView() {
        switch (mStatus) {
            case STATUS_ANDROID:

                break;
            case STATUS_IOS:

                break;
            case STATUS_WEB:

                break;
        }
    }

    @Override
    public void dismissLoadingView() {

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
    public void onAndroidRequestSuccess() {

    }

    @Override
    public void onAndroidRequestError() {

    }
}
