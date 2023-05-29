package com.example.todolistmvp;

import com.example.todolistmvp.model.Task;

public interface TaskItemEventListener {

    void onItemClick(Task task);

    void onItemLongClick(Task task);

}
