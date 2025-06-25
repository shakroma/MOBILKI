package com.example.myapplication1;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ActivityTypeAdapter extends RecyclerView.Adapter<ActivityTypeAdapter.ViewHolder> {
    private final List<NewActivityStartActivity.ActivityType> types;
    private int selected = 0;
    private final OnTypeSelectedListener listener;

    public interface OnTypeSelectedListener {
        void onTypeSelected(int position);
    }

    public ActivityTypeAdapter(List<NewActivityStartActivity.ActivityType> types, OnTypeSelectedListener listener) {
        this.types = types;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_type, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewActivityStartActivity.ActivityType type = types.get(position);
        holder.tvTypeName.setText(type.name);
        holder.itemView.setBackgroundColor(selected == position ? 0x223F51B5 : Color.TRANSPARENT);
        holder.itemView.setOnClickListener(v -> {
            int oldSelected = selected;
            selected = position;
            notifyItemChanged(oldSelected);
            notifyItemChanged(selected);
            listener.onTypeSelected(position);
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTypeName;
        ViewHolder(View v) {
            super(v);
            tvTypeName = v.findViewById(R.id.tvTypeName);
        }
    }
} 