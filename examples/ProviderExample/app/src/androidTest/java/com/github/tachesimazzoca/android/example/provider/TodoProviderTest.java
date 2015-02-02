package com.github.tachesimazzoca.android.example.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

import java.util.HashMap;
import java.util.Map;

public class TodoProviderTest extends ProviderTestCase2<TodoProvider> {
    public TodoProviderTest() {
        super(TodoProvider.class, "com.github.tachesimazzoca.android.example.provider");
    }

    public void testCRUD() {
        ContentResolver resolver = getMockContentResolver();
        Cursor cursor;

        cursor = resolver.query(TodoContract.Tasks.CONTENT_URI,
                null, null, null, null);
        assertEquals(0, cursor.getCount());

        // insert
        final int N = 5;
        Map<Long, ContentValues> expected = new HashMap<Long, ContentValues>();
        for (int n = 1; n <= N; n++) {
            ContentValues values = new ContentValues();
            values.put(TodoContract.Tasks.LABEL_ID, (n - 1) % 4 + 1);
            values.put(TodoContract.Tasks.CONTENT, "Task-" + n);
            values.put(TodoContract.Tasks.DUE_DATE, System.currentTimeMillis());
            Uri uri = resolver.insert(TodoContract.Tasks.CONTENT_URI, values);
            expected.put(ContentUris.parseId(uri), values);
        }
        cursor = resolver.query(TodoContract.Tasks.CONTENT_URI,
                null, null, null, null);
        assertEquals(expected.size(), cursor.getCount());
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(TodoContract.Tasks._ID));
            String content = cursor.getString(cursor.getColumnIndex(TodoContract.Tasks.CONTENT));
            assertEquals(expected.get(id).get(TodoContract.Tasks.CONTENT), content);
        }

        // update
        for (Map.Entry<Long, ContentValues> entry : expected.entrySet()) {
            long id = entry.getKey();
            ContentValues v = entry.getValue();
            ContentValues values = new ContentValues();
            values.put(TodoContract.Tasks.CONTENT,
                    "Updated " + v.get(TodoContract.Tasks.CONTENT));
            resolver.update(ContentUris.withAppendedId(TodoContract.Tasks.CONTENT_URI, id),
                    values, null, null);
        }
        cursor = resolver.query(TodoContract.Tasks.CONTENT_URI,
                null, null, null, null);
        assertEquals(expected.size(), cursor.getCount());
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(TodoContract.Tasks._ID));
            String content = cursor.getString(cursor.getColumnIndex(TodoContract.Tasks.CONTENT));
            ContentValues values = expected.get(id);
            assertEquals("Updated " + values.get(TodoContract.Tasks.CONTENT), content);
        }

        // delete
        Long ids[] = expected.keySet().toArray(new Long[expected.size()]);
        Uri one = ContentUris.withAppendedId(
                        TodoContract.Tasks.CONTENT_URI, ids[0]);
        resolver.delete(one, null, null);

        cursor = resolver.query(TodoContract.Tasks.CONTENT_URI,
                null, null, null, null);
        assertEquals(expected.size() - 1, cursor.getCount());

        cursor = resolver.query(one, null, null, null, null);
        assertEquals(0, cursor.getCount());

        // delete all
        resolver.delete(TodoContract.Tasks.CONTENT_URI, null, null);
        cursor = resolver.query(TodoContract.Tasks.CONTENT_URI, null, null, null, null);
        assertEquals(0, cursor.getCount());
    }
}
