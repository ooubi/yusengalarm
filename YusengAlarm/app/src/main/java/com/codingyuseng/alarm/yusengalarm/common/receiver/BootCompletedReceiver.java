package com.codingyuseng.alarm.yusengalarm.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.codingyuseng.alarm.yusengalarm.common.service.RegisterAlarmService;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent registerAlarm = new Intent(context, RegisterAlarmService.class);
        context.startService(registerAlarm);
    }
}
