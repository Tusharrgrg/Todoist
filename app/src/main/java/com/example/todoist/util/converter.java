package com.example.todoist.util;

import androidx.room.TypeConverter;

import com.example.todoist.model.Priority;

import java.util.Date;

public class converter {

    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static long dateToTimestamp(Date date){
        return date == null?null:date.getTime();
    }


    @TypeConverter
    public static String fromPriority(Priority priority){
        return priority == null?null:priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priority){
        return priority == null ? null : Priority.valueOf(priority);
    }

}
