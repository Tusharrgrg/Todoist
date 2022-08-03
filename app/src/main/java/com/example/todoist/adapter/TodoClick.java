package com.example.todoist.adapter;

import com.example.todoist.model.Task;

public interface TodoClick {

    void onTodoClick( Task task);
    void radioButtonClick(Task task);

}
