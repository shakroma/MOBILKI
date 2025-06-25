package com.example.myapplication1;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
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
        items.add(new ActivityItem("14.32 км", "2 часа 46 минут", "Серфинг", "14 часов назад", "@van_darkholme", null));
        items.add(new ActivityItem("228 м", "14 часов 48 минут", "Качели", "14 часов назад", "@techniquepasha", null));
        items.add(new ActivityItem("10 км", "1 час 10 минут", "Езда на кадилак", "14 часов назад", "@morgen_shtern", "Езда на кадилак"));
        items.add(new DateSectionItem("Май 2022 года"));
        items.add(new ActivityItem("1 000 м", "60 минут", "Велосипед", "29.05.2022", "@van_darkholme", null));

        if (items.isEmpty()) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setText("Нет активностей пользователей");
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
        String distance, duration, type, date, user, description;
        ActivityItem(String distance, String duration, String type, String date, String user, String description) {
            this.distance = distance;
            this.duration = duration;
            this.type = type;
            this.date = date;
            this.user = user;
            this.description = description;
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
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_user, parent, false);
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
                vh.tvDescription.setText(item.description != null ? item.description : "");
                // Оформление ника пользователя
                SpannableString span = new SpannableString(item.user);
                int color = ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark);
                span.setSpan(new ForegroundColorSpan(color), 0, item.user.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new UnderlineSpan(), 0, item.user.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                vh.tvUser.setText(span);
                vh.tvUser.setPaintFlags(vh.tvUser.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                vh.tvUser.setOnClickListener(v -> {
                    // Здесь можно реализовать переход на профиль пользователя
                });
                vh.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(requireContext(), ActivityDetailsActivity.class);
                    intent.putExtra("distance", item.distance);
                    intent.putExtra("duration", item.duration);
                    intent.putExtra("type", item.type);
                    intent.putExtra("date", item.date);
                    intent.putExtra("user", item.user);
                    intent.putExtra("description", item.description);
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
            TextView tvDistance, tvDuration, tvType, tvDate, tvUser, tvDescription;
            ActivityViewHolder(View v) {
                super(v);
                tvDistance = v.findViewById(R.id.tvDistance);
                tvDuration = v.findViewById(R.id.tvDuration);
                tvType = v.findViewById(R.id.tvType);
                tvDate = v.findViewById(R.id.tvDate);
                tvUser = v.findViewById(R.id.tvUser);
                tvDescription = v.findViewById(R.id.tvDescription);
            }
        }
    }
} 