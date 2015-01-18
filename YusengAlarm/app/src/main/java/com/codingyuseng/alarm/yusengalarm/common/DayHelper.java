package com.codingyuseng.alarm.yusengalarm.common;

/**
 * Dayhelper class. 일요일 월요일 화요일 수요일 목요일 금요일 토요일 순서로,
 * 2비트로 저장
 */
public class DayHelper {
    private static final String TAG = DayHelper.class.getSimpleName();

    public static final int SUN = 1;
    public static final int MON = 2;
    public static final int TUE = 4;
    public static final int WED = 8;
    public static final int THU = 16;
    public static final int FRI = 32;
    public static final int SAT = 64;
    public static final int[] DAY_OF_WEEK = {SUN, MON, TUE, WED, THU, FRI, SAT};

    private int mDays;

    /**
    * @param days 요일 정보를 binary로 가지고 있습니다.
    * ex) 월수금: 0101010 = 2+8+32, 월화수목금: 0111110 = 2+4+8+16+32
    */
    public DayHelper(int days) {
            mDays = days;
        }

    /**
    * 특정 요일을 가지고 있는지 체크합니다.
    * @param day 요일
    * @return 해당 요일이 포함되어 있으면 true, 포함되어 있지 않으면 false.
    */
    public boolean hasTheDay(int day) {
            return (mDays & day) == day;
        }

    /**
    * mDays를 String으로 받습니다.
    * @return mDays String.
    */
    public String getString() {
        StringBuilder sb = new StringBuilder(7);
        String[] kor_days = new String[] {"일", "월", "화", "수", "목", "금", "토"};
        for(int i = 0; i<7; i++) {
            if(hasTheDay(DAY_OF_WEEK[i])) {
                sb.append(kor_days[i] + " ");
            }
        }
        return sb.toString();
    }
}
