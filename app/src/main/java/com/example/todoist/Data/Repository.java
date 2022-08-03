package com.example.todoist.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todoist.model.Task;
import com.example.todoist.util.TaskDatabase;

import java.util.List;

public class Repository {

    private static TaskDao taskDao;
    private static LiveData<List<Task>> allTasks;

    public Repository(Application application) {
        TaskDatabase database = TaskDatabase.getDatabase(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getTask();
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public void insert(Task task){
        TaskDatabase.databaseWriteExecutor.execute(()-> taskDao.InsertTask(task));
    }

    public void update(Task task){
        TaskDatabase.databaseWriteExecutor.execute(()-> taskDao.updateTask(task));
    }

    public LiveData<Task> getId(long id){
        return taskDao.get(id);
    }

    public void delete(Task task){
        TaskDatabase.databaseWriteExecutor.execute(()-> taskDao.DeleteTask(task));
    }
}
