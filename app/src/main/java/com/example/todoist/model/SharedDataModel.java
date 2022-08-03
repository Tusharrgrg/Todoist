package com.example.todoist.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedDataModel extends ViewModel {

    private final MutableLiveData<Task> selectItem = new MutableLiveData<>();
    private boolean isEdit;
    public void select(Task task){
        selectItem.setValue(task);
    }
    public LiveData<Task> getSelectItem(){
        return selectItem;
    }

    public void setIsEdit(boolean isEdit){
        this.isEdit = isEdit;
    }

    public boolean getIsEdit(){
        return isEdit;
    }
}
