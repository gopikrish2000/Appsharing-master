package com.rbricks.appsharing.concept.Application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.rbricks.appsharing.concept.sqllite.NotesDbHelper;


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
