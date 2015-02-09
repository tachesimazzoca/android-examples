package com.github.tachesimazzoca.android.example.web;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebClientActivity extends ActionBarActivity {
    private static final String TAG = WebClientActivity.class.getSimpleName();

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_client);

        mWebView = (WebView) findViewById(R.id.web);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, url);
                return false;
            }
        });
        if (null == savedInstanceState) {
            mWebView.loadUrl("http://developer.android.com/index.html");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                if (mWebView.canGoBack())
                    mWebView.goBack();
                return true;
            case R.id.action_forward:
                if (mWebView.canGoForward())
                    mWebView.goForward();
                return true;
            case R.id.action_reload:
                mWebView.reload();
                return true;
            case R.id.action_cancel:
                mWebView.stopLoading();
                return true;
            case R.id.action_clear_cache:
                mWebView.clearCache(true);
                Toast.makeText(getApplicationContext(),
                        "Cleared the cache for all WebViews used.",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // WebView

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null != mWebView)
            mWebView.restoreState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (null != mWebView)
            mWebView.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
