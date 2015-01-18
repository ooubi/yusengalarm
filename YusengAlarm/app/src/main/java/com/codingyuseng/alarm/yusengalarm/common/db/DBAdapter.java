package com.codingyuseng.alarm.yusengalarm.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.codingyuseng.alarm.yusengalarm.common.YusengAlarmApp;
import com.codingyuseng.alarm.yusengalarm.common.model.Alarm;

import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.TABLE_ALARM;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_ID;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_TITLE;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_START_TIME;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_END_TIME;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_MODE;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_DAY;

/**
 * Adapter that controls db
 */
public class DBAdapter {
    private static final String TAG = DBAdapter.class.getSimpleName();

    private static DBAdapter mInstance;
    private Context mContext;
    private DBOpenHelper mDBOpenHelper;

    private DBAdapter() {
        mContext = YusengAlarmApp.getInstance().getContext();
        mDBOpenHelper = new DBOpenHelper(mContext);
    }

    public static DBAdapter getInstance() {
        if (mInstance == null) {
            mInstance = new DBAdapter();
        }
        return mInstance;
    }

    public void insertAlarm(Alarm alarm) {
        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        try {
            db.insert(TABLE_ALARM, null, getValuesFromAlarm(alarm));
        } catch (Exception e) {
            Log.e(TAG, "error while insert Schedule, : " + e.getMessage());
        } finally {
            db.close();
        }
    }

    private ContentValues getValuesFromAlarm(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put(ALARM_TITLE, alarm.title);
        values.put(ALARM_START_TIME, alarm.startTime.toString());
        values.put(ALARM_END_TIME, alarm.endTime.toString());
        values.put(ALARM_MODE, alarm.mode);
        values.put(ALARM_DAY, alarm.day);
        return values;
    }

    public List<Alarm> getAlarmList() {
        List<Alarm> scheduleList = new ArrayList<Alarm>();
        SQLiteDatabase db = mDBOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARM;
        Cursor c;
        try {
            c = db.rawQuery(query, null);
            while (c.moveToNext()) {
                scheduleList.add(getAlarmFromCursor(c));
            }
        } catch (Exception e) {
            Log.e(TAG, "error in pulling out alarms" + e.getMessage());
        } finally {
            db.close();
        }
        return scheduleList;
    }

    private Alarm getAlarmFromCursor(Cursor c) {
        Alarm alarm = new Alarm();
        alarm.id = c.getInt(c.getColumnIndex(ALARM_ID));
        alarm.title = c.getString(c.getColumnIndex(ALARM_TITLE));
        alarm.startTime = Time.valueOf(c.getString(c.getColumnIndex(ALARM_START_TIME)));
        alarm.endTime = Time.valueOf(c.getString(c.getColumnIndex(ALARM_END_TIME)));
        alarm.mode = c.getInt(c.getColumnIndex(ALARM_MODE));
        alarm.day = c.getInt(c.getColumnIndex(ALARM_DAY));
        return alarm;
    }

    public void deleteAlarm(Alarm alarm) {
        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ALARM +
                " WHERE id = " + alarm.id;
        try {
            db.execSQL(query);
        } catch (Exception e) {
            Log.e(TAG, "error in deleting alarm" + e.getMessage());
        } finally {
            db.close();
        }
    }
}

