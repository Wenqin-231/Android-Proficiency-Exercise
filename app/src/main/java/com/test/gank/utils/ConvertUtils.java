package com.test.gank.utils;

/**
 * Created by wenqin on 2017/7/23.
 * 转换工具类
 */

public class ConvertUtils {

    public static int dp2px(final float dpValue) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
