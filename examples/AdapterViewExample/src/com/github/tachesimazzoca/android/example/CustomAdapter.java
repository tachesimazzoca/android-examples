package com.github.tachesimazzoca.android.example;

import java.util.Map;
import java.util.List;

import android.view.LayoutInflater;
import android.app.Service;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, String>> mItems;

    public CustomAdapter(Context context, List<Map<String, String>> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || convertView.getId() != position) {
            LayoutInflater inflater = ((LayoutInflater) mContext.getSystemService(
                    Service.LAYOUT_INFLATER_SERVICE));
            ViewGroup rt = null;
            convertView = inflater.inflate(R.layout.view_custom_adapter_item, rt, false);
            convertView.setId(position);
            ((TextView) convertView.findViewById(R.id.custom_adapter_item_title))
                    .setText(mItems.get(position).get(ContentRepository.KEY_TITLE));
            ((TextView) convertView.findViewById(R.id.custom_adapter_item_description))
                    .setText(mItems.get(position).get(ContentRepository.KEY_DESCRIPTION));
        }
        return convertView;
    }
}
