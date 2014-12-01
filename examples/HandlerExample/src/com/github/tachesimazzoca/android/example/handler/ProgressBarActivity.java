package com.github.tachesimazzoca.android.example.handler;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarActivity extends Activity {
    private static final int MESSAGE_PROGRESS = 1;
    private static final int MESSAGE_TEXT = 2;

    private TextView mTextView;
    private ProgressBar mProgressBar;

    private Handler mHandler;

    private static class ProgressBarHandler extends Handler {
        private final WeakReference<ProgressBarActivity> mActivityRef;

        public ProgressBarHandler(ProgressBarActivity activity) {
            mActivityRef = new WeakReference<ProgressBarActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ProgressBarActivity activity = mActivityRef.get();
            if (null == activity)
                return;
            switch (msg.what) {
            case MESSAGE_PROGRESS:
                activity.getProgressBar().setProgress((Integer) msg.obj);
                break;
            case MESSAGE_TEXT:
                activity.getTextView().setText((String) msg.obj);
                break;
            default:
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        mHandler = new ProgressBarHandler(this);

        mTextView = (TextView) findViewById(R.id.message_text_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);

        Button postRunnableBtn = (Button) findViewById(R.id.post_runnable_button);
        postRunnableBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new RunnableTask()).start();
            }
        });

        Button sendMessageBtn = (Button) findViewById(R.id.send_message_button);
        sendMessageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MessageTask()).start();
            }
        });
    }

    public TextView getTextView() {
        return mTextView;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    private void waitFor(long msec) {
        try {
            Thread.sleep(msec);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private class RunnableTask implements Runnable {
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(0);
                    mTextView.setText("This message will be updated"
                            + " after the progress bar has finished.");
                }
            });

            for (int i = 0; i < 100; i++) {
                waitFor(50L);
                final int progress = i + 1;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setProgress(progress);
                    }
                });
            }

            final long DELAY = 3000L;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(
                            "Posted from Handler#postDelayed(Runnable)");
                }
            }, DELAY);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText("Posted from Handler#post(Runnable)."
                            + " This message will be updated"
                            + " via Handler#postDelayed after " + DELAY + "msec.");
                }
            });
        }
    }

    private class MessageTask implements Runnable {
        @Override
        public void run() {
            mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_PROGRESS, 0));
            mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_TEXT,
                    "This message will be updated after the progress bar has finished."));

            for (int i = 0; i < 100; i++) {
                waitFor(50L);
                mHandler.sendMessage(mHandler.obtainMessage(
                        MESSAGE_PROGRESS, i + 1));
            }

            final long DELAY = 3000L;
            mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_TEXT,
                    "Sent from Handler#sendMessageDelayed"), DELAY);

            mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_TEXT,
                    "Sent from Handler#sendMessage."
                            + " This message will be updated"
                            + " via Handler#sendMessageDelayed after " + DELAY + "msec."));
        }
    }
}
