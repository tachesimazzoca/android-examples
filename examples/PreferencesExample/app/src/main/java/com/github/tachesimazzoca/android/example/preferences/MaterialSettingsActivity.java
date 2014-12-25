package com.github.tachesimazzoca.android.example.preferences;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MaterialSettingsActivity extends ActionBarActivity
        implements HeaderPreferenceFragment.OnHeaderClickListener {

    private static final String TAG = "SettingsActivity";
    private boolean mMultiPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Whether or not the single pane layout is loaded
        mMultiPane = (null == findViewById(R.id.fragment_container));

        // We should use v4 SupportFragmentManager in v7 ActionBarActivity, but
        // PreferenceFragment is not a type of v4 Fragment. So, we dare to use
        // v11+ FragmentManger with the workaround shown at the "onBackPressed"
        PreferenceFragment frag = new HeaderPreferenceFragment();
        if (mMultiPane) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_header, frag)
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, frag)
                    .commit();
        }
    }

    @Override
    public void onHeaderClick(Preference preference) {
        Log.i(TAG, "onHeaderClick: " + preference.getKey());

        PreferenceFragment frag;
        String key = preference.getKey();
        if (HeaderPreferenceFragment.PREF_KEY_HEADER_ACCOUNT.equals(key)) {
            frag = new AccountPreferenceFragment();
        } else if (HeaderPreferenceFragment.PREF_KEY_HEADER_NOTIFICATIONS.equals(key)) {
            frag = new NotificationsPreferenceFragment();
        } else if (HeaderPreferenceFragment.PREF_KEY_HEADER_SHARING.equals(key)) {
            frag = new SimplePreferenceFragment();
            Bundle args = new Bundle();
            args.putString(SimplePreferenceFragment.ARG_RESOURCE, "prefs_settings_sharing");
            frag.setArguments(args);
        } else {
            frag = null;
        }

        if (null == frag)
            return;

        if (mMultiPane) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_content, frag)
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, frag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * This is just a workaround to use PreferenceFragment with v11+
     * FragmentManager. In ActionBarActivity, v4 SupportFragmentManager
     * pops the back stack properly, but v11+ FragmentManager avoids it.
     */
    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
            return;
        }
        super.onBackPressed();
    }
}

