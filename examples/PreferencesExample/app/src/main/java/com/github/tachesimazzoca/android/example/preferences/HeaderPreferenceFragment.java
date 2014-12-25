package com.github.tachesimazzoca.android.example.preferences;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HeaderPreferenceFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

    public static final String PREF_KEY_HEADER_ACCOUNT = "pref_key_header_account";
    public static final String PREF_KEY_HEADER_NOTIFICATIONS = "pref_key_header_notifications";
    public static final String PREF_KEY_HEADER_SHARING = "pref_key_header_sharing";

    private static final String[] HEADER_KEYS = {
            PREF_KEY_HEADER_ACCOUNT,
            PREF_KEY_HEADER_NOTIFICATIONS,
            PREF_KEY_HEADER_SHARING
    };

    private OnHeaderClickListener mListener;

    public interface OnHeaderClickListener {
        void onHeaderClick(Preference preference);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_settings_header);

        for (String key : HEADER_KEYS) {
            getPreferenceManager()
                    .findPreference(key)
                    .setOnPreferenceClickListener(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHeaderClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement HeaderClickListener");
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (null != mListener) {
            mListener.onHeaderClick(preference);
            return true;
        }
        return false;
    }
}
