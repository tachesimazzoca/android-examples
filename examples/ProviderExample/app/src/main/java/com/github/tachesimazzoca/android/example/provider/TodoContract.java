package com.github.tachesimazzoca.android.example.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class TodoContract {
    public static final String AUTHORITIES =
            "com.github.tachesimazzoca.android.example.provider";

    public static class Tasks {
        public static final Uri CONTENT_URI = Uri.parse(
                "content://" + AUTHORITIES + "/tasks");
        public static String _ID = BaseColumns._ID;
        public static String LABEL_ID = "label_id";
        public static String CONTENT = "content";
        public static String DUE_DATE = "due_date";
    }
}
