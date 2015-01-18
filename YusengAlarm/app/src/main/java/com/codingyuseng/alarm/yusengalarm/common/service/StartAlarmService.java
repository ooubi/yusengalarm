package com.codingyuseng.alarm.yusengalarm.common.service;

import android.app.IntentService;
import android.content.Intent;

import com.codingyuseng.alarm.yusengalarm.alarms.arcade.AlarmArcadeMainActivity;
import com.codingyuseng.alarm.yusengalarm.alarms.fivemeter.AlarmFivemeterMainActivity;
import com.codingyuseng.alarm.yusengalarm.alarms.math.AlarmMathMainActivity;
import com.codingyuseng.alarm.yusengalarm.alarms.mosquito.AlarmMosquitoMainActivity;
import com.codingyuseng.alarm.yusengalarm.alarms.ordinary.AlarmOrdinaryMainActivity;
import com.codingyuseng.alarm.yusengalarm.alarms.pattern.AlarmPatternMainActivity;
import com.codingyuseng.alarm.yusengalarm.alarms.shake.AlarmShakeMainActivity;
import com.codingyuseng.alarm.yusengalarm.alarms.social.AlarmSocialMainActivity;
import com.codingyuseng.alarm.yusengalarm.common.YusengAlarmApp;
import com.codingyuseng.alarm.yusengalarm.common.model.AlarmMode;

public class StartAlarmService extends IntentService {
    public static final String INTENT_KEY_MODE = "mode";

    public StartAlarmService() {
        super("StartAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.hasExtra(StartAlarmService.INTENT_KEY_MODE)) {
            int mode = intent.getIntExtra(StartAlarmService.INTENT_KEY_MODE, AlarmMode.ORDINARY);
            Intent alarmIntent = getAlarmIntentFromMode(mode);
            startActivity(alarmIntent);
        }
    }

    private Intent getAlarmIntentFromMode(int mode) {
        switch(mode) {
            case AlarmMode.ARCADE:
                return new Intent(YusengAlarmApp.getInstance().getContext(),
                        AlarmArcadeMainActivity.class);
            case AlarmMode.FIVEMETER:
                return new Intent(YusengAlarmApp.getInstance().getContext(),
                        AlarmFivemeterMainActivity.class);
            case AlarmMode.MATH:
                return new Intent(YusengAlarmApp.getInstance().getContext(),
                        AlarmMathMainActivity.class);
            case AlarmMode.MOSQUITO:
                return new Intent(YusengAlarmApp.getInstance().getContext(),
                        AlarmMosquitoMainActivity.class);
            case AlarmMode.ORDINARY:
                return new Intent(YusengAlarmApp.getInstance().getContext(),
                        AlarmOrdinaryMainActivity.class);
            case AlarmMode.PATTERN:
                return new Intent(YusengAlarmApp.getInstance().getContext(),
                        AlarmPatternMainActivity.class);
            case AlarmMode.SHAKE:
                return new Intent(YusengAlarmApp.getInstance().getContext(),
                        AlarmShakeMainActivity.class);
            case AlarmMode.SOCIAL:
                return new Intent(YusengAlarmApp.getInstance().getContext(),
                        AlarmSocialMainActivity.class);
            default:
                throw new RuntimeException("Undefined Mode : " + mode);
        }
    }
}
