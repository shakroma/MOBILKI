package com.example.myapplication1;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.SystemClock;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.List;

public class NewActivityTrackingActivity extends AppCompatActivity {
    private long startTime;
    private ActivityViewModel viewModel;
    private ActivityEntity.ActivityType type;
    private long activityId = -1;
    private boolean activitySaved = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity_tracking);

        viewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        startTime = System.currentTimeMillis();
        String typeStr = getIntent().getStringExtra("type");
        if (typeStr != null) {
            switch (typeStr) {
                case "Велосипед": type = ActivityEntity.ActivityType.BIKE; break;
                case "Бег": type = ActivityEntity.ActivityType.RUN; break;
                case "Шаг": type = ActivityEntity.ActivityType.WALK; break;
                default: type = ActivityEntity.ActivityType.BIKE;
            }
        } else {
            type = ActivityEntity.ActivityType.BIKE;
        }
        // Можно сохранить id активности, если передавать его из предыдущего экрана

        TextView tvType = findViewById(R.id.tvType);
        tvType.setText(type != null ? type.toString() : "Велосипед");
    }

    @Override
    public void onBackPressed() {
        saveActivityResult();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        saveActivityResult();
        super.onDestroy();
    }

    private void saveActivityResult() {
        if (activitySaved) return;
        activitySaved = true;
        long endTime = System.currentTimeMillis();
        List<ActivityEntity.LatLngPoint> coords = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            coords.add(new ActivityEntity.LatLngPoint(55 + Math.random(), 37 + Math.random()));
        }
        double distance = 0.5 + Math.random() * 19.5;
        ActivityEntity entity = new ActivityEntity(type, startTime, endTime, coords, distance);
        viewModel.insert(entity);
    }
} 