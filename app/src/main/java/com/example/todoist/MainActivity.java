package com.example.todoist;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoist.adapter.RecyclerViewAdapter;
import com.example.todoist.adapter.TodoClick;
import com.example.todoist.model.SharedDataModel;
import com.example.todoist.model.Task;
import com.example.todoist.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity implements TodoClick {
    public RecyclerView recyclerView;
    public RecyclerViewAdapter recyclerViewAdapter;
    BottomSheetFragment bottomSheetFragment;
    private SharedDataModel sharedDataModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);


        TaskViewModel taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication()
        ).create(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(this, tasks -> {
            recyclerViewAdapter = new RecyclerViewAdapter(tasks,
                    this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });


        sharedDataModel = new ViewModelProvider(this)
                .get(SharedDataModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            showBottomSheet();
        });
    }

    private void showBottomSheet() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoClick(Task task) {

        sharedDataModel.select(task);
        sharedDataModel.setIsEdit(true);
        showBottomSheet();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void radioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }

}