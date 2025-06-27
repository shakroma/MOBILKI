package com.example.myapplication1;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ActivityViewModel extends AndroidViewModel {
    private final ActivityRepository repository;
    private final LiveData<List<ActivityEntity>> allActivities;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new ActivityRepository(application);
        allActivities = repository.getAllActivities();
    }

    public LiveData<List<ActivityEntity>> getAllActivities() {
        return allActivities;
    }

    public void insert(ActivityEntity activity) {
        repository.insert(activity);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
} 