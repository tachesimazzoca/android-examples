package com.github.tachesimazzoca.android.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BlockingReceiver extends BroadcastReceiver {
    public static final String INTENT_BLOCKING =
            "com.github.tachesimazzoca.android.example.broadcast.BLOCKING";

    private final String mTag;
    private final long mWait;

    public BlockingReceiver(String tag, long wait) {
        mTag = tag;
        mWait = wait;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(mTag, "onReceive: " + intent.getAction());
        try {
            Thread.sleep(mWait);
            Log.i(mTag, mWait + "msec later");
            Toast.makeText(context, mTag, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(mTag, e.getMessage());
        }
    }
}
