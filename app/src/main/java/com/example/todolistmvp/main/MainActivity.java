package com.example.todolistmvp.main;

import static com.example.todolistmvp.R.id.addNewTaskBtn;
import static com.example.todolistmvp.R.id.deleteAllBtn;
import static com.example.todolistmvp.helper.Const.EXTRA_KEY_TASK;
import static com.example.todolistmvp.helper.Const.REQUEST_CODE;
import static com.example.todolistmvp.helper.Const.RESULT_CODE_ADD_TASK;
import static com.example.todolistmvp.helper.Const.RESULT_CODE_DELETE_TASK;
import static com.example.todolistmvp.helper.Const.RESULT_CODE_UPDATE_TASK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistmvp.TaskItemEventListener;
import com.example.todolistmvp.databinding.ActivityMainBinding;
import com.example.todolistmvp.detail.TaskDetailActivity;
import com.example.todolistmvp.helper.Const;
import com.example.todolistmvp.model.AppDatabase;
import com.example.todolistmvp.model.Task;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, TaskItemEventListener, View.OnClickListener, TextWatcher {

    private ActivityMainBinding binding;

    private MainContract.Presenter presenter;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;

    private LinearLayout emptyStat;
    private View addNewTask;
    private MaterialButton deleteAll;

    private EditText searchEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initial();
        setClickListener();
        setRecyclerView();
        presenter.onAttach(this);
    }

    private void initial() {

        presenter = new MainPresenter(AppDatabase.getAppDatabase(this).getTaskDao());
        taskAdapter = new TaskAdapter(this, this);
        recyclerView = binding.taskListRv;

        emptyStat = binding.emptyState;
        addNewTask = binding.addNewTaskBtn;

        deleteAll = binding.deleteAllBtn;

        searchEt = binding.searchEt;


    }

    private void setClickListener() {
        addNewTask.setOnClickListener(this);
        deleteAll.setOnClickListener(this);
        searchEt.addTextChangedListener(this);
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if ((resultCode == RESULT_CODE_ADD_TASK || resultCode == RESULT_CODE_UPDATE_TASK || resultCode == RESULT_CODE_DELETE_TASK) && data != null) {
                Task task = data.getParcelableExtra(Const.EXTRA_KEY_TASK);
                if (task != null) {
                    switch (resultCode) {
                        case RESULT_CODE_ADD_TASK:
                            taskAdapter.addItem(task);
                            break;
                        case RESULT_CODE_UPDATE_TASK:
                            taskAdapter.updateItem(task);
                            break;
                        case RESULT_CODE_DELETE_TASK:
                            taskAdapter.deleteItem(task);
                    }
                    recyclerView.smoothScrollToPosition(0);
                    setEmptyStatVisibility(taskAdapter.getItemCount() == 0);
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showTasks(List<Task> tasks) {
        taskAdapter.setTasks(tasks);
    }

    @Override
    public void clearTasks() {
        taskAdapter.clearItems();
    }

    @Override
    public void updateTask(Task task) {
        taskAdapter.updateItem(task);
    }

    @Override
    public void setEmptyStatVisibility(boolean visibility) {
        emptyStat.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(Task task) {
        presenter.onTaskItemClick(task);
    }

    @Override
    public void onItemLongClick(Task task) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra(EXTRA_KEY_TASK, task);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case addNewTaskBtn:
                startActivityForResult(new Intent(MainActivity.this, TaskDetailActivity.class), REQUEST_CODE);
                break;
            case deleteAllBtn:
                presenter.onDeleteAllBtnClick();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        presenter.search(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}