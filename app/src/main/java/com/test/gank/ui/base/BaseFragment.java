package com.test.gank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by wenqin on 2017/7/22.
 */

public class BaseFragment extends RxFragment {

    protected BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止getActivity为空的情况
        if (getActivity() instanceof BaseActivity) {
            mActivity = (BaseActivity) getActivity();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    protected void addFragment(Fragment fragment, int fragmentId) {
        addFragment(fragment, fragmentId, fragment.getClass().getSimpleName());
    }

    protected void switchFragment(Fragment fromFragment, Fragment toFragment, int fragmentId) {
        FragmentTransaction ft = getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .addToBackStack(null);
        if (!toFragment.isAdded()) {
            ft.hide(fromFragment)
                    .add(fragmentId, toFragment)
                    .commit();
        } else {
            ft.hide(fromFragment)
                    .show(toFragment)
                    .commit();
        }
    }

    protected void addFragment(Fragment fragment, int fragmentId, String tag) {
        Fragment f = getFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(fragmentId, fragment, tag);
            ft.commitAllowingStateLoss();
        }
    }

}
