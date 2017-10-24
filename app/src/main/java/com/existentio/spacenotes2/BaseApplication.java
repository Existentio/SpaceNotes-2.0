package com.existentio.spacenotes2;

import android.app.Application;

/**
 * Created by Георгий on 20.09.2017.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        instance = ((BaseApplication) getApplicationContext());

    }

}