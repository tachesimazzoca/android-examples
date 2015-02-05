package com.github.tachesimazzoca.android.example.search;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class AbbreviationProvider extends ContentProvider {
    private static final String[] COLUMN_NAMES = {
            BaseColumns._ID,
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_TEXT_2,
            SearchManager.SUGGEST_COLUMN_QUERY // Required if action is ACTION_SEARCH
    };

    private static final Object[][] ROWS = {
            new Object[]{1, "AFAIK", "As far as I know"},
            new Object[]{2, "AFK", "Away from keyboard"},
            new Object[]{3, "AKA", "Also known as"},
            new Object[]{4, "ASAP", "As soon as possible"},
            new Object[]{5, "BFN", "Bye for now"},
            new Object[]{6, "BTW", "By the way"},
            new Object[]{7, "FYI", "For your information"},
            new Object[]{8, "HTH", "Hope that helps"},
            new Object[]{9, "IMO", "In my opinion"},
            new Object[]{10, "IOW", "In other words"},
            new Object[]{11, "LOL", "Laughing out loud"}
    };

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (1 != selectionArgs.length)
            throw new IllegalArgumentException("The length of selectionArgs must be 1.");

        String q = selectionArgs[0].toUpperCase();

        MatrixCursor cursor = new MatrixCursor(COLUMN_NAMES);
        if (!q.isEmpty()) {
            for (Object[] row : ROWS) {
                if (((String) row[1]).contains(q))
                    cursor.addRow(new Object[]{row[0], row[1], row[2], row[1]});
            }
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues,
                      String selection, String[] selectionArgs) {
        return 0;
    }
}
