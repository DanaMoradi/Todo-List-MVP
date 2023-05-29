package com.example.todolistmvp.model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long add(Task task);

    @Delete
    int delete(Task task);

    @Update
    int update(Task task);

    @Query("SELECT * FROM tbl_task")
    List<Task> getAll();

    @Query("SELECT * FROM tbl_task WHERE title LIKE '%' || :query  || '%' ")
    List<Task> search(String query);

    @Query("DELETE FROM tbl_task")
    void deleteAll();

}
