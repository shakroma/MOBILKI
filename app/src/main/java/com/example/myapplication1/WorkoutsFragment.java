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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WorkoutsFragment extends Fragment {
    private static final int VIEW_TYPE_SECTION = 0;
    private static final int VIEW_TYPE_ACTIVITY = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Пример данных
        List<ActivityListItem> items = new ArrayList<>();
        items.add(new DateSectionItem("Вчера"));
        items.add(new ActivityItem("14.32 км", "2 часа 46 минут", "Серфинг 🏄‍♂️", "14 часов назад"));
        items.add(new DateSectionItem("Май 2022 года"));
        items.add(new ActivityItem("1 000 м", "60 минут", "Велосипед 🚴", "29.05.2022"));

        if (items.isEmpty()) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setText("Время потренить\nНажимай на кнопку ниже и начинаем трекать активность");
            emptyView.setGravity(android.view.Gravity.CENTER);
            emptyView.setTextSize(18);
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return emptyView;
        } else {
            recyclerView.setAdapter(new ActivityAdapter(items));
            return recyclerView;
        }
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

    // --- Адаптер ---
    class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<ActivityListItem> items;
        ActivityAdapter(List<ActivityListItem> items) { this.items = items; }

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
                ActivityItem item = (ActivityItem) items.get(position);
                ActivityViewHolder vh = (ActivityViewHolder) holder;
                vh.tvDistance.setText(item.distance);
                vh.tvDuration.setText(item.duration);
                vh.tvType.setText(item.type);
                vh.tvDate.setText(item.date);
                vh.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(requireContext(), ActivityDetailsActivity.class);
                    intent.putExtra("distance", item.distance);
                    intent.putExtra("duration", item.duration);
                    intent.putExtra("type", item.type);
                    intent.putExtra("date", item.date);
                    intent.putExtra("user", "");
                    intent.putExtra("description", "");
                    intent.putExtra("start", "");
                    intent.putExtra("finish", "");
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