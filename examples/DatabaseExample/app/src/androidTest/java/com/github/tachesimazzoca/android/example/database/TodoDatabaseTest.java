package com.github.tachesimazzoca.android.example.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.test.ApplicationTestCase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TodoDatabaseTest extends ApplicationTestCase<Application> {
    Context mContext;

    public TodoDatabaseTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mContext = getContext();
    }

    private static Cursor findAllTodoTasks(TodoDatabase dbHelper) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] columns = null;
        String whereClause = "";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = TodoDatabase.TASK_COLUMN_ID + " ASC";
        return dbHelper.getReadableDatabase().query(TodoDatabase.TASK_TABLE,
                columns, whereClause, whereArgs, groupBy, having, orderBy);
    }

    private static void deleteAllTodoTasks(TodoDatabase dbHelper) {
        String whereClause = null;
        String[] whereArgs = null;
        dbHelper.getWritableDatabase().delete(TodoDatabase.TASK_TABLE,
                whereClause, whereArgs);
    }

    public void testTodoTasks() {
        final TodoDatabase dbHelper = new TodoDatabase(mContext);
        final int N = 10;
        final Calendar calendar = Calendar.getInstance();

        // Clean up all records
        deleteAllTodoTasks(dbHelper);

        // Expected results
        HashMap<Long, Task> tasks = new HashMap<Long, Task>();

        // Insert
        calendar.set(2015, 1, 1);
        String nullColumnHack = null;
        for (int i = 0; i < N; i++) {
            ContentValues values = new ContentValues();
            values.put(TodoDatabase.TASK_COLUMN_CONTENT, "Do task " + i);
            values.put(TodoDatabase.TASK_COLUMN_LABEL_ID, (i % 4) + 1);
            values.put(TodoDatabase.TASK_COLUMN_DUE_DATE,
                    calendar.getTimeInMillis() + 86400 * i);
            long id = dbHelper.getWritableDatabase().insert(
                    TodoDatabase.TASK_TABLE, nullColumnHack, values);
            assertTrue(id > 0);
            tasks.put(id, new Task(id,
                    values.getAsInteger(TodoDatabase.TASK_COLUMN_LABEL_ID),
                    values.getAsString(TodoDatabase.TASK_COLUMN_CONTENT),
                    values.getAsLong(TodoDatabase.TASK_COLUMN_DUE_DATE)));
        }

        // Select
        Cursor cursor = findAllTodoTasks(dbHelper);
        assertEquals(N, cursor.getCount());
        long lastId = 0;
        int i = 0;
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(TodoDatabase.TASK_COLUMN_ID));
            assertTrue("todo_tasks._id asc", lastId < id);
            Task task = tasks.get(id);
            assertEquals(cursor.getInt(cursor.getColumnIndex(
                    TodoDatabase.TASK_COLUMN_LABEL_ID)), (int) task.labelId);
            assertEquals(cursor.getString(cursor.getColumnIndex(
                    TodoDatabase.TASK_COLUMN_CONTENT)), task.content);
            assertEquals(cursor.getLong(cursor.getColumnIndex(
                    TodoDatabase.TASK_COLUMN_DUE_DATE)), (long) task.dueDate);
            lastId = id;
            i++;
        }
        cursor.close();

        // Update
        for (Map.Entry<Long, Task> entry : tasks.entrySet()) {
            Task task = entry.getValue();
            ContentValues values = new ContentValues();
            values.put(TodoDatabase.TASK_COLUMN_CONTENT, task.content + " updated");
            dbHelper.getWritableDatabase().update(TodoDatabase.TASK_TABLE, values,
                    TodoDatabase.TASK_COLUMN_ID + " = " + task.id, null);
        }
        cursor = findAllTodoTasks(dbHelper);
        i = 0;
        lastId = 0;
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(TodoDatabase.TASK_COLUMN_ID));
            assertTrue("todo_tasks._id asc", lastId < id);
            Task task = tasks.get(id);
            assertEquals(cursor.getInt(cursor.getColumnIndex(
                    TodoDatabase.TASK_COLUMN_LABEL_ID)), (int) task.labelId);
            assertEquals(cursor.getString(cursor.getColumnIndex(
                    TodoDatabase.TASK_COLUMN_CONTENT)), task.content + " updated");
            assertEquals(cursor.getLong(cursor.getColumnIndex(
                    TodoDatabase.TASK_COLUMN_DUE_DATE)), (long) task.dueDate);
            lastId = id;
            i++;
        }
        cursor.close();

        // Clean up all records
        deleteAllTodoTasks(dbHelper);
    }
}