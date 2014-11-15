package com.github.tachesimazzoca.android.example.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.util.Log;

public class DownloadActivity extends Activity {
    private static final String TAG =
            new Throwable().getStackTrace()[0].getClassName();

    private Button mButton;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mTextView = (TextView) findViewById(R.id.download_text_view);

        mProgressBar = (ProgressBar) findViewById(R.id.download_progress_bar);
        mProgressBar.setMax(100);

        mButton = (Button) findViewById(R.id.async_download_button);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
        mButton.setEnabled(true);
    }

    private void download() {
        // hide download button
        mButton.setEnabled(false);

        // show progress UIs
        mProgressBar.setProgress(0);
        mTextView.setText("Preparing to download ...");

        new DownloaderTask(new DownloaderTask.Listener() {
            @Override
            public void onProgress(DownloaderTask.Progress progress) {
                Log.i(TAG, "onProgress: " + progress);
                mTextView.setText("Downloading " + progress.filename);
                mProgressBar.setProgress((int) (progress.percent * 100));
            }

            @Override
            public void onResult(Long result) {
                mProgressBar.setProgress(0);
                mTextView.setText(String.format("Done after %,d msec.", result));
                mButton.setEnabled(true);
            }
        }).execute("a.txt", "b.txt");
    }
}
