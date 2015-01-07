package com.github.tachesimazzoca.android.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TodoDatabase extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "todo";

    public static String TASK_TABLE = "tasks";
    public static String TASK_COLUMN_ID = BaseColumns._ID;
    public static String TASK_COLUMN_LABEL_ID = "label_id";
    public static String TASK_COLUMN_CONTENT = "content";
    public static String TASK_COLUMN_DUE_DATE = "due_date";

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
                + TASK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TASK_COLUMN_LABEL_ID + " INTEGER NOT NULL default 0,"
                + TASK_COLUMN_CONTENT + " TEXT NOT NULL,"
                + TASK_COLUMN_DUE_DATE + " INTEGER)");
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
    }
}
