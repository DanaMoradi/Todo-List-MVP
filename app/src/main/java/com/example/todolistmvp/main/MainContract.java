package com.example.todolistmvp.main;

import com.example.todolistmvp.model.Task;
import com.example.todolistmvp.helper.BasePresenter;
import com.example.todolistmvp.helper.BaseView;

import java.util.List;

public interface MainContract {


    interface View extends BaseView {

        void showTasks(List<Task> tasks);

        void clearTasks();

        void updateTask(Task task);

        void setEmptyStatVisibility(boolean visibility);

    }

    interface Presenter extends BasePresenter<View> {

        void onTaskItemClick(Task task);

        void onDeleteAllBtnClick();

        void search(String q);


    }


}
