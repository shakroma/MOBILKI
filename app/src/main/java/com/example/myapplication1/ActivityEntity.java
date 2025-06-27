package com.example.myapplication1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.List;

@Entity(tableName = "activities")
@TypeConverters({Converters.class})
public class ActivityEntity implements WorkoutsFragment.ActivityListItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public ActivityType type;
    public long startTime;
    public long endTime;
    public List<LatLngPoint> coordinates;
    public double distance;

    public ActivityEntity(ActivityType type, long startTime, long endTime, List<LatLngPoint> coordinates, double distance) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.coordinates = coordinates;
        this.distance = distance;
    }

    public enum ActivityType {
        BIKE, RUN, WALK
    }

    public static class LatLngPoint {
        public double lat;
        public double lng;
        public LatLngPoint(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }
    }
} 