package com.example.todolistmvp.detail;

import com.example.todolistmvp.model.Task;
import com.example.todolistmvp.helper.BasePresenter;
import com.example.todolistmvp.helper.BaseView;

public interface TaskDetailContract {


    interface View extends BaseView {

        void showTask(Task task);

        void setDeleteBtnVisibility(boolean visible);

        void showError(String error);

        void returnResult(int resultCode, Task task);

    }

    interface Presenter extends BasePresenter<View> {

        void onDeleteTask();

        void saveChanges(int importance, String title);


    }
}
