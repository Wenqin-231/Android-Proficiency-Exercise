package com.test.gank.utils;

import android.text.TextUtils;

/**
 * Created by wenqin on 2017/7/22.
 */

public class StringUtils {

    public static String getDateText(String date) {
        if (!TextUtils.isEmpty(date) && date.length() >= 20) {
            return date.substring(0, 19).replace("T", " ");
        }
        return date;
    }
}
