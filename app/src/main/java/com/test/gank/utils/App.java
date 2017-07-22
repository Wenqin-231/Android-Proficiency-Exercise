package com.test.gank.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by wenqin on 2017/7/22.
 */

public class App extends Application{

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
