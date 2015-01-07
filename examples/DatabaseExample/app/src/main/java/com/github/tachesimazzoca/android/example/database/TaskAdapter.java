package com.github.tachesimazzoca.android.example.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private final List<Task> mItems;
    private final LayoutInflater mInflater;
    private final TaskRepository mRepository;
    private final String[] mLabels;
    private int mLabelId;

    public class ViewHolder {
        public final TextView mContentView;
        public final TextView mDueDateView;

        public ViewHolder(TextView mContentView,
                          TextView mDueDateView) {
            this.mContentView = mContentView;
            this.mDueDateView = mDueDateView;
        }
    }

    public TaskAdapter(Context context) {
        this(context, Task.LABEL_NONE);
    }

    public TaskAdapter(Context context, int labelId) {
        mInflater = LayoutInflater.from(context);
        mRepository = new TaskRepository(context);
        mItems = new ArrayList<Task>();
        mLabels = context.getResources().getStringArray(R.array.task_labels);
        mLabelId = labelId;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        Task item = mItems.get(position);
        if (null != item)
            return item.id;
        else
            return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (null == view) {
            view = mInflater.inflate(R.layout.adapter_todo_task_list, null);
            holder = new ViewHolder(
                    (TextView) view.findViewById(R.id.task_content),
                    (TextView) view.findViewById(R.id.task_due_date));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Task item = (Task) getItem(position);
        holder.mContentView.setText(item.content);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        holder.mDueDateView.setText(df.format(new Date(item.dueDate)));
        return view;
    }

    public void reload() {
        reload(mLabelId);
    }

    public void reload(int labelId) {
        mItems.clear();
        Cursor cursor;
        mLabelId = labelId;
        cursor = mRepository.findAll(mLabelId);
        while (cursor.moveToNext()) {
            mItems.add(TaskRepository.fromCursor(cursor));
        }
        notifyDataSetChanged();
    }
}
