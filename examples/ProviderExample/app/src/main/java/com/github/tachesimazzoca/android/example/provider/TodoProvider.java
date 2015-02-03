package com.github.tachesimazzoca.android.example.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

public class TodoProvider extends ContentProvider {
    private static final String TAG = "TodoProvider";

    private TodoDatabase mDatabase;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int URI_TASKS = 1;
    private static final int URI_TASKS_ID = 2;

    static {
        mUriMatcher.addURI(TodoContract.AUTHORITIES, "tasks", URI_TASKS);
        mUriMatcher.addURI(TodoContract.AUTHORITIES, "tasks/#", URI_TASKS_ID);
    }

    @Override
    public boolean onCreate() {
        mDatabase = new TodoDatabase(getContext());
        return (null != mDatabase);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query: " + uri.toString());

        Cursor cursor;
        switch (mUriMatcher.match(uri)) {
            case URI_TASKS:
                String orderBy;
                if (null != sortOrder)
                    orderBy = sortOrder;
                else
                    orderBy = TodoContract.Tasks.DUE_DATE + " ASC";
                cursor = mDatabase.getReadableDatabase().query(TodoDatabase.TASK_TABLE,
                        projection, selection, selectionArgs,
                        null, null, orderBy);
                break;
            case URI_TASKS_ID:
                long id = Long.parseLong(uri.getLastPathSegment());
                cursor = mDatabase.getReadableDatabase().query(TodoDatabase.TASK_TABLE,
                        null, TodoContract.Tasks._ID + " = " + id,
                        null, null, null, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (mUriMatcher.match(uri) != URI_TASKS)
            throw new IllegalArgumentException("Unknown URI: " + uri);

        long id = mDatabase.getWritableDatabase().insert(
                TodoDatabase.TASK_TABLE, null, contentValues);
        if (id > 0) {
            Uri newUri = ContentUris.withAppendedId(TodoContract.Tasks.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        } else {
            throw new SQLException("Failed to insert " + contentValues);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int n;
        switch (mUriMatcher.match(uri)) {
            case URI_TASKS:
                n = mDatabase.getWritableDatabase().delete(TodoDatabase.TASK_TABLE, null, null);
                break;
            case URI_TASKS_ID:
                long id = Long.parseLong(uri.getLastPathSegment());
                n = mDatabase.getWritableDatabase().delete(TodoDatabase.TASK_TABLE,
                        TodoContract.Tasks._ID + " = " + id, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return n;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues,
                      String selection, String[] selectionArgs) {
        if (mUriMatcher.match(uri) != URI_TASKS_ID)
            throw new IllegalArgumentException("Unknown URI: " + uri);

        long id = Long.parseLong(uri.getLastPathSegment());
        int n = mDatabase.getWritableDatabase().update(
                TodoDatabase.TASK_TABLE, contentValues,
                TodoContract.Tasks._ID + " = " + id, null);
        getContext().getContentResolver().notifyChange(uri, null);
        return n;
    }
}
