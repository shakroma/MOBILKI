package com.example.myapplication1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        TextView tvType = findViewById(R.id.tvType);
        TextView tvDistance = findViewById(R.id.tvDistance);
        TextView tvDuration = findViewById(R.id.tvDuration);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvStart = findViewById(R.id.tvStart);
        TextView tvFinish = findViewById(R.id.tvFinish);
        EditText etComment = findViewById(R.id.etComment);

        String type = getIntent().getStringExtra("type");
        String distance = getIntent().getStringExtra("distance");
        String duration = getIntent().getStringExtra("duration");
        String date = getIntent().getStringExtra("date");
        String user = getIntent().getStringExtra("user");
        String description = getIntent().getStringExtra("description");
        String start = getIntent().getStringExtra("start");
        String finish = getIntent().getStringExtra("finish");
        String comment = getIntent().getStringExtra("comment");

        tvType.setText(type != null ? type : "");
        tvDistance.setText(distance != null ? distance : "");
        tvDuration.setText(duration != null ? duration : "");
        tvDate.setText(date != null ? date : "");
        tvStart.setText(!TextUtils.isEmpty(start) ? "Старт " + start : "");
        tvFinish.setText(!TextUtils.isEmpty(finish) ? "Финиш " + finish : "");
        etComment.setText(comment != null ? comment : "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        // Здесь можно добавить обработку кнопок share/edit
        return super.onOptionsItemSelected(item);
    }
} 