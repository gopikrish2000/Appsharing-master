package com.rbricks.appsharing.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;

import com.rbricks.appsharing.concept.Application.AppSharingApplication;

/**
 * Created by gopi on 05/05/17.
 */

public class AppUtils {

    public static String getPhoneMake() {
        String info = Build.MANUFACTURER + " | " + Build.VERSION.SDK_INT;
        try {
            Context context = AppSharingApplication.getInstance().getBaseContext();
            final PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            info = info + " | " + packageInfo.versionName;
            info = info + " | " + packageInfo.versionCode + "";


            final PackageInfo googlePlayServicePackageInfo = context.getPackageManager().getPackageInfo("com.google.android.gms", 0);
            info = info + " | gps- " + googlePlayServicePackageInfo.versionName + " : " + googlePlayServicePackageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;

    }

    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }

        return app_installed;
    }

    public static double getFreeSpace() {
        double sdAvailSize = 0.0;
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                sdAvailSize = (double) stat.getAvailableBlocksLong() * (double) stat.getBlockSizeLong();
            } else {
                sdAvailSize = (double) stat.getAvailableBlocks() * (double) stat.getBlockSize();
            }

        } catch (IllegalArgumentException e) // http://stackoverflow.com/questions/23516075/invalid-path-error-get-the-external-memory-size
        {
            // returning sufficient amount of size so that download is executed
            sdAvailSize = 15 * 1024 * 1024;
        }
        return sdAvailSize;
    }

    /**
     * Is called first from the HikeMessenger application.
     */
    public static boolean isFirstInstall(Context context) {
        try {
            long firstInstallTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;

            boolean isFirstInstall = firstInstallTime == lastUpdateTime;
            return isFirstInstall;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isLocationEnabled(Context context) {
        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean hasGps = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);

        if (!hasGps) {
            return false;
        } else if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else if (locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return false;
        }

        return false;
    }

    public static boolean hasGLES20() {
        ActivityManager am = (ActivityManager)
                AppSharingApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x20000;
    }

}
