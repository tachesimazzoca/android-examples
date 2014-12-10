package com.github.tachesimazzoca.android.example.fragment;

import android.app.Activity;
import android.os.Bundle;

public class TwoPaneActivity extends Activity implements
        ContentListFragment.ItemSelectedListener {

    private ContentFragment mContentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_pane);
        mContentFragment = (ContentFragment) getFragmentManager()
                .findFragmentById(R.id.content_fragment);
    }

    public void onItemSelected(int position) {
        mContentFragment.updateDetailView(position);
    }
}
