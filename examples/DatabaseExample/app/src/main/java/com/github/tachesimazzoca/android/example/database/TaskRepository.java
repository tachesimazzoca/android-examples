package com.github.tachesimazzoca.android.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.NoSuchElementException;

public class TaskRepository {
    private static Context mContext;
    private static TodoDatabase mDatabase;

    public TaskRepository(Context context) {
        mContext = context;
        mDatabase = new TodoDatabase(mContext);
        if (null == mDatabase)
            throw new NullPointerException("The database is null");
    }

    public static Task fromCursor(Cursor cursor) {
        Long id = cursor.getLong(cursor.getColumnIndex(
                TodoDatabase.TASK_COLUMN_ID));
        Integer labelId = cursor.getInt(cursor.getColumnIndex(
                TodoDatabase.TASK_COLUMN_LABEL_ID ));
        String content = cursor.getString(cursor.getColumnIndex(
                TodoDatabase.TASK_COLUMN_CONTENT));
        Long dueDate = cursor.getLong(cursor.getColumnIndex(
                TodoDatabase.TASK_COLUMN_DUE_DATE));
        return new Task(id, labelId, content, dueDate);
    }

    public void delete(Long id) {
        if (null == id) {
            deleteAll();
        } else {
            String where = TodoDatabase.TASK_COLUMN_ID + " = " + id.toString();
            mDatabase.getWritableDatabase().delete(TodoDatabase.TASK_TABLE, where, null);
        }
    }

    public void deleteAll() {
        mDatabase.getWritableDatabase().delete(TodoDatabase.TASK_TABLE, null, null);
    }

    public Cursor findAll(int labelId) {
        return mDatabase.getReadableDatabase().query(TodoDatabase.TASK_TABLE,
                null, "", null, null, null, TodoDatabase.TASK_COLUMN_DUE_DATE + " ASC");
    }

    public Task find(long id) throws NoSuchElementException {
        Cursor c = mDatabase.getReadableDatabase().query(TodoDatabase.TASK_TABLE,
                null, TodoDatabase.TASK_COLUMN_ID + "=" + id, null, null, null, null);
        if (!c.moveToNext())
            throw new NoSuchElementException("Not found id = " + id);
        return TaskRepository.fromCursor(c);
    }

    public void update(Task task) {
        if (null == task.id)
            throw new IllegalArgumentException("task.id must be not null");
        ContentValues values = new ContentValues();
        values.put(TodoDatabase.TASK_COLUMN_LABEL_ID, task.labelId);
        values.put(TodoDatabase.TASK_COLUMN_CONTENT, task.content);
        values.put(TodoDatabase.TASK_COLUMN_DUE_DATE, task.dueDate);
        mDatabase.getWritableDatabase().update(
                TodoDatabase.TASK_TABLE, values,
                TodoDatabase.TASK_COLUMN_ID + "=" + task.id, null);
    }

    public Task create(Task task) {
        ContentValues values = new ContentValues();
        values.put(TodoDatabase.TASK_COLUMN_CONTENT, task.content);
        values.put(TodoDatabase.TASK_COLUMN_LABEL_ID, task.labelId);
        values.put(TodoDatabase.TASK_COLUMN_DUE_DATE, task.dueDate);

        long id = mDatabase.getWritableDatabase().insert(
                TodoDatabase.TASK_TABLE, null, values);
        if (id > 0) {
            return new Task(id, task.labelId, task.content, task.dueDate);
        } else {
            throw new SQLException("Failed to insert " + task);
        }
    }

    public Task save(Task task) {
        ContentValues values = new ContentValues();
        values.put(TodoDatabase.TASK_COLUMN_CONTENT, task.content);
        values.put(TodoDatabase.TASK_COLUMN_LABEL_ID, task.labelId);
        values.put(TodoDatabase.TASK_COLUMN_DUE_DATE, task.dueDate);

        Task savedTask;
        if (null == task.id) {
            long id = mDatabase.getWritableDatabase().insert(
                    TodoDatabase.TASK_TABLE, null, values);
            if (id > 0) {
                savedTask = new Task(id, task.labelId, task.content, task.dueDate);
            } else {
                throw new SQLException("Failed to insert");
            }
        } else {
            mDatabase.getWritableDatabase().update(
                    TodoDatabase.TASK_TABLE, values,
                    TodoDatabase.TASK_COLUMN_ID + "=" + task.id, null);
            savedTask = task;
        }
        return savedTask;
    }
}
