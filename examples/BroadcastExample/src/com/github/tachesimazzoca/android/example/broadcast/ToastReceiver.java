package com.github.tachesimazzoca.android.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ToastReceiver extends BroadcastReceiver {
    public static final String INTENT_TOAST =
            "com.github.tachesimazzoca.android.example.broadcast.TOAST";

    private final String mTag;

    public ToastReceiver(String tag) {
        mTag = tag;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(mTag, "onReceive: " + intent.getAction());

        Toast.makeText(context, mTag, Toast.LENGTH_SHORT).show();
    }
}
