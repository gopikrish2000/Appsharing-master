package com.rbricks.appsharing.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.commonsware.cwac.cam2.util.Utils;
import com.rbricks.appsharing.concept.Application.AppSharingApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

import static android.text.format.DateFormat.format;

/**
 * Created by gopikrishna on 8/16/16.
 */
public class CommonUtils {

    public static Function<String, Boolean> isNullOrEmptyRx = s -> (s == null || s.trim().isEmpty());
    public static float ScaledDensityMultiplier, DensityMultiplier;
    public static int DensityDpi, DisplayWidthPixels, DisplayHeightPixels;
    public static final long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;

    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    public static boolean isNonNullOrEmpty(@Nullable CharSequence str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNullOrEmpty(@Nullable CharSequence str) {
        return (str == null || str.length() == 0 || str.toString().trim().length() == 0);
    }

    public static boolean isNullOrEmpty(@Nullable List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNullOrEmpty(String[] split) {
        return split == null || split.length == 0;
    }

    public static boolean equalsIgnoreCase(String first, String second) {
        if (isNullOrEmpty(first) || isNullOrEmpty(second)) {
            return false;
        }
        return first.equalsIgnoreCase(second);
    }

    public static String getNonNull(String input) {
        if (isNullOrEmpty(input)) {
            return "";
        }
        return input;
    }

    public static String ifNullReturn(String input, String defaultString) {
        if (isNullOrEmpty(input)) {
            return defaultString;
        }
        return input;
    }

    public static String getIntentValue(Intent intent, String key) {
        if (intent == null || intent.getExtras() == null) {
            return "";
        }
        Object object = intent.getExtras().get(key);
        if (object == null) {
            return "";
        }
        String result = String.valueOf(object);
        if (isNullOrEmpty(result)) {
            return "";
        }
        return result;
    }

    public static int getIntegerIntentValue(Intent intent, String key) {
        String strValue = getIntentValue(intent, key);
        try {
            return Integer.parseInt(strValue);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String getArgumentValue(Bundle bundle, String key) {
        if (bundle == null) {
            return "";
        }
        Object object = bundle.get(key);
        if (object == null) {
            return "";
        }
        String result = String.valueOf(object);
        if (isNullOrEmpty(result)) {
            return "";
        }
        return result;
    }

    public static Map<String, String> getNonEmptyIntentMap(Intent intent) {
        Map<String, String> map = new HashMap<>();
        if (intent == null || intent.getExtras() == null) {
            return map;
        }

        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()) {
            Object valueObj = bundle.get(key);
            if (valueObj != null) {
                String value = valueObj.toString();
                if (!isNullOrEmpty(value)) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }


    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static void setAllNull(Object... objects) {
        if (objects == null || objects.length == 0) {
            return;
        }
        for (int i = 0; i < objects.length; i++) {
            objects[i] = null;
        }
    }

    public static boolean isNonNullAll(Object... objects) {
        if (objects == null || objects.length == 0) {
            return false;
        }
        for (int i = 0; i < objects.length; i++) {
            if (isNull(objects[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullAny(Object... objects) {
        return !isNonNullAll(objects);
    }

    public static boolean valueInList(String input, String... strings) {
        if (isNullOrEmpty(input) || strings == null || strings.length == 0) {
            return false;
        }
        for (int i = 0; i < strings.length; i++) {
            if (input.equals(strings[i])) {
                return true;
            }
        }
        return false;
    }


    /*  Non Data input related Methods */

    public static void setDensityMultiplier(DisplayMetrics displayMetrics) {
        ScaledDensityMultiplier = displayMetrics.scaledDensity;
        DensityDpi = displayMetrics.densityDpi;
        DensityMultiplier = displayMetrics.density;
        DisplayWidthPixels = displayMetrics.widthPixels;
        DisplayHeightPixels = displayMetrics.heightPixels;
    }

    public static int dpToPx(float dp) {
        return (int) (dp * DensityMultiplier);
    }

    public static float pxToDp(int px) {
        return (px / DensityMultiplier);
    }

    public static int spToPx(float sp) {
        return (int) (sp * ScaledDensityMultiplier);
    }

    public static int getDeviceWidth() {
        return AppSharingApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getDeviceHeight() {
        return AppSharingApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDeviceDensityDPI() {
        return AppSharingApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().densityDpi;
    }

    public static void showToast(String msg) {
        Toast.makeText(AppSharingApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static String getCurrentDateTimeForDb() {
        Calendar.getInstance().setTimeZone(TimeZone.getTimeZone("IST"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        return dateFormat.format(date);
    }

    public static Date getDateTimeFromString(String dateString) {
        Calendar.getInstance().setTimeZone(TimeZone.getTimeZone("IST"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFormattedDateDifferenceFromNow(Date lastUpdatedDate) {
        String result = "";
        Date currentDate = Calendar.getInstance(TimeZone.getTimeZone("IST")).getTime();
        long diffInMillis = currentDate.getTime() - lastUpdatedDate.getTime();
        long days = diffInMillis / MILLISECS_PER_DAY;
        if (days == 0) {
            long hours = ((diffInMillis * 24) / MILLISECS_PER_DAY);
            if (hours == 0) {
                long min = ((diffInMillis * 24 * 60) / MILLISECS_PER_DAY);
                result = min + " min ago";
            } else {
                result = hours + " hours ago";
            }
        } else if (days > 0 && days < 30) {
            result = days + " days ago";
        } else {
            result = getFormattedDate(lastUpdatedDate);
        }
        return result;
    }

    public static void doUnsubscribe(Disposable lifeCycle) {
        if (lifeCycle == null || lifeCycle.isDisposed()) {
            return;
        }
        lifeCycle.dispose();
    }

    public static void dismissDialog(AlertDialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissDialog(DialogInterface dialog) {
        if (dialog == null) {
            return;
        }
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissDialog(ProgressDialog dialog) {
        if (dialog == null) {
            return;
        }
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getDPsFromPixels(int pixels) {
        /*
            public abstract Resources getResources ()
                Return a Resources instance for your application's package.
        */
        Context context = AppSharingApplication.getInstance();
        Resources r = context.getResources();
        int dps = Math.round(pixels / (r.getDisplayMetrics().densityDpi / 160f));
        return dps;
    }

    public static String getFormattedDate(Date date) {
        if (date == null) {
            return "";
        }
        return format("MMM dd", date).toString();
    }

}
