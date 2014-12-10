package com.github.tachesimazzoca.android.example.intent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class MainActivity extends Activity {
    private final static int REQUEST_CODE_EDIT_MESSAGE = 1;

    private TextView mMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageTextView = (TextView) findViewById(R.id.message_text_view);
    }

    private void safeStartActivity(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void sendUrl(View view) {
        String url = ((EditText) findViewById(R.id.url_edit_text)).getText().toString();
        if (!url.isEmpty()) {
            safeStartActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    public void sendGeo(View view) throws UnsupportedEncodingException {
        String url = ((EditText) findViewById(R.id.geo_edit_text)).getText().toString();
        if (!url.isEmpty()) {
            url = "geo:0,0?q=" + URLEncoder.encode(url, Charset.defaultCharset().name());
            safeStartActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    public void sendPlainText(View view) {
        String txt = ((EditText) findViewById(R.id.plain_text_edit_text)).getText().toString();
        if (!txt.isEmpty()) {
            Intent txtIntent = new Intent(Intent.ACTION_SEND);
            txtIntent.putExtra(Intent.EXTRA_TEXT, txt);
            txtIntent.setType("text/plain");
            String title = getResources().getString(R.string.share_text_to);
            Intent chooser = Intent.createChooser(txtIntent, title);
            safeStartActivity(chooser);
        }
    }

    public void editMessage(View view) {
        startActivityForResult(new Intent(this, SubActivity.class), REQUEST_CODE_EDIT_MESSAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EDIT_MESSAGE) {
            if (resultCode == RESULT_OK) {
                mMessageTextView.setText(data.getStringExtra(Intent.EXTRA_TEXT));
            }
        }
    }
}
