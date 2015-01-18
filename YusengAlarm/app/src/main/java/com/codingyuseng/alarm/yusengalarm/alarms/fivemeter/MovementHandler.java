package com.codingyuseng.alarm.yusengalarm.alarms.fivemeter;

/**
 * Movement handler class
 */
public class MovementHandler {
    private static final float DISTANCE_PASS_METER = 5;
    private static final float DISPLACEMENT_PASS_METER = 1.5f;
    private float mPrevAccX = 0;
    private float mPrevAccY = 0;
    private float mPrevVelocX = 0;
    private float mPrevVelocY = 0;
    private float mDistanceX = 0;
    private float mDistanceY = 0;
    private float mDisplacementX = 0;
    private float mDisplacementY = 0;
    private float mDistance = 0;
    private float mDisplacement = 0;
    private long mPrevMillis;

    public MovementHandler () {
        mPrevMillis = System.currentTimeMillis();
    }

    public void reset() {
        mPrevMillis = System.currentTimeMillis();
        mPrevAccX = 0;
        mPrevAccY = 0;
        mPrevVelocX = 0;
        mPrevVelocY = 0;
        mDistanceX = 0;
        mDistanceY = 0;
        mDisplacementX = 0;
        mDisplacementY = 0;
        mDistance = 0;
        mDisplacement = 0;
    }

    public void updateMovement(float accX, float accY) {
        long currentMillis = System.currentTimeMillis();
        float deltaSecond = (currentMillis - mPrevMillis) / 1000;
        float currentVelocX = (accX - mPrevAccX) * deltaSecond;
        float currentVelocY = (accY - mPrevAccY) * deltaSecond;
        float distX = (currentVelocX - mPrevVelocX) * deltaSecond;
        float distY = (currentVelocY - mPrevVelocY) * deltaSecond;
        mPrevMillis = currentMillis;
        mPrevAccX = accX;
        mPrevAccY = accY;
        mPrevVelocX = currentVelocX;
        mPrevVelocY = currentVelocY;
        mDisplacementX += distX;
        mDisplacementY += distY;
        mDistanceX += Math.abs(distX);
        mDistanceY += Math.abs(distY);
        mDisplacement = (float) Math.sqrt(mDisplacementX*mDisplacementX + mDisplacementY*mDisplacementY);
        mDistance = (float) Math.sqrt(mDistanceX*mDistanceX + mDistanceY*mDistanceY);
    }

    public float getDisplacement() {
        return mDisplacement;
    }

    public float getDistance() {
        return mDistance;
    }

    public boolean isDisplacementPass() {
        return mDisplacement >= DISPLACEMENT_PASS_METER;
    }

    public boolean isDistancePass() {
        return mDistance >= DISTANCE_PASS_METER - DISPLACEMENT_PASS_METER;
    }
}
