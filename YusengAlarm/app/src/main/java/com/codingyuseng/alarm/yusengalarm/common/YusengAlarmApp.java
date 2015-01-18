package com.codingyuseng.alarm.yusengalarm.common;

import android.app.Application;
import android.content.Context;

/**
 * Application class for implementing singleton design.
 */
public class YusengAlarmApp extends Application {
    private static final String TAG = YusengAlarmApp.class.getSimpleName();
    private static YusengAlarmApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static YusengAlarmApp getInstance() {
        return mInstance;
    }

    public Context getContext() {
        return super.getApplicationContext();
    }
    }
