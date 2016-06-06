package com.rbricks.appsharing.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by gopikrishna on 6/6/16.
 */
public class GopiBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String broadcastParam = intent.getExtras().getString("broadcastParam");
        System.out.println("broadcastParam inside broadcast= " + broadcastParam);
    }
}
