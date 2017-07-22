package com.test.gank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.test.gank.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by wenqin on 2017/7/22.
 */

public class BaseActivity extends RxAppCompatActivity {

    public static final int CONTENT_ID = R.id.content_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }

    protected void addFragment(Fragment fragment) {
        addFragment(fragment, CONTENT_ID, fragment.getClass().getSimpleName());
    }

    /**
     * 在Activity中构建一个Fragment
     */
    protected void addFragment(Fragment fragment, int fragmentId, String tag) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(fragmentId, fragment, tag);
            ft.commitAllowingStateLoss();
        }
    }
}
