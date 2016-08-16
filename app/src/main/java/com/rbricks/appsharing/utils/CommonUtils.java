package com.rbricks.appsharing.utils;

import rx.functions.Func1;

/**
 * Created by gopikrishna on 8/16/16.
 */
public class CommonUtils {

    public static Func1<String, Boolean> isNullOrEmptyRx = s -> (s == null || s.trim().isEmpty());

    public static boolean isNullOrEmpty(String s){
        return (s == null || s.trim().isEmpty());
    }
}
