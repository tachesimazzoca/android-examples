package com.github.tachesimazzoca.android.example.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StickyBroadcastActivity extends Activity {
    private static final String INTENT_STICKY =
            "com.github.tachesimazzoca.android.example.broadcast.STICKY";

    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_broadcast);

        Button bcBtn = (Button) findViewById(R.id.battery_capacity_button);
        bcBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // If you know the Intent your are registering for is sticky,
                // you can supply null for your receiver.
                Intent batteryStatus = registerReceiver(
                        null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                String msg = "Battery Status: "
                        + batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                toast(msg);
            }
        });

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = "Power Connected";
                if (isInitialStickyBroadcast())
                    msg += "(initial)";
                toast(msg);
            }
        }, new IntentFilter(Intent.ACTION_POWER_CONNECTED));

        // -----------------------------------------------------------------------------
        // DEPRECATED: Sticky broadcasts should not be used. These related
        // methods
        // were deprecated in API level 21.
        // -----------------------------------------------------------------------------
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                toast("Received a sticky broadcast");
            }
        };
        registerReceiver(mReceiver, new IntentFilter(INTENT_STICKY));

        Button setStickyBtn = (Button) findViewById(R.id.set_sticky_broadcast_button);
        setStickyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStickyBroadcast(new Intent(INTENT_STICKY));
            }
        });

        Button removeStickyBtn = (Button) findViewById(R.id.remove_sticky_broadcast_button);
        removeStickyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStickyBroadcast(new Intent(INTENT_STICKY));
            }
        });
        // -----------------------------------------------------------------------------
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
