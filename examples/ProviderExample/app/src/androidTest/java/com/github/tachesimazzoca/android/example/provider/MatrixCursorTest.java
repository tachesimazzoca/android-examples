package com.github.tachesimazzoca.android.example.provider;

import android.app.Application;
import android.database.MatrixCursor;
import android.test.ApplicationTestCase;

public class MatrixCursorTest extends ApplicationTestCase<Application> {
    public MatrixCursorTest() {
        super(Application.class);
    }

    public void testMatrixCursor() {
        final String[] columnNames = {"_ID", "NAME", "EMAIL"};
        MatrixCursor cursor = new MatrixCursor(columnNames);

        final int N = 10;
        for (int n = 1; n <= N; n++) {
            MatrixCursor.RowBuilder builder = cursor.newRow();
            builder.add(n);
            builder.add("NAME-" + n);
            builder.add("user" + n + "@example.net");
        }

        assertEquals(N, cursor.getCount());
        while (cursor.moveToNext()) {
            int n = cursor.getInt(cursor.getColumnIndex(columnNames[0]));
            assertEquals("NAME-" + n,
                    cursor.getString(cursor.getColumnIndex(columnNames[1])));
            assertEquals("user" + n + "@example.net",
                    cursor.getString(cursor.getColumnIndex(columnNames[2])));
        }
    }
}
