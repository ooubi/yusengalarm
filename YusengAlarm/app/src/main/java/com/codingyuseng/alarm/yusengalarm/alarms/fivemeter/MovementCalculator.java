package com.codingyuseng.alarm.yusengalarm.alarms.fivemeter;

/**
 * Movement handler class
 */
public class MovementCalculator {
    public static final float DISTANCE_PASS_METER = 5f;
    private static final float DISPLACEMENT_PASS_METER = 2.5f;
    private static final float DELTA_SECOND_LOWER_LIMIT = 0.001f;
    private static final int EVENT_VAR_NUM = 3;
    private float[] mPrevAccs;
    private float[] mPrevVelocs;
    private float[] mDistances;
    private float[] mDisplacements;
    private float mDistance;
    private float mDisplacement;
    private long mPrevMillis;

    public MovementCalculator() {
        reset();
    }

    public void reset() {
        mPrevMillis = System.currentTimeMillis();
        mPrevAccs = new float[EVENT_VAR_NUM];
        mPrevVelocs = new float[EVENT_VAR_NUM];
        mDistances = new float[EVENT_VAR_NUM];
        mDisplacements = new float[EVENT_VAR_NUM];
        mDistance = 0;
        mDisplacement = 0;
    }

    public void updateMovement(float[] accs) {
        long currentMillis = System.currentTimeMillis();
        float deltaSecond = (float) (currentMillis - mPrevMillis) / 1000;
        if(deltaSecond >= DELTA_SECOND_LOWER_LIMIT && accs.length >= EVENT_VAR_NUM) {
            mPrevMillis = currentMillis;
            setMovement(accs, deltaSecond);
        }
    }

    private void setMovement(float[] accs, float deltaSecond) {
        for(int i = 0; i < EVENT_VAR_NUM; i++) {
            float currentVeloc = ((accs[i] + mPrevAccs[i]) * deltaSecond) / 2;
            float currentDist = ((currentVeloc + mPrevVelocs[i]) * deltaSecond) / 2;
            mPrevAccs[i] = accs[i];
            mPrevVelocs[i] = currentVeloc;
            mDisplacements[i] += currentDist;
            mDistances[i] += Math.abs(currentDist);
        }
        mDisplacement = getFormatDiagonalLength(mDisplacements);
        mDistance = getFormatDiagonalLength(mDistances);
    }

    public float getDisplacement() { return mDisplacement; }

    public float getDistance() { return mDistance; }

    public boolean isDisplacementPass() {
        return mDisplacement >= DISPLACEMENT_PASS_METER;
    }

    public boolean isDistancePass() {
        return mDistance >= DISTANCE_PASS_METER;
    }

    private float getFormatDiagonalLength(float[] elements) {
        float length = 0.0f;
        for(float element : elements) {
            length += (element * element);
        }
        return ((int) (Math.sqrt(length) * 10)) / 10;
    }
}