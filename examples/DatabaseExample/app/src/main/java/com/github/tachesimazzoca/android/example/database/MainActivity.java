package com.github.tachesimazzoca.android.example.database;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    private static final int REQUEST_CODE_TASK_EDIT = 1;

    private TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.task_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                dispatchTaskEditIntent(id);
            }
        });

        mTaskAdapter = new TaskAdapter(getApplicationContext());
        lv.setAdapter(mTaskAdapter);
        mTaskAdapter.reload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_task:
                startActivityForResult(new Intent(this, TaskEditActivity.class),
                        REQUEST_CODE_TASK_EDIT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TASK_EDIT:
                if (resultCode == RESULT_OK)
                    mTaskAdapter.reload();
                break;
            default:
                break;
        }
    }

    private void dispatchTaskEditIntent(long id) {
        Intent intent = new Intent(this, TaskEditActivity.class);
        intent.putExtra(TaskEditActivity.EXTRA_TASK_ID, id);
        startActivityForResult(intent, REQUEST_CODE_TASK_EDIT);
    }
}
