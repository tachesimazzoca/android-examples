package com.github.tachesimazzoca.android.example.intent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void interactByURIString(String uriString) {
        Uri location = Uri.parse(uriString);
        Intent uriIntent = new Intent(Intent.ACTION_VIEW, location);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(uriIntent, 0);
        if (activities.size() > 0) {
            startActivity(uriIntent);
        }
    }

    public void sendUrl(View view) {
        String url = ((EditText) findViewById(R.id.edit_url)).getText().toString();
        if (!url.isEmpty()) {
            interactByURIString(url);
        }
    }

    public void sendGeo(View view) throws UnsupportedEncodingException {
        String url = ((EditText) findViewById(R.id.edit_geo)).getText().toString();
        if (!url.isEmpty()) {
            interactByURIString("geo:0,0?q=" + URLEncoder.encode(url, Charset.defaultCharset().name()));
        }
    }

    public void sendPlainText(View view) {
        String txt = ((EditText) findViewById(R.id.edit_plain_text)).getText().toString();
        if (!txt.isEmpty()) {
            Intent txtIntent = new Intent(Intent.ACTION_SEND);
            txtIntent.putExtra(Intent.EXTRA_TEXT, txt);
            txtIntent.setType("text/plain");
            String title = getResources().getString(R.string.chooser_share_text);
            Intent chooser = Intent.createChooser(txtIntent, title);
            if (txtIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
        }
    }
}
