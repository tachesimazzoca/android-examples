package com.github.tachesimazzoca.android.example.provider;

import android.app.Application;
import android.content.UriMatcher;
import android.net.Uri;
import android.test.ApplicationTestCase;

public class UriTest extends ApplicationTestCase<Application> {
    private static final String AUTHORITIES =
            "com.github.tachesimazzoca.android.example.provider";
    public UriTest() {
        super(Application.class);
    }

    public void testAppendedPath() {
        Uri uri = Uri.withAppendedPath(Uri.parse(
                "content://" + AUTHORITIES), "todo");
        assertEquals("content://" + AUTHORITIES + "/todo", uri.toString());
        assertEquals("/todo", uri.getPath());
    }

    public void testUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITIES, "users", 1);
        matcher.addURI(AUTHORITIES, "users/#", 2);
        matcher.addURI(AUTHORITIES, "users/#/articles", 3);
        matcher.addURI(AUTHORITIES, "users/#/articles/#", 4);

        assertEquals(UriMatcher.NO_MATCH,
                matcher.match(Uri.parse("content://" + AUTHORITIES + "/none")));

        assertEquals(1,
                matcher.match(Uri.parse("content://" + AUTHORITIES + "/users")));
        assertEquals(2,
                matcher.match(Uri.parse("content://" + AUTHORITIES + "/users/123")));
        assertEquals(3,
                matcher.match(Uri.parse("content://" + AUTHORITIES + "/users/123/articles")));
        assertEquals(4,
                matcher.match(Uri.parse("content://" + AUTHORITIES + "/users/123/articles/45")));
    }
}