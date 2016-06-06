package com.rbricks.appsharing.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v4.content.LocalBroadcastManager;

import com.rbricks.appsharing.receivers.GopiBroadcastReceiver;

/**
 * Created by gopikrishna on 6/6/16.
 */
public class GopiIntentService extends IntentService {

    private GopiBroadcastReceiver receiver;

    public GopiIntentService() {
        super("GopiIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String intentServiceParam = intent.getExtras().getString("intentServiceParam");
        String str =  intentServiceParam + "NewlyAdded";
        System.out.println("intentServiceParam Updated is = " + str);
        IntentFilter filter = new IntentFilter("com.gopi.broadcast");
        receiver = new GopiBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);


        Intent broadcastIntent = new Intent("com.gopi.broadcast");
        broadcastIntent.putExtra("broadcastParam", "This is BroadCastParam");
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);

        stickyBroadcast();
    }

    // Sticky Broadcast For making broadcast alive even after receiver call is done  ( doesn't require receiver can return in Intent )
    public void stickyBroadcast() {
        // Register for the battery changed event
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        // Intent is sticky so using null as receiver works fine
// return value contains the status
        Intent batteryStatus = this.registerReceiver(null, filter);

// Are we charging / charged?
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL;

        boolean isFull = status == BatteryManager.BATTERY_STATUS_FULL;

// How are we charging?
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        System.out.println("acCharge = " + acCharge + " battery status isFull is " + isFull);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
