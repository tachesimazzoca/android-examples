package com.github.tachesimazzoca.android.example.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private Class<?>[] MENU_ACTIVITIES = {
            ProgressBarActivity.class,
            CounterActivity.class };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.main_list_view);
        String[] items = new String[MENU_ACTIVITIES.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = MENU_ACTIVITIES[i].getSimpleName();
        }
        lv.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, items));
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                navigateTo(position);
            }
        });
    }

    private void navigateTo(int position) {
        startActivity(new Intent(this, MENU_ACTIVITIES[position]));
    }
}
