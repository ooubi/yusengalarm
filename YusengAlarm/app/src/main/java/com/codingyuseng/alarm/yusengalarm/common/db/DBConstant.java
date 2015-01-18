package com.codingyuseng.alarm.yusengalarm.common.db;

/**
 * DB에 사용되는 constant들을 저장하는 클래스입니다
 */
public final class DBConstant {

    static final String DBName = "yusengalarm.db";
    static final int DBVersion = 1;

    //    TABLE
    static final String TABLE_ALARM = "alarm";

    //    SCHEDULE
    static final String ALARM_ID = "id";
    static final String ALARM_TITLE = "title";
    static final String ALARM_START_TIME = "start_time";
    static final String ALARM_END_TIME = "end_time";
    static final String ALARM_MODE = "mode";
    static final String ALARM_DAY = "day";

}
