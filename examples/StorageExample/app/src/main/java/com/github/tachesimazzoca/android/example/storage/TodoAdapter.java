package com.github.tachesimazzoca.android.example.storage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends BaseAdapter {
    private static final String NEWLINE = "\n";
    private static final String NEWLINE_REGEXP = "(\\r\\n|\\r|\\n)";
    private static final String FILENAME_TODO = "todo.dat";

    private WeakReference<Context> mContextRef;
    private final LayoutInflater mInflater;
    private final List<Todo> mItems;

    public class Todo {
        public final String content;

        public Todo(String content) {
            this.content = content;
        }
    }

    public TodoAdapter(Context context) {
        mContextRef = new WeakReference<Context>(context);
        mInflater = LayoutInflater.from(context);

        File f = new File(context.getFilesDir(), FILENAME_TODO);
        if (f.exists()) {
            String body = IOUtils.readString(f);
            String[] lines = body.split(NEWLINE_REGEXP);
            mItems = new ArrayList<Todo>(lines.length);
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].isEmpty())
                    continue;
                mItems.add(new Todo(lines[i]));
            }
        } else {
            mItems = new ArrayList<Todo>();
        }
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = mInflater.inflate(R.layout.layout_todo_item, null);
        }
        TextView contentView = (TextView) view.findViewById(R.id.todo_content_text_view);
        Todo item = (Todo) getItem(position);
        contentView.setText(item.content);
        return view;
    }

    public void addItem(String content) {
        mItems.add(new Todo(content));
        writeToFile();
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        mItems.remove(index);
        writeToFile();
        notifyDataSetChanged();
    }

    public void removeItems() {
        mItems.clear();
        writeToFile();
        notifyDataSetChanged();
    }

    private void writeToFile() {
        Context context = mContextRef.get();
        if (null == context)
            return;
        try {
            FileOutputStream f = context.openFileOutput(FILENAME_TODO, Context.MODE_PRIVATE);
            StringBuilder sb = new StringBuilder();
            for (Todo item : mItems) {
                sb.append(item.content);
                sb.append(NEWLINE);
            }
            IOUtils.writeString(sb.toString(), f);
        } catch (FileNotFoundException e) {
            // Fail gracefully
            e.printStackTrace();
        }
    }
}
