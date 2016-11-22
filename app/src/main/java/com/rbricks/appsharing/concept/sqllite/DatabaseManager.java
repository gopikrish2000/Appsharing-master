package com.rbricks.appsharing.concept.sqllite;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.rbricks.appsharing.concept.Application.AppSharingApplication;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static com.rbricks.appsharing.concept.sqllite.SqlliteTables.NotesListingTable.CREATED_ON;
import static com.rbricks.appsharing.concept.sqllite.SqlliteTables.NotesListingTable.DESCRIPTION;
import static com.rbricks.appsharing.concept.sqllite.SqlliteTables.NotesListingTable.ID;
import static com.rbricks.appsharing.concept.sqllite.SqlliteTables.NotesListingTable.IS_DELETED;
import static com.rbricks.appsharing.concept.sqllite.SqlliteTables.NotesListingTable.TABLE_NAME;
import static com.rbricks.appsharing.concept.sqllite.SqlliteTables.NotesListingTable.TITLE;
import static com.rbricks.appsharing.concept.sqllite.SqlliteTables.NotesListingTable.UPDATED_ON;
import static com.rbricks.appsharing.utils.CommonUtils.getCurrentDateTimeForDb;
import static com.rbricks.appsharing.utils.CommonUtils.isNullOrEmpty;


/**
 * Created by gopikrishna on 12/11/16.
 */

public class DatabaseManager {

    public static Observable<Long> insertNotes(NotesItem notes) {
        return Observable.create(subscriber -> {
            long result;
            try {
                String currentDateTimeForDb = getCurrentDateTimeForDb();
                ContentValues contentValues = new ContentValues();
                contentValues.put(TITLE, notes.getTitle());
                contentValues.put(DESCRIPTION, notes.getDescription());
                contentValues.put(UPDATED_ON, currentDateTimeForDb);
                contentValues.put(CREATED_ON, currentDateTimeForDb);
                result = AppSharingApplication.getInstance().getWritableDB().insert(TABLE_NAME, null, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("exception in insertion", e.getLocalizedMessage());
                result = -1;
            }
            subscriber.onNext(result);
        });
    }

    public static Observable<Boolean> updateNotes(NotesItem notes) {
        return Observable.create(subscriber -> {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TITLE, notes.getTitle());
                contentValues.put(DESCRIPTION, notes.getDescription());
                contentValues.put(UPDATED_ON, getCurrentDateTimeForDb());

                int i = AppSharingApplication.getInstance().getWritableDB().update(TABLE_NAME,
                        contentValues,
                        ID + " = ?",
                        new String[]{String.valueOf(notes.getId())});
                subscriber.onNext(i != -1);
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onNext(false);
            }
        });
    }

    public static boolean hardDeleteNotes(int id) {
        int i = AppSharingApplication.getInstance().getWritableDB().delete(TABLE_NAME, ID + " =?",
                new String[]{String.valueOf(id)});
        return i != -1;
    }

    public static Observable<Boolean> softDeleteNotes(int id) {
        return Observable.create(subscriber -> {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(IS_DELETED, "1");

                int i = AppSharingApplication.getInstance().getWritableDB().update(TABLE_NAME,
                        contentValues,
                        ID + " = ?",
                        new String[]{String.valueOf(id)});
                subscriber.onNext(i != -1);
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onNext(false);
            }
        });
    }

    public static Observable<List<NotesItem>> getAllNotes() {
        return Observable.create(subscriber -> {
            List<NotesItem> notesItems = new ArrayList<>();
            Cursor cursor = AppSharingApplication.getInstance().getReadableDb()
                    .rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + IS_DELETED + " = ? ORDER BY " + UPDATED_ON + " DESC ", new String[]{"0"});
            try {
                while (cursor.moveToNext()) {
                    NotesItem notesItem = new NotesItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getInt(5));
                    notesItems.add(notesItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
            subscriber.onNext(notesItems);
        });
    }

    public static Observable<List<NotesItem>> getAllSearchedNotes(String searchTerm) {
        if (isNullOrEmpty(searchTerm)) {
            return Observable.just(new ArrayList<>());
        }
        return Observable.create(subscriber -> {
            List<NotesItem> notesItems = new ArrayList<>();
            String regexSearchTerm = "%" + searchTerm + "%";
            Cursor cursor = AppSharingApplication.getInstance().getReadableDb()
                    .rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + IS_DELETED + " = ? AND (" + TITLE + " LIKE ?  OR "
                            + DESCRIPTION + " LIKE ? )"
                            + " ORDER BY " + UPDATED_ON + " DESC ", new String[]{"0", regexSearchTerm, regexSearchTerm});
            try {
                while (cursor.moveToNext()) {
                    NotesItem notesItem = new NotesItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getInt(5));
                    notesItems.add(notesItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
            System.out.println("notesItems in getAllSearchedNotes = " + notesItems);
            subscriber.onNext(notesItems);
        });
    }

    public static Observable<NotesItem> getNoteById(long id) {
        return Observable.create(subscriber -> {
            Cursor cursor = AppSharingApplication.getInstance().getReadableDb()
                    .rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = ? lIMIT 1", new String[]{id + ""});
            try {
                while (cursor.moveToNext()) {
                    NotesItem notesItem = new NotesItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getInt(5));
                    subscriber.onNext(notesItem);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
            subscriber.onNext(null);
        });
    }

}
