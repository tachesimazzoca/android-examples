package com.github.tachesimazzoca.android.example.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class SettingsActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new View(this));

        SettingsFragment frag = new SettingsFragment();
        String action = getIntent().getAction();
        int prefId = R.xml.prefs_settings;
        if (getString(R.string.pref_settings_profile).equals(action)) {
            prefId = R.xml.prefs_settings_profile;
        }
        Bundle args = new Bundle();
        args.putInt(SettingsFragment.ARG_PREF_ID, prefId);
        frag.setArguments(args);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, frag)
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        public static final String ARG_PREF_ID = "prefId";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int prefId = getArguments().getInt(ARG_PREF_ID);
            addPreferencesFromResource(prefId);
        }
    }
}

