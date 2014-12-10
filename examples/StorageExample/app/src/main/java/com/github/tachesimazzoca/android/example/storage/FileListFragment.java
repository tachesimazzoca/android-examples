package com.github.tachesimazzoca.android.example.storage;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileListFragment extends Fragment {
    private static final String TAG = "FileListFragment";

    private static final String FORMAT_LAST_MODIFIED = "yyyy-MM-dd HH:mm:ss";
    private static final String[] ADAPTER_ITEM_KEYS = {"text1", "text2"};
    private static final String ARG_BASEDIR = "basedir";

    private OnFragmentInteractionListener mListener;
    private File[] mItems;

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(File item);
    }

    public static FileListFragment newInstance(String path) {
        FileListFragment fragment = new FileListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BASEDIR, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_file_list, container, false);

        File basedir = new File(getArguments().getString(ARG_BASEDIR));
        Log.i(TAG, "Listing " + basedir.getAbsolutePath());

        // A clickable header to close itself
        TextView txv = (TextView) view.findViewById(R.id.basedir_text_view);
        txv.setText(basedir.getAbsolutePath());
        txv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // A list of files
        List<File> fileList;
        mItems = basedir.listFiles();
        if (null != mItems)
            fileList = Arrays.asList(mItems);
        else
            fileList = new ArrayList<File>();

        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        SimpleDateFormat fmt = new SimpleDateFormat(FORMAT_LAST_MODIFIED);
        for (File f : fileList) {
            Map<String, String> row = new HashMap<String, String>();
            String nm = f.getName();
            if (f.isDirectory())
                nm += "/";
            row.put(ADAPTER_ITEM_KEYS[0], nm);
            row.put(ADAPTER_ITEM_KEYS[1], fmt.format(new Date(f.lastModified())));
            rowList.add(row);
        }

        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_2 :
                android.R.layout.simple_list_item_2;
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), rowList, layout,
                ADAPTER_ITEM_KEYS, new int[]{android.R.id.text1, android.R.id.text2});

        ListView lsv = (ListView) view.findViewById(R.id.item_list_view);
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (null != mListener && null != mItems)
                    mListener.onFragmentInteraction(mItems[i]);
            }
        });
        lsv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
