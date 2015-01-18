package com.codingyuseng.alarm.yusengalarm.common.model;

import java.sql.Time;

/**
 * Contains Alarm information
 */
public class Alarm {
    public int id;
    public String title;
    public Time startTime;
    public Time endTime;
    public int mode;
    public int day;
        /* 일요일 월요일 화요일 수요일 목요일 금요일 토요일 순서로,
        2비트로 저장. DayHelper의 도움을 받음
         */
}
