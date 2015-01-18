package com.codingyuseng.alarm.yusengalarm.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.DBName;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.DBVersion;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.TABLE_ALARM;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_ID;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_TITLE;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_START_TIME;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_END_TIME;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_MODE;
import static com.codingyuseng.alarm.yusengalarm.common.db.DBConstant.ALARM_DAY;

/**
 * helper that opens db
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createScheduleTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // empty
    }

    private void createScheduleTable(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder(300);
        sb.append("CREATE TABLE " + TABLE_ALARM)
                .append(" ( ")
                .append(ALARM_ID + " Integer Primary Key Autoincrement, ")
                .append(ALARM_TITLE + " Text NOT NULL, ")
                .append(ALARM_START_TIME + " Time NOT NULL, ")
                .append(ALARM_END_TIME + " Time NOT NULL, ")
                .append(ALARM_MODE + " Integer NOT NULL, ")
                .append(ALARM_DAY + " Integer NOT NULL")
                .append(" );");
        db.execSQL(sb.toString());
    }
}
