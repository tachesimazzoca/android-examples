package com.github.tachesimazzoca.android.example.preferences;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AccountPreferenceFragment extends PreferenceFragment {
    private static final String PREF_KEY_ACCOUNT_NICKNAME = "pref_key_account_nickname";

    private SharedPreferences.OnSharedPreferenceChangeListener mListener;
    private Preference mNicknamePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_settings_account);

        // Fetch preferences to update each summary text
        mNicknamePreference = getPreferenceManager().findPreference(
                PREF_KEY_ACCOUNT_NICKNAME);

        // Register a listener to update each preference
        mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(
                    SharedPreferences sharedPreferences, String key) {
                if (PREF_KEY_ACCOUNT_NICKNAME.equals(key)) {
                    mNicknamePreference.setSummary(
                           sharedPreferences.getString(PREF_KEY_ACCOUNT_NICKNAME, ""));
                }
            }
        };
        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(mListener);

        // To set the initial value, invoke the callback.
        mListener.onSharedPreferenceChanged(prefs, PREF_KEY_ACCOUNT_NICKNAME);
    }
}
