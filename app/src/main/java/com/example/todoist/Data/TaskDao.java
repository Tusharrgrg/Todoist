package com.example.todoist.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoist.model.Task;

import java.util.List;

@Dao
public interface TaskDao {


    @Insert
    void InsertTask(Task task);

    @Query("DELETE FROM Task_table")
    void deleteAll();

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTask();

    @Query("SELECT *FROM Task_table WHERE Task_table.Task_Id == :id")
    LiveData<Task> get(long id);

    @Update
    void updateTask(Task task);

    @Delete
    void DeleteTask(Task task);


}
