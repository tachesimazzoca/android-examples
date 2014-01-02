package com.github.tachesimazzoca.android.example.lifecycle;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final String TAG = (new Throwable().getStackTrace()[0].getClassName());
    private static final String STATE_LIFECYCLE_LOG = "lifecycleLog";
    private static final int MAX_LIFECYCLE_LOG_LINES = 25;

    private ArrayList<String> mLifecycleLog = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restoreLifecycleLog(savedInstanceState);
        appendLifecycleLog("onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreLifecycleLog(savedInstanceState);
        appendLifecycleLog("onRestoreInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        appendLifecycleLog("onSaveInstanceState");
        savedInstanceState.putStringArrayList(STATE_LIFECYCLE_LOG, mLifecycleLog);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void restoreLifecycleLog(Bundle savedInstanceState) {
        mLifecycleLog.clear();
        if (savedInstanceState != null) {
            ArrayList<String> lines = savedInstanceState.getStringArrayList(STATE_LIFECYCLE_LOG);
            if (lines != null) {
                for (String line : lines) {
                    mLifecycleLog.add(new String(line));
                }
            }
        }
    }

    private void appendLifecycleLog(String line) {
        mLifecycleLog.add(String.format("%d %s", System.currentTimeMillis(), line));
        while (!mLifecycleLog.isEmpty() &&
                mLifecycleLog.size() > MAX_LIFECYCLE_LOG_LINES) {
            mLifecycleLog.remove(0);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = mLifecycleLog.size() - 1; i >= 0; i--) {
            sb.append(mLifecycleLog.get(i));
            sb.append(System.getProperty("line.separator"));
        }
        TextView logTextView = (TextView) findViewById(R.id.lifecycle_log_text);
        if (logTextView != null)
            logTextView.setText(sb.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        appendLifecycleLog("onStart");
    }

    @Override
    protected void onRestart() {
        super.onStart();
        appendLifecycleLog("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        appendLifecycleLog("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        appendLifecycleLog("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        appendLifecycleLog("onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}