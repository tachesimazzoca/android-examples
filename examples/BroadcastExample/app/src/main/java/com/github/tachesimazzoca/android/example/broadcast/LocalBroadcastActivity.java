package com.github.tachesimazzoca.android.example.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LocalBroadcastActivity extends Activity {
    private static final String TAG = "LocalBroadcastActivity";

    private ToastReceiver mToastReceiver;
    private LocalBroadcastManager mBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broadcast);

        mBroadcast = LocalBroadcastManager.getInstance(
                getApplicationContext());

        mToastReceiver = new ToastReceiver("ToastReceiver(LocalBroadcast)");
        IntentFilter toastIntentFilter = new IntentFilter(ToastReceiver.INTENT_TOAST);
        toastIntentFilter.setPriority(10);
        mBroadcast.registerReceiver(mToastReceiver, toastIntentFilter);

        Button btn = (Button) findViewById(R.id.send_local_broadcast_button);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBroadcast.sendBroadcast(new Intent(ToastReceiver.INTENT_TOAST));
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");

        mBroadcast.unregisterReceiver(mToastReceiver);

        super.onDestroy();
    }
}
