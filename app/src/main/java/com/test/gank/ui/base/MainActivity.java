package com.test.gank.ui.base;


import android.os.Bundle;
import android.widget.Toast;

import com.test.gank.home.HomeFragment;
import com.test.gank.utils.TimeUtils;

public class MainActivity extends BaseActivity {

    private long mPressTimeMills = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(HomeFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        // 退回到主页面的时候去判断双击退出程序
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            onHomeBackPress();
        }
    }

    private void onHomeBackPress() {
        long nowTimeMills = TimeUtils.getNowMills();
        if (nowTimeMills - mPressTimeMills > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressTimeMills = nowTimeMills;
        } else {
            finish();
        }
    }
}
