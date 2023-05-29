package com.example.todolistmvp.detail;

import static com.example.todolistmvp.R.id.deleteTaskBtn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistmvp.R;
import com.example.todolistmvp.databinding.ActivityTaskDetailBinding;
import com.example.todolistmvp.helper.Const;
import com.example.todolistmvp.model.AppDatabase;
import com.example.todolistmvp.model.Task;

public class TaskDetailActivity extends AppCompatActivity implements View.OnClickListener, TaskDetailContract.View {
    private ActivityTaskDetailBinding binding;
    private TaskDetailContract.Presenter presenter;

    private View backBtn;


    private FrameLayout normalImportanceBtn;
    private FrameLayout highImportanceBtn;
    private FrameLayout lowImportanceBtn;


    private EditText taskEt;

    private ImageView deleteBtn;


    private int selectedImportance = Task.IMPORTANCE_NORMAL;
    private ImageView lastSelectedImportanceIv;

    private View saveChanges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initialView();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        lastSelectedImportanceIv = binding.normalImportanceCheckIv;

        highImportanceBtn.setOnClickListener(this);
        lowImportanceBtn.setOnClickListener(this);
        normalImportanceBtn.setOnClickListener(this);
        saveChanges.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        presenter.onAttach(this);
    }

    private void initialView() {

        presenter = new TaskDetailPresenter(AppDatabase.getAppDatabase(this).getTaskDao(), getIntent().getParcelableExtra(Const.EXTRA_KEY_TASK));


        backBtn = binding.backBtn;

        normalImportanceBtn = binding.normalImportanceBtn;
        highImportanceBtn = binding.highImportanceBtn;
        lowImportanceBtn = binding.lowImportanceBtn;

        taskEt = binding.taskEt;

        saveChanges = binding.saveChangesBtn;

        deleteBtn = binding.deleteTaskBtn;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.highImportanceBtn:
                if (selectedImportance != Task.IMPORTANCE_HIGH) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = binding.highImportanceCheckIv;
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_HIGH;
                    lastSelectedImportanceIv = imageView;
                }
                break;
            case R.id.lowImportanceBtn:
                if (selectedImportance != Task.IMPORTANCE_LOW) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = binding.lowImportanceCheckIv;
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_LOW;
                    lastSelectedImportanceIv = imageView;
                }
                break;
            case R.id.normalImportanceBtn:
                if (selectedImportance != Task.IMPORTANCE_NORMAL) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = binding.normalImportanceCheckIv;
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_NORMAL;
                    lastSelectedImportanceIv = imageView;
                }
                break;
            case R.id.saveChangesBtn:
                String taskString = taskEt.getText().toString();
                presenter.saveChanges(selectedImportance, taskString);
                break;
            case deleteTaskBtn:
                presenter.onDeleteTask();


        }
    }

    @Override
    public void showTask(Task task) {
        taskEt.setText(task.getTitle());
        switch (task.getImportance()) {
            case Task.IMPORTANCE_HIGH:
                highImportanceBtn.performClick();
                break;
            case Task.IMPORTANCE_NORMAL:
                normalImportanceBtn.performClick();
                break;
            case Task.IMPORTANCE_LOW:
                lowImportanceBtn.performClick();
                break;
        }
    }

    @Override
    public void setDeleteBtnVisibility(boolean visible) {
        deleteBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void returnResult(int resultCode, Task task) {
        Intent intent = new Intent();
        intent.putExtra(Const.EXTRA_KEY_TASK, task);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}