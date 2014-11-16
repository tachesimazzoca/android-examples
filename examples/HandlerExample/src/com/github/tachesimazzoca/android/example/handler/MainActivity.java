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

public class MainActivity extends Activity {
    private static final int MESSAGE_PROGRESS = 1;
    private static final int MESSAGE_TEXT = 2;

    private TextView mTextView;
    private ProgressBar mProgressBar;

    private Handler mRunnableHandler;
    private Handler mMessageHandler;

    private static class MessageHandler extends Handler {
        private final WeakReference<MainActivity> mActivityRef;

        public MessageHandler(MainActivity activity) {
            mActivityRef = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivityRef.get();
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
        setContentView(R.layout.activity_main);

        if (null == mRunnableHandler)
            mRunnableHandler = new Handler();
        if (null == mMessageHandler)
            mMessageHandler = new MessageHandler(this);

        mTextView = (TextView) findViewById(R.id.message_text_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);

        Button postRunnableBtn = (Button) findViewById(R.id.post_runnable_button);
        postRunnableBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doRunnableTask();
            }
        });

        Button sendMessageBtn = (Button) findViewById(R.id.send_message_button);
        sendMessageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doMessageTask();
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

    private void doRunnableTask() {
        mProgressBar.setProgress(0);
        mTextView.setText("Waiting for the task on another thead."
                + " This message will be update via Hander#post.");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRunnableHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("This message will be updated"
                                + " after the progress bar has finished.");
                    }
                });

                for (int i = 0; i < 100; i++) {
                    waitFor(50L);
                    final int progress = i + 1;
                    mRunnableHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(progress);
                        }
                    });
                }

                final long DELAY = 3000L;
                mRunnableHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(
                                "Posted from Handler#postDelayed(Runnable)");
                    }
                }, DELAY);

                mRunnableHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("Posted from Handler#post(Runnable)."
                                + " This message will be updated"
                                + " via Handler#postDelayed after " + DELAY + "msec.");
                    }
                });
            }
        }).start();
    }

    private void doMessageTask() {
        mProgressBar.setProgress(0);
        mTextView.setText("Waiting for the task on another thead."
                + " This message will be update via Hander#sendMessage");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMessageHandler.sendMessage(mMessageHandler.obtainMessage(MESSAGE_TEXT,
                        "This message will be updated after the progress bar has finished."));

                for (int i = 0; i < 100; i++) {
                    waitFor(50L);
                    mMessageHandler.sendMessage(mMessageHandler.obtainMessage(
                            MESSAGE_PROGRESS, i + 1));
                }

                final long DELAY = 3000L;
                mMessageHandler.sendMessageDelayed(mMessageHandler.obtainMessage(MESSAGE_TEXT,
                        "Sent from Handler#sendMessageDelayed"), DELAY);

                mMessageHandler.sendMessage(mMessageHandler.obtainMessage(MESSAGE_TEXT,
                        "Sent from Handler#sendMessage."
                                + " This message will be updated"
                                + " via Handler#sendMessageDelayed after " + DELAY + "msec."));
            }
        }).start();
    }
}
