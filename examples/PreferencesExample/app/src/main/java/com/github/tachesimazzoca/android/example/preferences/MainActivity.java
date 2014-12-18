package com.github.tachesimazzoca.android.example.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    private Class<?>[] MENU_ACTIVITIES = {
            SettingsActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = new String[MENU_ACTIVITIES.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = MENU_ACTIVITIES[i].getSimpleName();
        }

        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                startActivity(new Intent(MainActivity.this, MENU_ACTIVITIES[position]));
            }
        });
        setContentView(listView);
    }
}

