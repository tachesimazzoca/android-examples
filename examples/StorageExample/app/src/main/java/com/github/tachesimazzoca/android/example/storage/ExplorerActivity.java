package com.github.tachesimazzoca.android.example.storage;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.File;

public class ExplorerActivity extends ActionBarActivity
        implements FileListFragment.OnFragmentInteractionListener {
    private static final String TAG = "ExplorerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        if (null == savedInstanceState) {
            File storageDir = Environment.getExternalStorageDirectory();
            String path = storageDir.getAbsolutePath();
            FileListFragment fileListFrag = FileListFragment.newInstance(path);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fileListFrag)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(File item) {
        Log.i(TAG, "selected: " + item.getAbsolutePath());
        if (!item.isDirectory())
            return;
        String path = item.getAbsolutePath();
        FileListFragment fileListFrag = FileListFragment.newInstance(path);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fileListFrag)
                .addToBackStack(null)
                .commit();
    }
}
