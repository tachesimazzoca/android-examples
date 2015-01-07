package com.github.tachesimazzoca.android.example.database;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

public class ApplicationTest extends ApplicationTestCase<Application> {
    Context mContext;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mContext = getContext();
    }

    public void testContext() {
        assertEquals("com.github.tachesimazzoca.android.example.database",
                mContext.getPackageName());
    }
}