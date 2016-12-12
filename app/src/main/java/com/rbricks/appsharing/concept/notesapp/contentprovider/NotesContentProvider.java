package com.rbricks.appsharing.concept.notesapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.rbricks.appsharing.concept.notesapp.utils.NotesDbHelper;
import com.rbricks.appsharing.concept.notesapp.utils.SqlliteTables.NotesListingTable;

import static android.provider.UserDictionary.Words.CONTENT_URI;

/**
 * Created by gopikrishna on 08/12/16.
 */

public class NotesContentProvider extends ContentProvider {
    public static String PROVIDER_NAME = "com.notes.gopi.provider";
    public static final Uri NOTES_URI = Uri.parse("content://" + PROVIDER_NAME + "/" + NotesListingTable.TABLE_NAME);
    private SQLiteDatabase db;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, NotesListingTable.TABLE_NAME, 1);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        NotesDbHelper dbHelper = new NotesDbHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case 1:
                Cursor cursor = db.query(NotesListingTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/vnd." + PROVIDER_NAME;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case 1:
                long rowID = db.insert(NotesListingTable.TABLE_NAME, null, values);
                if (rowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                } else {
                    throw new SQLException("Failed to add a record into " + uri);
                }

        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteId = -1;
        switch (uriMatcher.match(uri)) {
            case 1:
                deleteId = db.delete(NotesListingTable.TABLE_NAME, selection, selectionArgs);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteId;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updateId = -1;
        switch (uriMatcher.match(uri)) {
            case 1:
                updateId = db.update(NotesListingTable.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updateId;
    }
}
