package com.github.tachesimazzoca.android.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PingReceiver extends BroadcastReceiver {
    public static final String INTENT_PING =
            "com.github.tachesimazzoca.android.example.broadcast.PING";

    private final String mTag;

    public PingReceiver(String tag) {
        mTag = tag;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(mTag, "onReceive: " + intent.getAction());

        String msg = String.format("%s%n", mTag);

        String data = getResultData();
        if (null == data)
            data = msg;
        else
            data = data + msg;
        setResultData(data);
    }
}
