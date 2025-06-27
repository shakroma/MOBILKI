package com.example.myapplication1;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityRepository {
    private final ActivityDao activityDao;
    private final LiveData<List<ActivityEntity>> allActivities;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ActivityRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        activityDao = db.activityDao();
        allActivities = activityDao.getAllActivities();
    }

    public LiveData<List<ActivityEntity>> getAllActivities() {
        return allActivities;
    }

    public void insert(ActivityEntity activity) {
        executor.execute(() -> activityDao.insert(activity));
    }

    public void deleteAll() {
        executor.execute(activityDao::deleteAll);
    }
} 