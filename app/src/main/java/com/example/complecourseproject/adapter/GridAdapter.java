package com.example.complecourseproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.complecourseproject.GridActivity;
import com.example.complecourseproject.Item;
import com.example.complecourseproject.R;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<Item> items;
    public GridAdapter(GridActivity gridActivity, List<Item> itemList) {
        this.context = gridActivity;
        this.items = itemList;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItem;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            gridItem = inflater.inflate(R.layout.grid_item, parent, false);
        } else {
            gridItem = convertView;
        }

        Item currentItem = items.get(position);

        TextView title = gridItem.findViewById(R.id.titleTextView);
        TextView textView = gridItem.findViewById(R.id.grid_text);

        title.setText(currentItem.getTitle());
        textView.setText(currentItem.getDescription());

        return gridItem;
    }
}
