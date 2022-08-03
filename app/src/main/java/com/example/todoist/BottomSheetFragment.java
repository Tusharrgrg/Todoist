package com.example.todoist;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoist.model.Priority;
import com.example.todoist.model.SharedDataModel;
import com.example.todoist.model.Task;
import com.example.todoist.model.TaskViewModel;
import com.example.todoist.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {


    SharedDataModel sharedDataModel;
    Calendar calendar = Calendar.getInstance();
    Priority priority;
    private EditText enterTodo;
    private ImageButton calenderButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton radioButton;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private int selectButtonId;
    private Group calendarGroup;
    private Date DueDate;
    private boolean isEdit;

    public BottomSheetFragment() {

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calenderButton = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip = view.findViewById(R.id.today_chip);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);


        todayChip.setOnClickListener(this);
        tomorrowChip.setOnClickListener(this);
        nextWeekChip.setOnClickListener(this);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedDataModel.getSelectItem().getValue() != null) {
            isEdit = sharedDataModel.getIsEdit();
            Task task = sharedDataModel.getSelectItem().getValue();
            enterTodo.setText(task.getTask());
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedDataModel = new ViewModelProvider(requireActivity()).get(SharedDataModel.class);

        calenderButton.setOnClickListener(view12 -> {
            calendarGroup.setVisibility(
                    calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideKeyboard(view12);
        });


        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            calendar.clear();
            calendar.set(year, month, day);
            DueDate = calendar.getTime();
        });

        priorityButton.setOnClickListener(view13 -> {

            Utils.hideKeyboard(view13);
            priorityRadioGroup.setVisibility(
                    priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );

            priorityRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if (priorityRadioGroup.getVisibility() == View.VISIBLE) {
                    selectButtonId = checkedId;
                    radioButton = view.findViewById(selectButtonId);
                    if (radioButton.getId() == R.id.radioButton_high) {
                        priority = Priority.HiGH;
                    } else if (radioButton.getId() == R.id.radioButton_med) {
                        priority = Priority.MEDIUM;
                    } else if (radioButton.getId() == R.id.radioButton_low) {
                        priority = Priority.LOW;
                    } else {
                        priority = Priority.LOW;
                    }
                } else {
                    priority = Priority.LOW;
                }
            });
        });

        saveButton.setOnClickListener(view1 -> {
            String task = enterTodo.getText().toString().trim();
            if (!TextUtils.isEmpty(task) && DueDate != null && priority!= null) {
                Task myTask = new Task(task, priority,
                        DueDate, Calendar.getInstance().getTime(), false);

                if (isEdit) {
                    Task updateTask = sharedDataModel.getSelectItem().getValue();
                    assert updateTask != null;
                    updateTask.setTask(task);
                    updateTask.setDateCreated(Calendar.getInstance().getTime());
                    updateTask.setPriority(priority);
                    updateTask.setDueDate(DueDate);
                    TaskViewModel.update(updateTask);
                    sharedDataModel.setIsEdit(false);

                } else {
                    TaskViewModel.insert(myTask);
                }

                enterTodo.setText("");
                if (this.isVisible()) this.dismiss();
            }
            else {
                Snackbar.make(saveButton, R.string.email_a_copy, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.today_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            DueDate = calendar.getTime();
        }
        else if (id == R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            DueDate = calendar.getTime();
        }
        else if (id == R.id.next_week_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            DueDate = calendar.getTime();
        }
    }
}
