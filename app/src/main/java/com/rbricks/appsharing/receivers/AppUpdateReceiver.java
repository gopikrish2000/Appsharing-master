package com.rbricks.appsharing.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rbricks.appsharing.utils.AppUtils;

public class AppUpdateReceiver extends BroadcastReceiver {

	private static final String TAG = AppUpdateReceiver.class.getName();

	@Override
    public void onReceive(final Context context, Intent intent) {

		Log.d(TAG, "new version of app has been installed and new version is " + AppUtils.getPhoneMake());
	}
}