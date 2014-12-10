package com.github.tachesimazzoca.android.example.broadcast;

import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NormalBroadcastActivity extends Activity {
    private static final String TAG = "NormalBroadcastActivity";

    private static final int MAX_TOAST_RECEIVERS = 3;
    private static final int MAX_PING_RECEIVERS = 3;

    private TextView mTextView;
    private ToastReceiver[] mToastReceivers;
    private PingReceiver[] mPingReceivers;
    private BlockingReceiver mBlockingReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_broadcast);

        mTextView = (TextView) findViewById(R.id.result_text_view);

        Random rnd = new Random();

        mToastReceivers = new ToastReceiver[MAX_TOAST_RECEIVERS];
        for (int i = 0; i < MAX_TOAST_RECEIVERS; i++) {
            int priority = rnd.nextInt(21) - 10;
            mToastReceivers[i] = new ToastReceiver(
                    String.format("ToastReceiver[%d] priority: %d", i, priority));
            IntentFilter intentFilter = new IntentFilter(ToastReceiver.INTENT_TOAST);
            intentFilter.setPriority(priority);
            registerReceiver(mToastReceivers[i], intentFilter);
        }

        mPingReceivers = new PingReceiver[MAX_PING_RECEIVERS];
        for (int i = 0; i < MAX_PING_RECEIVERS; i++) {
            int priority = rnd.nextInt(21) - 10;
            mPingReceivers[i] = new PingReceiver(
                    String.format("PingReceiver[%d] priority: %d", i, priority));
            IntentFilter intentFilter = new IntentFilter(PingReceiver.INTENT_PING);
            intentFilter.setPriority(priority);
            registerReceiver(mPingReceivers[i], intentFilter);
        }

        mBlockingReceiver = new BlockingReceiver("BlockingReceiver 3sec", 3000L);
        IntentFilter intentFilter = new IntentFilter(BlockingReceiver.INTENT_BLOCKING);
        registerReceiver(mBlockingReceiver, intentFilter);

        Button normalBtn = (Button) findViewById(R.id.send_broadcast_button);
        normalBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("");
                sendBroadcast(new Intent(ToastReceiver.INTENT_TOAST));
            }
        });

        Button orderedBtn = (Button) findViewById(R.id.send_ordered_broadcast_button);
        orderedBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("");
                sendOrderedBroadcast(new Intent(PingReceiver.INTENT_PING), null,
                        new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context content, Intent intent) {
                                String msg = getResultData();
                                if (null != msg)
                                    mTextView.setText(msg);
                            }
                        }, null, 0, null, null);
            }
        });

        Button blockingBtn = (Button) findViewById(R.id.send_blocking_broadcast_button);
        blockingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("Receiver#onReceive runs on the UI thread."
                        + " Consider starting a Service.");
                sendBroadcast(new Intent(BlockingReceiver.INTENT_BLOCKING));
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");

        for (int i = 0; i < mToastReceivers.length; i++) {
            unregisterReceiver(mToastReceivers[i]);
        }
        for (int i = 0; i < mPingReceivers.length; i++) {
            unregisterReceiver(mPingReceivers[i]);
        }
        unregisterReceiver(mBlockingReceiver);

        super.onDestroy();
    }
}
