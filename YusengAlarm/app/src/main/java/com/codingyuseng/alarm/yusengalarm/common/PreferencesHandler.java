package com.codingyuseng.alarm.yusengalarm.common;

import android.content.Context;

public class PreferencesHandler {
    private static final String TAG = PreferencesHandler.class.getSimpleName();

    private static PreferencesHandler mInstance;

    private Context mContext;

    private PreferencesHandler() {
        mContext = YusengAlarmApp.getInstance().getContext();
    }

    public static PreferencesHandler getInstance() {
        if (mInstance == null) {
            mInstance = new PreferencesHandler();
        }
        return mInstance;
    }
}
