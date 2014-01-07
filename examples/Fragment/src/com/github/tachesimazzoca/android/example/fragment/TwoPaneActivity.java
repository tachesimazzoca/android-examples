package com.github.tachesimazzoca.android.example.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TwoPaneActivity extends FragmentActivity implements
        ContentListFragment.ItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_pane);
    }

    public void onItemSelected(int position) {
        ContentFragment contentFrag = (ContentFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_fragment);
        contentFrag.updateDetailView(position);
    }
}
