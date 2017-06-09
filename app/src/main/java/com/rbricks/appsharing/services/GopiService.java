package com.rbricks.appsharing.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

/**
 * Created by gopikrishna on 6/5/16.
 */
public class GopiService extends Service {
    public String networkData;


    public GopiService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Create is called first n only one thing , So do basic initializations here. better than onStartCommand which will be called
        // for all the threads.
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new GopiBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String param1 = intent.getExtras().getString("param1");  // can pass ResultReceiver in the bundle for result passing.
        ResultReceiver resultReceiver = intent.getParcelableExtra("result_receiver");
        Bundle bundle = new Bundle();
        bundle.putString("result", param1 + "MODI");
        resultReceiver.send(200, bundle);
        System.out.println("param1 = " + param1);

        Thread thread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler(Looper.myLooper());
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        networkData = param1 + "ADDED";
                        String param2 = networkData;
                        System.out.println("modified param in new thread = " + param2);
                        stopSelf();
                    }
                });
                Looper.loop();
            }
        };
        thread.start();
//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public class GopiBinder extends Binder {

        public GopiService getService() {
            return GopiService.this;
        }

        public String getUpdatedInput() {
            return networkData;
        }

    }

}
