package com.codingyuseng.alarm.yusengalarm.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.codingyuseng.alarm.yusengalarm.common.model.AlarmMode;
import com.codingyuseng.alarm.yusengalarm.common.service.RegisterAlarmService;
import com.codingyuseng.alarm.yusengalarm.common.service.StartAlarmService;

public class StartAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra(StartAlarmService.INTENT_KEY_MODE)) {
            int mode = intent.getIntExtra(StartAlarmService.INTENT_KEY_MODE, AlarmMode.ORDINARY);
            Intent startAlarm = new Intent(context, StartAlarmService.class);
            startAlarm.putExtra(StartAlarmService.INTENT_KEY_MODE, mode);
            context.startService(startAlarm);
        }
    }
}
