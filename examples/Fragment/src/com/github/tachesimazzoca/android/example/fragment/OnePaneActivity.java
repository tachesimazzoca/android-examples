package com.github.tachesimazzoca.android.example.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class OnePaneActivity extends FragmentActivity
        implements ContentListFragment.ItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pane);
        if (savedInstanceState == null) {
            ContentListFragment contentListFrag = new ContentListFragment();
            contentListFrag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, contentListFrag)
                    .commit();
        }
    }
    
    public void onItemSelected(int position) {
        ContentFragment contentFrag = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(ContentFragment.ARG_POSITION, position);
        contentFrag.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, contentFrag)
                .addToBackStack(null)
                .commit();
    }
}
