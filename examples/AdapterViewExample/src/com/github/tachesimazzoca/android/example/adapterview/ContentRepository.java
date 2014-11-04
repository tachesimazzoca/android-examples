package com.github.tachesimazzoca.android.example.adapterview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentRepository {
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";

    private final List<Map<String, String>> mItems;

    private ContentRepository(int max) {
        mItems = new ArrayList<Map<String, String>>();
        for (int n = 0; n < max; n++) {
            Map<String, String> m = new HashMap<String, String>();
            m.put(KEY_TITLE, "Title" + n);
            m.put(KEY_DESCRIPTION, "Description" + n);
            mItems.add(m);
        }
    }

    public static ContentRepository newInstance() {
        return newInstance(50);
    }

    public static ContentRepository newInstance(int max) {
        return new ContentRepository(max);
    }

    public List<Map<String, String>> getItems() {
        return mItems;
    }
}
