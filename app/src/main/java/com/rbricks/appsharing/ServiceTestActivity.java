package com.rbricks.appsharing;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;

import com.rbricks.appsharing.services.GopiService;

public class ServiceTestActivity extends AppCompatActivity {
    private Intent gopiServiceIntent;

//    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        this.gopiServiceIntent = new Intent(this, GopiService.class);
        gopiServiceIntent.putExtra("param1", "This is first param");
        HandlerThread handlerThread = new HandlerThread("bgthread");
        handlerThread.start();
        gopiServiceIntent.putExtra("result_receiver", new GopiResultReceiver(new Handler(handlerThread.getLooper()))); // if u want callback in bg thread.
        startService(gopiServiceIntent);
        bindService(gopiServiceIntent, serviceConnection, 0);
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GopiService.GopiBinder gopiBinderService = (GopiService.GopiBinder) service;
            String updatedInput = gopiBinderService.getUpdatedInput();  // or do some long running process by giving interface callback.
            System.out.println("updatedInput in ServiceConnection is = " + updatedInput);
            System.out.println("updatedInput in ServiceConnection  with refVar is = " + gopiBinderService.getService().networkData);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    static class GopiResultReceiver extends ResultReceiver {
        public GopiResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
//            super.onReceiveResult(resultCode, resultData);
            System.out.println("Result Received with resultCode = " + resultCode + " resultData " + resultData);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(gopiServiceIntent);
        unbindService(serviceConnection);
    }
}
