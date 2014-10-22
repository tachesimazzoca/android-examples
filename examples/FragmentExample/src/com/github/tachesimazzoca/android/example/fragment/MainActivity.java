package com.github.tachesimazzoca.android.example.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements
        ContentListFragment.ItemSelectedListener {

    private enum LayoutType {
        ONE_PANE(false),
        TWO_PANE(true);

        private boolean frameLayout;

        private LayoutType(boolean frameLayout) {
            this.frameLayout = frameLayout;
        }

        public boolean isFrameLayout() {
            return frameLayout;
        }
    }

    private LayoutType layoutType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        layoutType = (findViewById(R.id.fragment_container) != null)
                ? LayoutType.ONE_PANE : LayoutType.TWO_PANE;

        if (savedInstanceState != null)
            return;
        if (!layoutType.isFrameLayout()) {
            ContentListFragment contentListFrag = new ContentListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, contentListFrag).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_one_pane:
            startActivity(new Intent(this, OnePaneActivity.class));
            return true;
        case R.id.action_two_pane:
            startActivity(new Intent(this, TwoPaneActivity.class));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void onItemSelected(int position) {
        if (!layoutType.isFrameLayout()) {
            ContentFragment contentFrag = new ContentFragment();
            Bundle args = new Bundle();
            args.putInt(ContentFragment.ARG_POSITION, position);
            contentFrag.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, contentFrag)
                    .addToBackStack(null).commit();
        } else {
            ContentFragment contentFrag = (ContentFragment) getFragmentManager()
                    .findFragmentById(R.id.content_fragment);
            contentFrag.updateDetailView(position);
        }
    }
}
