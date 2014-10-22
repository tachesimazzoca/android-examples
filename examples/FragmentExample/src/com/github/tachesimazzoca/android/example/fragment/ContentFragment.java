package com.github.tachesimazzoca.android.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {
    public static final String ARG_POSITION = "position";
    private static final int POSITION_NONE = -1;

    private int mPosition = POSITION_NONE;
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mTextView = (TextView) view.findViewById(R.id.content_detail_text_view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            updateDetailView(args.getInt(ARG_POSITION));
        } else {
            if (mPosition != POSITION_NONE)
                updateDetailView(mPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mPosition);
    }

    public void updateDetailView(int position) {
        Content content = ContentsRepository.getContent(position);
        mTextView.setText(content.body);
    }
}
