package com.github.tachesimazzoca.android.example.storage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        File picturesDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        List<File> fileList = Arrays.asList(picturesDir.listFiles());
        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        for (File f : fileList) {
            Map<String, String> row = new HashMap<String, String>();
            row.put("text1", f.getName());
            row.put("text2", new Date(f.lastModified()).toString());
            rowList.add(row);
        }
        SimpleAdapter adapter = new SimpleAdapter(
            this,
            rowList,
            android.R.layout.simple_list_item_2,
            new String[] {"text1", "text2"},
            new int[] {android.R.id.text1, android.R.id.text2}
        );
        ListView downloadsView = (ListView) findViewById(R.id.list_downloads);
        downloadsView.setAdapter(adapter);
    }
}