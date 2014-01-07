package com.github.tachesimazzoca.android.example.storage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class MemoActivity extends Activity {
    private static final String TAG =
            (new Throwable()).getStackTrace()[0].getClassName();
    private static final String INTERNAL_FILENAME_MEMO = "memo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        List<String> filenames = Arrays.asList(fileList());
        if (filenames.contains(INTERNAL_FILENAME_MEMO)) {
            InputStreamReader isr = null;
            try {
                FileInputStream fis = openFileInput(INTERNAL_FILENAME_MEMO);
                isr = new InputStreamReader(fis, Charset.defaultCharset());
                StringBuilder sb = new StringBuilder();
                int chr;
                while ((chr = isr.read()) != -1) {
                    sb.append(String.format("%c", chr));
                }
                ((EditText) findViewById(R.id.edit_memo)).setText(sb.toString());
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            } finally {
                if (isr != null) { try { isr.close(); } catch (Exception e) {} }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        String memo = ((EditText) findViewById(R.id.edit_memo))
                .getText().toString();
        if (memo.isEmpty()) {
            deleteFile(INTERNAL_FILENAME_MEMO);
        } else {
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(INTERNAL_FILENAME_MEMO, MODE_PRIVATE);
                fos.write(memo.getBytes(Charset.defaultCharset()));
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            } finally {
                if (fos != null) { try { fos.close(); } catch (Exception e) {} }
            }
        }
    }
}
