package com.github.tachesimazzoca.android.example.preferences;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NotificationsPreferenceFragment extends PreferenceFragment {
    private static final String PREF_KEY_UPDATES_INTERVAL = "pref_key_updates_interval";

    private SharedPreferences.OnSharedPreferenceChangeListener mListener;
    private Preference mUpdatesIntervalPreference;
    private String[] mUpdatesIntervalValues;
    private String[] mUpdatesIntervalLabels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_settings_notifications);

        // Fetch preferences to update each summary text
        mUpdatesIntervalPreference = getPreferenceManager().findPreference(
                PREF_KEY_UPDATES_INTERVAL);

        mUpdatesIntervalLabels = getResources().getStringArray(R.array.updates_interval_labels);
        mUpdatesIntervalValues = getResources().getStringArray(R.array.updates_interval_values);

        // Register a listener to update each preference
        mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(
                    SharedPreferences sharedPreferences, String key) {
                if (PREF_KEY_UPDATES_INTERVAL.equals(key)) {
                    int idx = ArrayUtils.indexOf(
                            mUpdatesIntervalValues,
                            sharedPreferences.getString(PREF_KEY_UPDATES_INTERVAL, ""));
                    if (ArrayUtils.INDEX_NOT_FOUND != idx)
                        mUpdatesIntervalPreference.setSummary(mUpdatesIntervalLabels[idx]);
                    else
                        mUpdatesIntervalPreference.setSummary("");
                }
            }
        };
        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(mListener);

        // To set the initial value, invoke the callback.
        mListener.onSharedPreferenceChanged(prefs, PREF_KEY_UPDATES_INTERVAL);
    }
}
