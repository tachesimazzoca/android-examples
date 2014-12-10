package com.github.tachesimazzoca.android.example.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MeanActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean);

        Button btn = (Button) findViewById(R.id.mean_button);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mean();
            }
        });
    }

    private void mean() {
        final TextView textView = (TextView) findViewById(R.id.mean_text_view);

        Integer[] params = new Integer[10];
        for (int i = 0; i < 10; i++) {
            params[i] = i + 1;
        }

        new MeanTask(new MeanTask.Listener() {
            @Override
            public void onProgress(String message) {
                textView.setText(message);
            }

            @Override
            public void onResult(Float result) {
                textView.setText(String.format("mean: %.02f", result));
            }
        }).execute(params);
    }
}
