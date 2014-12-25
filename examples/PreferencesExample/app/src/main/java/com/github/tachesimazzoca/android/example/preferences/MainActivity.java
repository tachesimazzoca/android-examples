package com.github.tachesimazzoca.android.example.preferences;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    private static Class<?>[] MENU_ACTIVITIES = {
            MaterialSettingsActivity.class,
            LegacySettingsActivity.class
    };
    private static Class<?>[] MENU_ACTIVITIES_LEGACY = {
            LegacySettingsActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Class<?>[] activities;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            activities = MENU_ACTIVITIES_LEGACY;
        } else {
            activities = MENU_ACTIVITIES;
        }

        String[] labels = new String[activities.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = activities[i].getSimpleName();
        }

        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, labels));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                startActivity(new Intent(MainActivity.this, activities[position]));
            }
        });
        setContentView(listView);
    }
}

