package com.github.tachesimazzoca.android.example.preferences;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

public class LegacySettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            String category = getIntent().getStringExtra("category");
            int prefId = R.xml.prefs_headers_legacy;
            if (null != category) {
                if ("general".equals(category)) {
                    prefId = R.xml.prefs_settings_general_legacy;
                } else if ("account".equals(category)) {
                    prefId = R.xml.prefs_settings_account;
                } else if ("notifications".equals(category)) {
                    prefId = R.xml.prefs_settings_notifications;
                } else if ("sharing".equals(category)) {
                    prefId = R.xml.prefs_settings_sharing_legacy;
                }
            }
            addPreferencesFromResource(prefId);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.prefs_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    @Override
    public boolean onIsMultiPane() {
        return getResources().getBoolean(R.bool.prefs_multi);
    }
}

