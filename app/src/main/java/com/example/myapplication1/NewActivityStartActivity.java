package com.example.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import java.util.Arrays;
import java.util.List;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Random;

public class NewActivityStartActivity extends AppCompatActivity implements OnMapReadyCallback {
    private int selectedType = 0;
    private static final List<ActivityType> types = Arrays.asList(
            new ActivityType("Велосипед"),
            new ActivityType("Бег"),
            new ActivityType("Шаг")
    );
    private ActivityViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity_start);

        viewModel = new ViewModelProvider(this).get(ActivityViewModel.class);

        // Инициализация карты
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        RecyclerView rvTypes = findViewById(R.id.rvActivityTypes);
        rvTypes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ActivityTypeAdapter adapter = new ActivityTypeAdapter(types, pos -> {
            selectedType = pos;
        });
        rvTypes.setAdapter(adapter);

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            // --- Переход на экран трекинга ---
            Intent intent = new Intent(this, NewActivityTrackingActivity.class);
            intent.putExtra("type", types.get(selectedType).name);
            startActivity(intent);
        });

        // Кнопка-крестик для выхода
        ImageButton btnClose = new ImageButton(this);
        btnClose.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        btnClose.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        btnClose.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Пока ничего не делаем, карта пустая
    }

    static class ActivityType {
        String name;
        ActivityType(String name) {
            this.name = name;
        }
    }
} 