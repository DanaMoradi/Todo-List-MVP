package com.example.todolistmvp.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_task")
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private Boolean isComplete;
    private int importance = IMPORTANCE_NORMAL;

    public static final int IMPORTANCE_HIGH = 2;
    public static final int IMPORTANCE_NORMAL = 1;
    public static final int IMPORTANCE_LOW = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeValue(this.isComplete);
        dest.writeInt(this.importance);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.isComplete = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.importance = source.readInt();
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.isComplete = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.importance = in.readInt();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
