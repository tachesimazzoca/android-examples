package com.github.tachesimazzoca.android.example.storage;

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
           case R.id.action_settings:
               startActivity(new Intent(this, SettingsActivity.class));
               return true;
           case R.id.action_memo:
               startActivity(new Intent(this, MemoActivity.class));
               return true;
           case R.id.action_downloads:
               startActivity(new Intent(this, DownloadsActivity.class));
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }
}