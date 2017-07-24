package com.test.gank.utils;

import android.text.TextUtils;

/**
 * Created by wenqin on 2017/7/22.
 */

public class StringUtils {

    //2017-07-24T12:13:11.280Z
    private static final String PUBLISH_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getDateText(long mills) {
        String date = TimeUtils.millis2String(mills, PUBLISH_FORMAT);
        date = date.replace(" ", "T") + ".280Z";
        return date;
    }

    public static long getDateTextMills(String date) {
        date = date.replace(" ", "T").substring(0, PUBLISH_FORMAT.length());
        return TimeUtils.string2Millis(date, PUBLISH_FORMAT);
    }

    public static String getDateWithoutTime(String dateStr) {
        if (!TextUtils.isEmpty(dateStr) && dateStr.contains("T")) {
            return dateStr.substring(0, dateStr.indexOf("T"));
        }
        return dateStr;
    }
}
