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

    public static String getDateWithoutTime(String dateStr) {
        if (!TextUtils.isEmpty(dateStr) && dateStr.contains("T")) {
            return dateStr.substring(0, dateStr.indexOf("T"));
        }
        return dateStr;
    }
}
