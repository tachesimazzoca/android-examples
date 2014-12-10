package com.github.tachesimazzoca.android.example.storage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.LinkedHashMap;
import java.util.Map;

public class SettingsActivity extends ActionBarActivity {
    private static final String TAG = "SettingsActivity";

    private static final String PREFS_KEY_SETTINGS = "settings";

    private static final Map<Integer, Settings.Colors> COLORS_MAP =
            new LinkedHashMap<Integer, Settings.Colors>() {
                {
                    put(R.id.settings_colors_red, Settings.Colors.RED);
                    put(R.id.settings_colors_green, Settings.Colors.GREEN);
                    put(R.id.settings_colors_blue, Settings.Colors.BLUE);
                }
            };

    private static final Map<Integer, Settings.YesNo> YES_NO_MAP =
            new LinkedHashMap<Integer, Settings.YesNo>() {
                {
                    put(R.id.settings_yes_no_yes, Settings.YesNo.YES);
                    put(R.id.settings_yes_no_maybe, Settings.YesNo.MAYBE);
                    put(R.id.settings_yes_no_no, Settings.YesNo.NO);
                }
            };

    private Settings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String serialized = prefs.getString(PREFS_KEY_SETTINGS, null);
        if (null != serialized) {
            try {
                mSettings = (Settings) Base64Serializer.unserialize(serialized);
            } catch (Exception e) {
                Log.i(TAG, e.getMessage());
            }
        } else {
            mSettings = new Settings();
        }
        if (null == mSettings)
            mSettings = new Settings();

        for (Map.Entry<Integer, Settings.Colors> entry : COLORS_MAP.entrySet()) {
            CheckBox cb = (CheckBox) findViewById(entry.getKey());
            final Settings.Colors v = entry.getValue();
            cb.setChecked(mSettings.colors.contains(v));
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean checked = ((CheckBox) view).isChecked();
                    Log.i(TAG, "clicked: " + v.label);
                    mSettings = mSettings.updateColor(v, checked);
                }
            });
        }

        for (Map.Entry<Integer, Settings.YesNo> entry : YES_NO_MAP.entrySet()) {
            RadioButton rb = (RadioButton) findViewById(entry.getKey());
            rb.setChecked(mSettings.yesNo == entry.getValue());
        }
        RadioGroup yesNo = (RadioGroup) findViewById(R.id.settings_yes_no);
        yesNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                Settings.YesNo v = YES_NO_MAP.get(id);
                Log.i(TAG, "clicked: " + v.label);
                mSettings = mSettings.updateYesNo(v);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        try {
            String serialized = Base64Serializer.serialize(mSettings);
            editor.putString(PREFS_KEY_SETTINGS, serialized);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        } finally {
            editor.commit();
        }
    }
}
