package com.test.gank.utils;

import android.app.Application;
import android.content.Context;

import com.test.gank.db.DaoMaster;
import com.test.gank.db.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by wenqin on 2017/7/22.
 */

public class App extends Application {

    private static Context sContext;

    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;


    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ?
                "gank-item-db-encrypted" : "gank-item-db");

        Database db = ENCRYPTED ?  helper.getEncryptedWritableDb("super-secret")
                : helper.getWritableDb();

        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static Context getContext() {
        return sContext;
    }
}
