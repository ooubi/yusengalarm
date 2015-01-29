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


public class AlarmFivemeterMainActivity extends Activity implements SensorEventListener {
    private static final String MODE_LYING_TEXT = "Wake up!";
    private static final String MODE_WALKING_TEXT = "Let's stroll little";
    private static final String MODE_AWAKENED_TEXT = "Nice";
    private MovementCalculator mMovementCalculator;
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

        mMovementCalculator = new MovementCalculator();
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
        mSensorManager.registerListener(this, mAcceleration, SensorManager.SENSOR_DELAY_UI);
        mMovementCalculator.reset();
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
        mMovementCalculator.updateMovement(event.values);
        if (mMode.equals(Mode.LYING)
                && mMovementCalculator.isDisplacementPass()) {
            mMovementCalculator.reset();
            changeStatusToWalking();
        }
        if (mMode.equals(Mode.WALKING)) {
            mAlarmSwitchButton.setText(mMovementCalculator.getDistance() + " M");
            if (mMovementCalculator.isDistancePass()) {
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
        mStatusTextView.setText(MODE_LYING_TEXT);
        mAlarmSwitchButton.setActivated(false);
        mAlarmSwitchButton.setBackgroundColor(Color.GRAY);
    }

    private void changeStatusToWalking() {
        mMode = Mode.WALKING;
        mStatusTextView.setText(MODE_WALKING_TEXT);
        mAlarmSwitchButton.setActivated(true);
        mAlarmSwitchButton.setBackgroundColor(Color.RED);
    }

    private void changeStatusToAwakened() {
        mMode = Mode.AWAKENED;
        mStatusTextView.setText(MODE_AWAKENED_TEXT);
        mAlarmSwitchButton.setText(MovementCalculator.DISTANCE_PASS_METER + " M");
        mAlarmSwitchButton.setBackgroundColor(Color.GREEN);
    }
}
