package com.github.tachesimazzoca.android.example.hello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // Receives the message via Intent.
        String message = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        TextView textView = (TextView) findViewById(R.id.text_message);
        textView.setText(message);

        Button button = (Button) findViewById(R.id.button_ok);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
