package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkoutsFragment extends Fragment {
    private static final int VIEW_TYPE_SECTION = 0;
    private static final int VIEW_TYPE_ACTIVITY = 1;

    private ActivityViewModel viewModel;
    private ActivityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ActivityAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        viewModel.getAllActivities().observe(getViewLifecycleOwner(), activities -> {
            adapter.setItems(activities);
        });

        return recyclerView;
    }

    // --- Модели ---
    interface ActivityListItem {}
    static class ActivityItem implements ActivityListItem {
        String distance, duration, type, date;
        ActivityItem(String distance, String duration, String type, String date) {
            this.distance = distance;
            this.duration = duration;
            this.type = type;
            this.date = date;
        }
    }
    static class DateSectionItem implements ActivityListItem {
        String section;
        DateSectionItem(String section) { this.section = section; }
    }

    // --- Вспомогательные методы ---
    private String formatDistance(double distance) {
        if (distance < 1.0) {
            return String.format("%d м", (int)(distance * 1000));
        } else {
            return String.format("%.2f км", distance);
        }
    }
    private String formatDuration(long start, long end) {
        long durationMs = end - start;
        long minutes = durationMs / 60000;
        long hours = minutes / 60;
        minutes = minutes % 60;
        if (hours > 0) return hours + " ч " + minutes + " минут";
        else return minutes + " минут";
    }
    private String getTypeNameRu(ActivityEntity.ActivityType type) {
        switch (type) {
            case BIKE: return "Велосипед 🚴";
            case RUN: return "Бег 🏃";
            case WALK: return "Шаг 🚶";
            default: return type.toString();
        }
    }
    private String formatDate(long time) {
        long now = System.currentTimeMillis();
        long diff = now - time;
        long hours = diff / (1000 * 60 * 60);
        if (hours < 24) {
            return hours + " часов назад";
        } else {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
            return sdf.format(new java.util.Date(time));
        }
    }

    // --- Адаптер ---
    class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<ActivityListItem> items;
        ActivityAdapter(List<ActivityListItem> items) { this.items = items; }
        public void setItems(List<ActivityEntity> newEntities) {
            List<ActivityListItem> newItems = new ArrayList<>();
            for (ActivityEntity entity : newEntities) {
                newItems.add(entity);
            }
            this.items = newItems;
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            if (items.get(position) instanceof DateSectionItem) return VIEW_TYPE_SECTION;
            else return VIEW_TYPE_ACTIVITY;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_SECTION) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
                return new SectionViewHolder(v);
            } else {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
                return new ActivityViewHolder(v);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof SectionViewHolder) {
                ((SectionViewHolder) holder).tvSection.setText(((DateSectionItem) items.get(position)).section);
            } else if (holder instanceof ActivityViewHolder) {
                ActivityEntity item = (ActivityEntity) items.get(position);
                ActivityViewHolder vh = (ActivityViewHolder) holder;
                String distanceStr = formatDistance(item.distance);
                String duration = formatDuration(item.startTime, item.endTime);
                String typeRu = getTypeNameRu(item.type);
                String dateStr = formatDate(item.startTime);
                vh.tvDistance.setText(distanceStr);
                vh.tvDuration.setText(duration);
                vh.tvType.setText(typeRu);
                vh.tvDate.setText(dateStr);
                vh.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(requireContext(), ActivityDetailsActivity.class);
                    intent.putExtra("distance", distanceStr);
                    intent.putExtra("duration", duration);
                    intent.putExtra("type", typeRu);
                    intent.putExtra("date", dateStr);
                    intent.putExtra("user", "");
                    intent.putExtra("description", "");
                    intent.putExtra("start", item.startTime);
                    intent.putExtra("finish", item.endTime);
                    intent.putExtra("comment", "");
                    startActivity(intent);
                });
            }
        }

        @Override
        public int getItemCount() { return items.size(); }

        class SectionViewHolder extends RecyclerView.ViewHolder {
            TextView tvSection;
            SectionViewHolder(View v) {
                super(v);
                tvSection = v.findViewById(R.id.tvSection);
            }
        }
        class ActivityViewHolder extends RecyclerView.ViewHolder {
            TextView tvDistance, tvDuration, tvType, tvDate;
            ActivityViewHolder(View v) {
                super(v);
                tvDistance = v.findViewById(R.id.tvDistance);
                tvDuration = v.findViewById(R.id.tvDuration);
                tvType = v.findViewById(R.id.tvType);
                tvDate = v.findViewById(R.id.tvDate);
            }
        }
    }
} 