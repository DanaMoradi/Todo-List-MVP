package com.example.todolistmvp.main;

import com.example.todolistmvp.model.Task;
import com.example.todolistmvp.model.TaskDao;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private TaskDao taskDao;
    List<Task> tasks;
    private MainContract.View view;

    public MainPresenter(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.tasks = taskDao.getAll();
    }

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;
        if (!tasks.isEmpty()) {
            view.showTasks(tasks);
            view.setEmptyStatVisibility(false);
        } else {
            view.setEmptyStatVisibility(true);
        }

    }

    @Override
    public void onDetach() {

    }


    @Override
    public void onTaskItemClick(Task task) {
        task.setComplete(!task.getComplete());
        int result = taskDao.update(task);
        if (result > 0) {
            view.updateTask(task);
        }
    }

    @Override
    public void onDeleteAllBtnClick() {
        taskDao.deleteAll();
        view.clearTasks();
        view.setEmptyStatVisibility(true);
    }

    @Override
    public void search(String q) {
        if (!q.isEmpty()) {
            taskDao.search(q);
            List<Task> tasks = taskDao.search(q);
            view.showTasks(tasks);
        } else {
            List<Task> tasks = taskDao.getAll();
            view.showTasks(tasks);
        }

    }
}
