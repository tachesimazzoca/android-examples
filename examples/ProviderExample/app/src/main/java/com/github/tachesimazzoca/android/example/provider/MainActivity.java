package com.github.tachesimazzoca.android.example.provider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private TodoListAdapter mTodoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.todo_list_view);
        mTodoListAdapter = new TodoListAdapter(
                getApplicationContext(), null, 0);
        lv.setAdapter(mTodoListAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                dispatchTodoEditIntent(id);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void dispatchTodoEditIntent(long id) {
        Intent intent = new Intent(this, TodoEditActivity.class);
        intent.putExtra(TodoEditActivity.EXTRA_TODO_ID, id);
        startActivity(intent);
    }

    // Action bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                startActivity(new Intent(this, TodoEditActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // LoaderCallbacks

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = new CursorLoader(this,
                TodoContract.Tasks.CONTENT_URI,
                null, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mTodoListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mTodoListAdapter.swapCursor(null);
    }
}
