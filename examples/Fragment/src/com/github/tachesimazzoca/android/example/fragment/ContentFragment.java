package com.github.tachesimazzoca.android.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {
    public static final String ARG_POSITION = "position";
    private static final int POSITION_NONE = -1;
    private int mPosition = POSITION_NONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateDetailView(args.getInt(ARG_POSITION));
        } else {
            if (mPosition != POSITION_NONE) {
                updateDetailView(mPosition);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mPosition);
    }

    public void updateDetailView(int position) {
        mPosition = position;
        TextView view = (TextView) getActivity()
                .findViewById(R.id.content_detail_view);
        Content content = ContentsService.getContent(position);
        view.setText(content.body);
    }
}
