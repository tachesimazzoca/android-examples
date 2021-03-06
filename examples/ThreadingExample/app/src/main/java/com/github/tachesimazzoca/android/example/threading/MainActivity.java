package com.github.tachesimazzoca.android.example.threading;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageView = (TextView) findViewById(R.id.message_text_view);

        Button uiThreadBtn = (Button) findViewById(R.id.block_ui_thread_button);
        uiThreadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                waitFor(5000L);
                mMessageView.setText("Running on the UI thread blocks all operations."
                        + " It's an infeasible solution.");
            }
        });

        Button anotherThreadBtn = (Button) findViewById(R.id.another_thread_button);
        anotherThreadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageView.setText("Waiting for the task on another thread."
                        + " But it's going to fail.");
                new Thread(new Runnable() {
                    public void run() {
                        waitFor(5000L);
                        mMessageView.setText("This doesn't work.");
                    }
                }).start();
            }
        });

        Button runOnUiThreadBtn = (Button) findViewById(R.id.run_on_ui_thread_button);
        runOnUiThreadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageView.setText("Waiting for the task on another thread."
                        + " This message will be updated via Activity#runOnUiThread.");
                new Thread(new Runnable() {
                    public void run() {
                        waitFor(5000L);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                mMessageView.setText("Posted from Activity#runOnUiThread.");
                            }
                        });
                    }
                }).start();
            }
        });

        Button postRunnableBtn = (Button) findViewById(R.id.post_runnable_button);
        postRunnableBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageView.setText("Waiting for the task on another thread."
                        + " This message will be updated via View#post.");
                new Thread(new Runnable() {
                    public void run() {
                        waitFor(5000L);
                        mMessageView.post(new Runnable() {
                            public void run() {
                                mMessageView.setText("Posted from View#post.");
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void waitFor(long msec) {
        try {
            Thread.sleep(msec);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
