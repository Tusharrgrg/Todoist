package com.example.todoist.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoist.Data.Repository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    public static  Repository repository;;
    public final LiveData<List<Task>> allTasks;


    public TaskViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        allTasks = repository.getAllTasks();

    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }
    public LiveData<Task> getId(long id){return repository.getId(id);}
    public static void insert(Task task){repository.insert(task);}
    public static void update(Task task){repository.update(task);}
    public static void delete(Task task){repository.delete(task);}

}
