package com.test.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.test.gank.ui.BaseActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by wenqin on 2017/7/22.
 */

public class BaseFragment extends RxFragment{

    private BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止getActivity为空的情况
        if (getActivity() instanceof  BaseActivity) {
            mActivity = (BaseActivity) getActivity();
        }
    }

    protected void switchFragment(Fragment fromFragment, Fragment toFragment) {
        FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .addToBackStack(null);
        if (!toFragment.isAdded()) {
            ft.hide(fromFragment)
                    .add(BaseActivity.CONTENT_ID, toFragment)
                    .commit();
        } else {
            ft.hide(fromFragment)
                    .show(toFragment)
                    .commit();
        }
    }

}
