package com.github.tachesimazzoca.android.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements
        ContentListFragment.ItemSelectedListener {
    private enum LayoutType {
        ONE_PANE {
            boolean isFrameLayout() {
                return true;
            }
        },
        TWO_PANE {
            boolean isFrameLayout() {
                return false;
            }
        };

        abstract boolean isFrameLayout();
    }

    private LayoutType layoutType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isFrameLayout()) {
            if (savedInstanceState == null) {
                ContentListFragment contentListFrag = new ContentListFragment();
                contentListFrag.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, contentListFrag).commit();
            }
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
        if (isFrameLayout()) {
            ContentFragment contentFrag = new ContentFragment();
            Bundle args = new Bundle();
            args.putInt(ContentFragment.ARG_POSITION, position);
            contentFrag.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, contentFrag)
                    .addToBackStack(null).commit();
        } else {
            ContentFragment contentFrag = (ContentFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.content_fragment);
            contentFrag.updateDetailView(position);
        }
    }

    private boolean isFrameLayout() {
        if (layoutType == null) {
            layoutType = (findViewById(R.id.fragment_container) != null) ? LayoutType.ONE_PANE
                    : LayoutType.TWO_PANE;
        }
        return layoutType.isFrameLayout();
    }
}
