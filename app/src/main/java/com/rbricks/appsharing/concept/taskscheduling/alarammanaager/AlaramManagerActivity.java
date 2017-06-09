package com.rbricks.appsharing.concept.taskscheduling.alarammanaager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;
import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.taskscheduling.services.GopiOneOffService;
import com.rbricks.appsharing.services.GopiService;

import java.util.Calendar;

public class AlaramManagerActivity extends AppCompatActivity {

    private GcmNetworkManager mGcmNetworkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaram_manager);

//        alarmManagerSchedule();
        oneOffTaskSchedule();
    }

    // It requires googleservices.json , so commenting.
    private void oneOffTaskSchedule() {
        mGcmNetworkManager = GcmNetworkManager.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putInt("oneoff_key", 100);
        OneoffTask task = new OneoffTask.Builder()
                .setService(GopiOneOffService.class)   // Service which is extending GCMTaskService Only . which has public int onRunTask(TaskParams taskParams) method
                .setTag("GopiOneOffServiceTAG")
                .setExtras(bundle)
                .setExecutionWindow(15L, 25L)
                .setRequiredNetwork(Task.NETWORK_STATE_ANY)   // optimization will run only when network is available or any ...
                .build();
        mGcmNetworkManager.schedule(task);  // gcmNetworkManager.cancelTask("TAG",gcmtaskService.class); for cancelling.
    }

    /*private void periodicTaskSchedule() {
        PeriodicTask task = new PeriodicTask.Builder()
                .setService(MyTaskService.class)
                .setTag("TAG")
                .setPeriod(30L)
                .build();

        mGcmNetworkManager.schedule(task);
    }
*/
    private void alarmManagerSchedule() {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, GopiService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30 * 1000, pintent);
        alarm.cancel(pintent);
    }
}
