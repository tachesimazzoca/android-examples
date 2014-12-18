package com.github.tachesimazzoca.android.example.storage;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.util.Date;

public class CacheActivity extends ActionBarActivity {
    private static final String TAG = "CacheActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);

        File cache = new File(getCacheDir(), "cache.dat");
        String data;
        if (cache.isFile()) {
            Log.i(TAG, "Loading " + cache.getAbsolutePath());
            data = IOUtils.readString(cache);
        } else {
            data = "";
        }
        data = String.format("%s%n", new Date()) + data;
        IOUtils.writeString(data, cache);

        TextView textView = (TextView) findViewById(R.id.cache_text_view);
        textView.setText(data);
    }
}
