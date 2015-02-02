package com.github.tachesimazzoca.android.example.provider;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListAdapter extends CursorAdapter {
    private static LayoutInflater mLayoutInflater;
    private final List<Item> mItems = new ArrayList<Item>();

    private static class Item {
        public final long id;
        public final int labelId;
        public final String content;
        public final Date dueDate;

        private Item(long id, int labelId, String content, Date dueDate) {
            this.id = id;
            this.labelId = labelId;
            this.content = content;
            this.dueDate = dueDate;
        }

        public static Item fromCursor(Cursor cursor) {
            return new Item(
                    cursor.getLong(cursor.getColumnIndex(
                            TodoContract.Tasks._ID)),
                    cursor.getInt(cursor.getColumnIndex(
                            TodoContract.Tasks.LABEL_ID)),
                    cursor.getString(cursor.getColumnIndex(
                            TodoContract.Tasks.CONTENT)),
                    new Date(cursor.getLong(cursor.getColumnIndex(
                            TodoContract.Tasks.DUE_DATE))));
        }
    }

    private static class ViewHolder {
        public final TextView content;
        public final TextView dueDate;

        private ViewHolder(TextView content, TextView dueDate) {
            this.content = content;
            this.dueDate = dueDate;
        }
    }

    public TodoListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);

        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View newView = mLayoutInflater.inflate(R.layout.adapter_todo_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(
                (TextView) newView.findViewById(R.id.content),
                (TextView) newView.findViewById(R.id.due_date));
        newView.setTag(holder);
        return newView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        Item item = Item.fromCursor(cursor);
        holder.content.setText(item.content);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        holder.dueDate.setText(df.format(item.dueDate));
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        mItems.clear();
        if (null != newCursor) {
            while (newCursor.moveToNext()) {
                mItems.add(Item.fromCursor(newCursor));
            }
            newCursor.moveToFirst();
        }
        return super.swapCursor(newCursor);
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
        Item item = mItems.get(position);
        if (null != item)
            return item.id;
        else
            return 0;
    }
}
