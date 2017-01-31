package com.rbricks.appsharing.concept.taskscheduling.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.rbricks.appsharing.receivers.GopiBroadcastReceiver;

/**
 * Created by gopikrishna on 6/6/16.
 */
public class SchedulingIntentService extends IntentService {

    private GopiBroadcastReceiver receiver;

    public SchedulingIntentService() {
        super("GopiIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String intentServiceParam = intent.getExtras() != null ? intent.getExtras().getString("intentServiceParam") : "";
        String str = intentServiceParam + "NewlyAdded";
        System.out.println("intentServiceParam Updated is = " + str);
        IntentFilter filter = new IntentFilter("com.gopi.broadcast");
        receiver = new GopiBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);


        Intent broadcastIntent = new Intent("com.gopi.broadcast");
        broadcastIntent.putExtra("broadcastParam", "This is BroadCastParam");
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
