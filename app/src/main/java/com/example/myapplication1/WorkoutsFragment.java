package com.example.myapplication1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WorkoutsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(android.R.layout.list_content, container, false);
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Пример данных
        List<ActivityItem> activities = new ArrayList<>();
        activities.add(new ActivityItem("14.32 км", "2 часа 46 минут", "Серфинг 🏄‍♂️", "14 часов назад"));
        activities.add(new ActivityItem("1 000 м", "60 минут", "Велосипед 🚴", "29.05.2022"));

        if (activities.isEmpty()) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setText("Время потренить\nНажимай на кнопку ниже и начинаем трекать активность");
            emptyView.setGravity(android.view.Gravity.CENTER);
            emptyView.setTextSize(18);
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return emptyView;
        } else {
            recyclerView.setAdapter(new ActivityAdapter(activities));
            return recyclerView;
        }
    }

    // Модель данных
    static class ActivityItem {
        String distance, duration, type, date;
        ActivityItem(String distance, String duration, String type, String date) {
            this.distance = distance;
            this.duration = duration;
            this.type = type;
            this.date = date;
        }
    }

    // Адаптер для RecyclerView
    static class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
        List<ActivityItem> items;
        ActivityAdapter(List<ActivityItem> items) { this.items = items; }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
            return new ViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ActivityItem item = items.get(position);
            holder.tvDistance.setText(item.distance);
            holder.tvDuration.setText(item.duration);
            holder.tvType.setText(item.type);
            holder.tvDate.setText(item.date);
        }
        @Override
        public int getItemCount() { return items.size(); }
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvDistance, tvDuration, tvType, tvDate;
            ViewHolder(View v) {
                super(v);
                tvDistance = v.findViewById(R.id.tvDistance);
                tvDuration = v.findViewById(R.id.tvDuration);
                tvType = v.findViewById(R.id.tvType);
                tvDate = v.findViewById(R.id.tvDate);
            }
        }
    }
} 