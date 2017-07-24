package com.test.gank.db;

import android.text.TextUtils;

import com.test.gank.utils.StringUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by wenqin on 2017/7/24.
 */

public class DateConverter implements PropertyConverter<String, Long> {
    @Override
    public String convertToEntityProperty(Long databaseValue) {
        if (databaseValue == null || databaseValue <= 0) {
            return null;
        } else {
            return StringUtils.getDateText(databaseValue);
        }
    }

    @Override
    public Long convertToDatabaseValue(String entityProperty) {
        if (TextUtils.isEmpty(entityProperty)) {
            return null;
        } else {
            return StringUtils.getDateTextMills(entityProperty);
        }
    }
}
