package com.github.tachesimazzoca.android.example.handler;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity {
    private static final String TAG = "CounterActivity";
    private static final String LF = String.format("%n");

    private static final int MESSAGE_INCREMENT = 1;
    private static final int MESSAGE_FINISHED = 2;

    private TextView mTextView;
    private Counter mCounter;

    private static class Counter {
        private int value;

        public Counter() {
            value = 0;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    private static class CounterHandler extends Handler {
        private WeakReference<CounterActivity> mActivityRef;

        public CounterHandler(CounterActivity activity) {
            mActivityRef = new WeakReference<CounterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CounterActivity activity = mActivityRef.get();
            if (null == activity)
                return;
            Counter counter = activity.getCounter();
            switch (msg.what) {
            case MESSAGE_INCREMENT:
                counter.setValue(counter.getValue() + 1);
                Log.i(TAG, "count: " + counter.getValue());
                break;
            case MESSAGE_FINISHED:
                String tag = (String) msg.obj;
                activity.addSummary(tag + ": " + counter.getValue());
                break;
            default:
                break;
            }
        }
    }

    private static class CounterThread extends Thread {
        private Handler mHandler;
        private CounterActivity mActivity;

        public CounterThread(CounterActivity activity) {
            mActivity = activity;
        }

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new CounterHandler(mActivity);
            Looper.loop();
        }

        public Handler getHandler() {
            return mHandler;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        mTextView = (TextView) findViewById(R.id.count_text_view);
        mCounter = new Counter();

        Button nsfBtn = (Button) findViewById(R.id.non_thread_safe_button);
        nsfBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCounter.setValue(0);
                mTextView.setText("Running ..." + LF);
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100000; i++) {
                            mCounter.setValue(mCounter.getValue() + 1);
                        }
                        addSummary(Thread.currentThread().getName() + ": " + mCounter.getValue());
                    }
                };
                new Thread(task).start();
                new Thread(task).start();
            }
        });

        final CounterThread looperThread = new CounterThread(CounterActivity.this);
        looperThread.start();

        Button looperBtn = (Button) findViewById(R.id.count_via_handler_button);
        looperBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCounter.setValue(0);
                mTextView.setText("Running ..." + LF);
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = looperThread.getHandler();
                        for (int i = 0; i < 100; i++) {
                            Message msg = handler.obtainMessage(MESSAGE_INCREMENT);
                            handler.sendMessage(msg);
                        }
                        handler.sendMessage(handler.obtainMessage(MESSAGE_FINISHED,
                                Thread.currentThread().getName()));
                    }
                };
                for (int n = 1; n <= 10; n++) {
                    new Thread(task).start();
                }
            }
        });
    }

    protected Counter getCounter() {
        return mCounter;
    }

    protected void addSummary(final String line) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(mTextView.getText() + line + LF);
            }
        });
    }
}
