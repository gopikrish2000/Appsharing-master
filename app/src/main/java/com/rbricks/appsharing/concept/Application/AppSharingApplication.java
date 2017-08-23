package com.rbricks.appsharing.concept.Application;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.commonsware.cwac.cam2.util.Utils;
import com.rbricks.appsharing.concept.notesapp.utils.NotesDbHelper;
import com.rbricks.appsharing.utils.CommonUtils;


/**
 * Created by gopikrishna on 12/11/16.
 */

public class AppSharingApplication extends Application {

    private NotesDbHelper notesDbHelper;
    private static AppSharingApplication appSharingApplication;
    private SQLiteDatabase sqLiteDatabase;


    @Override
    public void onCreate() {
        super.onCreate();
        appSharingApplication = this;
        initializeDb();
        CommonUtils.setDensityMultiplier(getResources().getDisplayMetrics());
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                System.out.println("AppSharingApplication.onActivityCreated " + activity.getClass().getSimpleName() + " with bundle " + savedInstanceState);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static AppSharingApplication getInstance() {
        return appSharingApplication;
    }

    private void initializeDb() {
        notesDbHelper = new NotesDbHelper(this);
    }

    public SQLiteDatabase getReadableDb() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = notesDbHelper.getReadableDatabase();
        }
        return sqLiteDatabase;
    }

    public SQLiteDatabase getWritableDB() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = notesDbHelper.getWritableDatabase();
        } else if (!sqLiteDatabase.isOpen()) {
            sqLiteDatabase = notesDbHelper.getWritableDatabase();
        }
        return sqLiteDatabase;
    }

}
