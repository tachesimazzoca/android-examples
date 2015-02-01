package com.github.tachesimazzoca.android.example.contacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends CursorAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private static LayoutInflater mLayoutInflater;

    private static class Item {
        public final String displayName;
        public final String lookup;

        private Item(String displayName, String lookup) {
            this.displayName = displayName;
            this.lookup = lookup;
        }
    }

    private static class ViewHolder {
        public final TextView displayName;
        public final TextView lookup;

        private ViewHolder(TextView displayName, TextView lookup) {
            this.displayName = displayName;
            this.lookup = lookup;
        }
    }

    public ContactListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);

        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View newView = mLayoutInflater.inflate(
                R.layout.adapter_contact_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(
                (TextView) newView.findViewById(R.id.display_name),
                (TextView) newView.findViewById(R.id.lookup));
        newView.setTag(holder);
        return newView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.displayName.setText(
                cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME)));
        Uri uri = ContactsContract.Contacts.getLookupUri(
                cursor.getLong(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID)),
                cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.LOOKUP_KEY)));
        holder.lookup.setText(uri.toString());
    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        mItems.clear();
        if (null != newCursor) {
            while (newCursor.moveToNext()) {
                Uri uri = ContactsContract.Contacts.getLookupUri(
                        newCursor.getLong(newCursor.getColumnIndex(
                                ContactsContract.Contacts._ID)),
                        newCursor.getString(newCursor.getColumnIndex(
                                ContactsContract.Contacts.LOOKUP_KEY)));
                Item item = new Item(
                        newCursor.getString(newCursor.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME)),
                        uri.toString());
                mItems.add(item);
            }
            newCursor.moveToFirst();
        }
        return super.swapCursor(newCursor);
    }
}
