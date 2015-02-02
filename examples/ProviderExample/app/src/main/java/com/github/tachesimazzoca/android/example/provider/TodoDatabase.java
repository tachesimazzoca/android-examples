package com.github.tachesimazzoca.android.example.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabase extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "todo";

    public static String TASK_TABLE = "tasks";

    public TodoDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dropTables(db);
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This doesn't support DB migration.
        dropTables(db);
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TASK_TABLE + " ("
                + TodoContract.Tasks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TodoContract.Tasks.LABEL_ID + " INTEGER NOT NULL default 0,"
                + TodoContract.Tasks.CONTENT + " TEXT NOT NULL,"
                + TodoContract.Tasks.DUE_DATE + " INTEGER)");
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
    }
}
