package com.example.myapplication1;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromActivityType(ActivityEntity.ActivityType type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static ActivityEntity.ActivityType toActivityType(String type) {
        return type == null ? null : ActivityEntity.ActivityType.valueOf(type);
    }

    @TypeConverter
    public static String fromLatLngList(List<ActivityEntity.LatLngPoint> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<ActivityEntity.LatLngPoint> toLatLngList(String value) {
        Type listType = new TypeToken<List<ActivityEntity.LatLngPoint>>(){}.getType();
        return gson.fromJson(value, listType);
    }
} 