package com.rbricks.appsharing.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import rx.functions.Func1;

/**
 * Created by gopikrishna on 8/16/16.
 */
public class CommonUtils {

    public static Func1<String, Boolean> isNullOrEmptyRx = s -> (s == null || s.trim().isEmpty());

    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    public static String getCurrentDateTimeForDb() {
        Calendar.getInstance().setTimeZone(TimeZone.getTimeZone("IST"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        return dateFormat.format(date);
    }
}
