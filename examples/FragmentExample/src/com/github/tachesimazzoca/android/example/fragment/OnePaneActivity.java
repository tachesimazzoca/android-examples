package com.github.tachesimazzoca.android.example.fragment;

import android.app.Activity;
import android.os.Bundle;

public class OnePaneActivity extends Activity
        implements ContentListFragment.ItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pane);
        if (savedInstanceState == null) {
            ContentListFragment contentListFrag = new ContentListFragment();
            contentListFrag.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, contentListFrag)
                    .commit();
        }
    }

    public void onItemSelected(int position) {
        ContentFragment contentFrag = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(ContentFragment.ARG_POSITION, position);
        contentFrag.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, contentFrag)
                .addToBackStack(null)
                .commit();
    }
}
