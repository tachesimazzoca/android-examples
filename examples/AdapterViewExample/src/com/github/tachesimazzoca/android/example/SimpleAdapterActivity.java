package com.github.tachesimazzoca.android.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SimpleAdapterActivity extends Activity {
    private final ContentRepository mContentRepository;
   
    public SimpleAdapterActivity() {
        mContentRepository = new ContentRepository();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);

        ListView lv = (ListView) findViewById(R.id.simple_adapter_list_view);
        lv.setAdapter(new SimpleAdapter(this, mContentRepository.getItems(),
                android.R.layout.simple_list_item_2,
                new String[] { ContentRepository.KEY_TITLE, ContentRepository.KEY_DESCRIPTION },
                new int[] { android.R.id.text1, android.R.id.text2 }));

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                toast("position:" + position);
            }
        });
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
