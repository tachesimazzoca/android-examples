package com.github.tachesimazzoca.android.example.preferences;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new View(this));

        PreferenceFragment frag;
        String category = getIntent().getStringExtra("category");
        if ("account".equals(category)) {
            frag = new AccountPreferenceFragment();
        } else if ("notifications".equals(category)) {
            frag = new NotificationsPreferenceFragment();
        } else if ("sharing".equals(category)) {
            frag = new SimplePreferenceFragment();
            Bundle args = new Bundle();
            args.putString(SimplePreferenceFragment.ARG_RESOURCE, "prefs_settings_sharing");
            frag.setArguments(args);
        } else {
            frag = new SettingsFragment();
        }

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, frag)
                .commit();
    }
}

