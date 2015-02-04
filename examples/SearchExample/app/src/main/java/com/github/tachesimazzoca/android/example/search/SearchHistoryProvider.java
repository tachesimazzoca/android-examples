package com.github.tachesimazzoca.android.example.search;

import android.content.SearchRecentSuggestionsProvider;

public class SearchHistoryProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY =
            "com.github.tachesimazzoca.android.example.search.SearchHistoryProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchHistoryProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
