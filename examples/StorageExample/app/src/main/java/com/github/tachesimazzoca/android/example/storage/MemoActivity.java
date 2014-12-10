package com.github.tachesimazzoca.android.example.storage;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;

import java.io.File;

public class MemoActivity extends ActionBarActivity {
    private static final String TAG = "MemoActivity";
    private static final String FILENAME_MEMO = "memo.txt";
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        File f = getMemoAsFile();
        Log.i(TAG, "Loading " + f.getAbsolutePath());

        mEditText = (EditText) findViewById(R.id.memo_edit_text);
        if (f.isFile() && f.canWrite()) {
            mEditText.setText(IOUtils.readString(f));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        File f = getMemoAsFile();
        String str = mEditText.getText().toString();
        if (null == str || str.isEmpty()) {
            f.delete();
        } else {
            IOUtils.writeString(str, f);
        }
    }

    private File getMemoAsFile() {
        return new File(getExternalFilesDir(null).getAbsolutePath(), FILENAME_MEMO);
    }
}
