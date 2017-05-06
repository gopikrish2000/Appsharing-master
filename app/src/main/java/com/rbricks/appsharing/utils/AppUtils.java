package com.rbricks.appsharing.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

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
}
