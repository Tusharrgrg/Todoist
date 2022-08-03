package com.example.todoist.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoist.Data.TaskDao;
import com.example.todoist.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({converter.class})
public abstract class TaskDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "Task_database";
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static volatile TaskDatabase INSTANCE;
    public static final RoomDatabase.Callback sRoomDatabaseCallback =
            new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        TaskDao taskDao = INSTANCE.taskDao();
                        taskDao.deleteAll();
                    });
                }
            };

    public static TaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TaskDatabase.class, DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();
}
