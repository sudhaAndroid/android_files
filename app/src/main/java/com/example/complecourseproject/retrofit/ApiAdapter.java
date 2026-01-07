package com.example.complecourseproject.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complecourseproject.APIIntegrationActivity;
import com.example.complecourseproject.R;

import org.w3c.dom.Text;

import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ViewHolder> {
    Context context;
    List<User> list;
    public ApiAdapter(Context context, List<User> userList) {
        this.context = context;
        this.list = userList;
    }

    @NonNull
    @Override
    public ApiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiAdapter.ViewHolder holder, int position) {
        User user = list.get(position);
        holder.body.setText(user.body);
        holder.title.setText(user.title);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.titleTextView);
            body = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
