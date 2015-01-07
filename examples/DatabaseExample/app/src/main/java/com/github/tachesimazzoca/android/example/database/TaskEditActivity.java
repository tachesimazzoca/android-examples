package com.github.tachesimazzoca.android.example.database;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.NoSuchElementException;

public class TaskEditActivity extends ActionBarActivity {
    public static final String EXTRA_TASK_ID = "task.id";

    private Long mTaskId;
    private int mLabelId;
    private TaskRepository mRepository;
    private TextView mContentEdit;
    private Spinner mLabelSpinner;
    private DatePicker mDueDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        // Set the specified ID via the intent. Create a new
        // task if the ID is 0.
        Long id = getIntent().getLongExtra(EXTRA_TASK_ID, 0);
        if (id > 0)
            mTaskId = id;
        else
            mTaskId = null;

        mRepository = new TaskRepository(getApplicationContext());
        // mContentEdit
        mContentEdit = (TextView) findViewById(R.id.task_content_edit);
        // mLabelSpinner
        mLabelSpinner = (Spinner) findViewById(R.id.task_label_spinner);
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
        mDueDatePicker = (DatePicker) findViewById(R.id.task_due_date_picker);

        if (null != mTaskId) {
            // Fill the values of the task to update.
            try {
                Task task = mRepository.find(mTaskId);
                mLabelId = task.labelId;
                mLabelSpinner.setSelection(mLabelId);
                mContentEdit.setText(task.content);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(task.dueDate);
                mDueDatePicker.updateDate(
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

            } catch (NoSuchElementException e) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_task_save:
                save();
                return true;
            case R.id.menu_task_delete:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mDueDatePicker.getYear(), mDueDatePicker.getMonth(),
                mDueDatePicker.getDayOfMonth());
        mRepository.save(new Task(mTaskId, mLabelId,
                mContentEdit.getText().toString(), calendar.getTimeInMillis()));
        setResult(RESULT_OK);
        finish();
    }

    private void delete() {
        if (null != mTaskId)
            mRepository.delete(mTaskId);
        setResult(RESULT_OK);
        finish();
    }
}
