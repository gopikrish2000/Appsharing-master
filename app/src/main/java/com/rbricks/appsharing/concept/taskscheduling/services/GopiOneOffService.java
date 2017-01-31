package com.rbricks.appsharing.concept.taskscheduling.services;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

/**
 * Created by gopikrishna on 31/01/17.
 */

public class GopiOneOffService extends GcmTaskService {

    @Override
    public int onRunTask(TaskParams taskParams) {
        int oneOffValue = taskParams.getExtras().getInt("oneoff_key", 0);
        System.out.println("Inside Oneoff Service printing value " + oneOffValue);
        return 0;
    }
}
