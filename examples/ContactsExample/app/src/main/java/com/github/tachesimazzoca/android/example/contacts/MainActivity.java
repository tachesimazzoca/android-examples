package com.github.tachesimazzoca.android.example.contacts;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivity";
    private static final int MAX_ITEMS = 10;

    private ContactListAdapter mContactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mContactListAdapter = new ContactListAdapter(getApplicationContext(), null, 0);
        ListView lv = (ListView) findViewById(R.id.contacts_list_view);
        lv.setAdapter(mContactListAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.i(TAG, "onCreateLoader");
        CursorLoader loader = new CursorLoader(this,
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.LOOKUP_KEY,
                        ContactsContract.Contacts.DISPLAY_NAME},
                null, null, ContactsContract.Contacts._ID + " LIMIT 0," + MAX_ITEMS);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.i(TAG, "onLoadFinished");
        mContactListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        Log.i(TAG, "onLoaderReset");
        mContactListAdapter.swapCursor(null);
    }
}
