package com.codingyuseng.alarm.yusengalarm.alarms.fivemeter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codingyuseng.alarm.yusengalarm.R;

//implement step counter alarm, for 4.4 android
public class AlarmFivemeterMainActivity extends Activity implements SensorEventListener {
    private MovementHandler mMovementHandler;
    private SensorManager mSensorManager;
    private Sensor mAcceleration;
    private TextView mStatusTextView;
    private Button mAlarmSwitchButton;
    private Mode mMode = Mode.LYING;;
    private enum Mode {
        LYING, WALKING, AWAKENED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_fivemeter_main);

        mMovementHandler = new MovementHandler();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        mStatusTextView = (TextView) this.findViewById(R.id.fivemeter_status);
        mAlarmSwitchButton = (Button) this.findViewById(R.id.fivemeter_alarm_switch);

        mAlarmSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAlarmSwitchClicked();
            }
        });

        changeModeToLying();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAcceleration, SensorManager.SENSOR_DELAY_FASTEST);
        mMovementHandler.reset();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        if(!mMode.equals(Mode.AWAKENED)) {
            fireAlarm();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mMovementHandler.updateMovement(event.values[0], event.values[1]);
        if (mMode.equals(Mode.LYING)
                && mMovementHandler.isDisplacementPass()) {
            changeStatusToWalking();
        }
        if (mMode.equals(Mode.WALKING)) {
            mAlarmSwitchButton.setText(mMovementHandler.getDistance() + " M");
            if (mMovementHandler.isDistancePass()) {
                changeStatusToAwakened();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    private void fireAlarm() {
        Intent intent = new Intent(this, AlarmFivemeterMainActivity.class);
        startActivity(intent);
    }

    private void handleAlarmSwitchClicked() {
        if(mMode.equals(Mode.AWAKENED)) {
            //turn off alarm
            finish();
        }
    }

    private void changeModeToLying() {
        mMode = Mode.LYING;
        mAlarmSwitchButton.setEnabled(false);
        mAlarmSwitchButton.setBackgroundColor(Color.GRAY);
    }

    private void changeStatusToWalking() {
        mMode = Mode.WALKING;
        mAlarmSwitchButton.setEnabled(true);
        mAlarmSwitchButton.setBackgroundColor(Color.RED);
    }

    private void changeStatusToAwakened() {
        mMode = Mode.AWAKENED;
        mAlarmSwitchButton.setBackgroundColor(Color.GREEN);
    }
}
