package com.example.myapplication1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ActivityDao {
    @Insert
    void insert(ActivityEntity activity);

    @Query("SELECT * FROM activities ORDER BY startTime DESC")
    LiveData<List<ActivityEntity>> getAllActivities();

    @Query("DELETE FROM activities")
    void deleteAll();
} 