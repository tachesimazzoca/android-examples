package com.github.tachesimazzoca.android.example.storage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.util.Log;

public class SettingsActivity extends Activity {
    private static final String TAG =
            (new Throwable()).getStackTrace()[0].getClassName();
    private static final String PREFS_KEY_SETTINGS = "settings";

    private Settings mSettings = new Settings();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String serialized = prefs.getString(PREFS_KEY_SETTINGS, null);
        if (serialized != null) {
            try {
                mSettings = (Settings) Base64Serializer.unserialize(serialized);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }

        if (mSettings.colors.contains(Settings.Colors.RED)) {
            ((CheckBox) findViewById(R.id.settings_colors_red)).setChecked(true);
        }
        if (mSettings.colors.contains(Settings.Colors.GREEN)) {
            ((CheckBox) findViewById(R.id.settings_colors_green)).setChecked(true);
        }
        if (mSettings.colors.contains(Settings.Colors.BLUE)) {
            ((CheckBox) findViewById(R.id.settings_colors_blue)).setChecked(true);
        }

        switch (mSettings.yesNo) {
            case YES:
                ((RadioButton) findViewById(R.id.settings_yesno_yes)).setChecked(true);
                break;
            case NO:
                ((RadioButton) findViewById(R.id.settings_yesno_no)).setChecked(true);
                break;
            case MAYBE:
                ((RadioButton) findViewById(R.id.settings_yesno_maybe)).setChecked(true);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        try {
            String serialized = Base64Serializer.serialize(mSettings);
            editor.putString(PREFS_KEY_SETTINGS, serialized);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        } finally {
            editor.commit();
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.settings_colors_red:
                mSettings = mSettings.updateColor(Settings.Colors.RED, checked);
                break;
            case R.id.settings_colors_green:
                mSettings = mSettings.updateColor(Settings.Colors.GREEN, checked);
                break;
            case R.id.settings_colors_blue:
                mSettings = mSettings.updateColor(Settings.Colors.BLUE, checked);
                break;
            default:
        }
    }

    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.settings_yesno_yes:
                mSettings = mSettings.updateYesNo(Settings.YesNo.YES);
                break;
            case R.id.settings_yesno_maybe:
                mSettings = mSettings.updateYesNo(Settings.YesNo.MAYBE);
                break;
            case R.id.settings_yesno_no:
                mSettings = mSettings.updateYesNo(Settings.YesNo.NO);
                break;
            default:
        }
    }
}