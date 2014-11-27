package com.github.tachesimazzoca.android.example.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private Class<?>[] MENU_ACTIVITIES = {
            NormalBroadcastActivity.class,
            LocalBroadcastActivity.class, };

    private static final String TAG = "MainActivity";

    private ToastReceiver mToastReceiver;
    private PingReceiver mPingReceiver;

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

        mToastReceiver = new ToastReceiver("MainActivity.ToastReceiver priority: 0");
        IntentFilter toastIntentFilter = new IntentFilter(ToastReceiver.INTENT_TOAST);
        toastIntentFilter.setPriority(0);
        registerReceiver(mToastReceiver, toastIntentFilter);

        mPingReceiver = new PingReceiver("MainActivity.PingReceiver priority: 0");
        IntentFilter pingIntentFilter = new IntentFilter(PingReceiver.INTENT_PING);
        pingIntentFilter.setPriority(0);
        registerReceiver(mPingReceiver, pingIntentFilter);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");

        if (null != mToastReceiver)
            unregisterReceiver(mToastReceiver);
        if (null != mPingReceiver)
            unregisterReceiver(mPingReceiver);

        super.onDestroy();
    }

    private void navigateTo(int position) {
        startActivity(new Intent(this, MENU_ACTIVITIES[position]));
    }
}
