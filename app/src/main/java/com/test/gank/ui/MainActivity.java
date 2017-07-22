package com.test.gank.ui;


import android.os.Bundle;

import com.test.gank.home.HomeFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(HomeFragment.newInstance());
    }
}
