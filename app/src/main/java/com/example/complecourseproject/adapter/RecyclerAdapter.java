package com.example.complecourseproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complecourseproject.Item;
import com.example.complecourseproject.R;
import com.example.complecourseproject.RecyclerActivity;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<Item> items;


    public RecyclerAdapter(Context context, List<Item> itemList) {
        this.context= context;
        this.items= itemList;
        Log.d("ItemsList",""+items.size());
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);//0
        holder.titleNme.setText(item.getTitle());
        holder.descName.setText(item.getDescription());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleNme,descName;
        ImageView imageView;

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            titleNme = itemView.findViewById(R.id.titleTextView);
            descName = itemView.findViewById(R.id.descriptionTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
