package com.example.todoist.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.todoist.model.Priority;
import com.example.todoist.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        return simpleDateFormat.format(date);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int priorityColor(Task task) {
        int color;

        if (task.getPriority() == Priority.HiGH) {
            color = Color.argb(200, 201, 21, 23);
        } else if (task.getPriority() == Priority.MEDIUM) {
            color = Color.argb(200, 155, 179, 0);
        } else {
            color = Color.argb(200, 51, 181, 129);
        }

        return color;
    }
}
