package com.example.myapplication1;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewActivityTrackingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity_tracking);

        String type = getIntent().getStringExtra("type");
        TextView tvType = findViewById(R.id.tvType);
        tvType.setText(type != null ? type : "Велосипед");
    }
} 