package com.github.tachesimazzoca.android.example.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SubActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub);
    }

    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.message_edit_text);
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
