package com.github.tachesimazzoca.android.example.preferences;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SimplePreferenceFragment extends PreferenceFragment {
    public static final String ARG_RESOURCE = "resource";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String resourceName = getArguments().getString(ARG_RESOURCE);
        if (null != resourceName) {
            int resId = getResources().getIdentifier(
                    resourceName, "xml", getActivity().getPackageName());
            if (0 != resId)
                addPreferencesFromResource(resId);
        }
    }
}
