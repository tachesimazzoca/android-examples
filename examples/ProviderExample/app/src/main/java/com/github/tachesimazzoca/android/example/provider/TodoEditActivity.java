package com.github.tachesimazzoca.android.example.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class TodoEditActivity extends ActionBarActivity {
    public static final String EXTRA_TODO_ID = "todo.id";

    private Long mTodoId;
    private int mLabelId;

    private EditText mContentEdit;
    private Spinner mLabelSpinner;
    private DatePicker mDueDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit);

        Long id = getIntent().getLongExtra(EXTRA_TODO_ID, 0);
        if (id > 0)
            mTodoId = id;
        else
            mTodoId = null;

        // mContentEdit
        mContentEdit = (EditText) findViewById(R.id.content_edit);

        // mLabelSpinner
        mLabelSpinner = (Spinner) findViewById(R.id.label_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_labels, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLabelSpinner.setAdapter(adapter);
        mLabelId = 0;
        mLabelSpinner.setSelection(mLabelId);
        mLabelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                mLabelId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mLabelId = 0;
            }
        });

        // mDueDatePicker
        mDueDatePicker = (DatePicker) findViewById(R.id.due_date_picker);

        // Fill the value of each UI if the ID is specified.
        if (null != mTodoId) {
            Cursor cursor = getContentResolver().query(
                    ContentUris.withAppendedId(TodoContract.Tasks.CONTENT_URI, mTodoId),
                    null, null, null, null);
            if (cursor.moveToNext()) {
                mContentEdit.setText(cursor.getString(cursor.getColumnIndex(
                        TodoContract.Tasks.CONTENT)));
                mLabelId = cursor.getInt(cursor.getColumnIndex(
                        TodoContract.Tasks.LABEL_ID));
                mLabelSpinner.setSelection(mLabelId);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(
                        TodoContract.Tasks.DUE_DATE)));
                mDueDatePicker.updateDate(
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
            } else {
                // The item does not exist.
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                save();
                return true;
            case R.id.delete:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {
        ContentValues values = new ContentValues();
        values.put(TodoContract.Tasks.LABEL_ID, mLabelId);
        values.put(TodoContract.Tasks.CONTENT, mContentEdit.getText().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(mDueDatePicker.getYear(), mDueDatePicker.getMonth(),
                mDueDatePicker.getDayOfMonth());
        values.put(TodoContract.Tasks.DUE_DATE, calendar.getTimeInMillis());
        ContentResolver resolver = getContentResolver();
        if (null == mTodoId) {
            resolver.insert(TodoContract.Tasks.CONTENT_URI, values);
        } else {
            resolver.update(
                    ContentUris.withAppendedId(TodoContract.Tasks.CONTENT_URI, mTodoId),
                    values, null, null);
        }
        finish();
    }

    private void delete() {
        if (null != mTodoId) {
            ContentResolver resolver = getContentResolver();
            resolver.delete(
                    ContentUris.withAppendedId(TodoContract.Tasks.CONTENT_URI, mTodoId),
                    null, null);
        }
        finish();
    }
}
