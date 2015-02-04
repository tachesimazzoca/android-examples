package com.github.tachesimazzoca.android.example.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class SearchableActivity extends ActionBarActivity {
    private static final String TAG = "SearchDialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_dialog, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent with " + intent.getAction());
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.i(TAG, "handleIntent with " + intent.getAction());
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Bundle appData = intent.getBundleExtra(SearchManager.APP_DATA);
            if (null != appData) {
                for (String key : appData.keySet())
                    Log.i(TAG, key + "@appData: " + appData.getString(key));
            }

            String q = intent.getStringExtra(SearchManager.QUERY);
            TextView view = (TextView) findViewById(R.id.search_query);
            view.setText(q);
        }
    }
}
