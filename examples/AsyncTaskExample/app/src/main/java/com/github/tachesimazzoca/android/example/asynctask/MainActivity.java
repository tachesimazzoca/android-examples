package com.github.tachesimazzoca.android.example.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_item_download:
            startActivity(new Intent(this, DownloadActivity.class));
            return true;
        case R.id.menu_item_mean:
            startActivity(new Intent(this, MeanActivity.class));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
