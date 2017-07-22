package com.test.gank.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.gank.R;
import com.test.gank.ui.base.LazyLoadFragment;
import com.test.gank.utils.App;

/**
 * Created by wenqin on 2017/7/22.
 */

public class ItemListFragment extends LazyLoadFragment implements HomeView{

    private static final String KEY_STATUS = "key_status";
    public static final int STATUS_ANDROID = 0;
    public static final int STATUS_IOS = 1;
    public static final int STATUS_WEB = 2;

    private int mStatus;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void fetchData() {
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
}
