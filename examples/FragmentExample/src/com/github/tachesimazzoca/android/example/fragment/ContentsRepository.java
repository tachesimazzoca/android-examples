package com.github.tachesimazzoca.android.example.fragment;

import java.util.ArrayList;
import java.util.List;

public class ContentsRepository {
    @SuppressWarnings("serial")
    private static final ArrayList<Content> records = new ArrayList<Content>() {
        {
            add(new Content("Item1", "Body1"));
            add(new Content("Item2", "Body2"));
            add(new Content("Item3", "Body3"));
            add(new Content("Item4", "Body4"));
            add(new Content("Item5", "Body5"));
        }
    };

    public static String[] getTitles() {
        List<String> titles = new ArrayList<String>();
        for (Content content : records) {
            titles.add(content.title);
        }
        return titles.toArray(new String[records.size()]);
    }

    public static Content getContent(int idx) {
        return records.get(idx);
    }
}
