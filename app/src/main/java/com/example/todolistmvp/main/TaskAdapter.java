package com.example.todolistmvp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistmvp.R;
import com.example.todolistmvp.TaskItemEventListener;
import com.example.todolistmvp.databinding.ItemTaskBinding;
import com.example.todolistmvp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ItemTaskBinding binding;
    private List<Task> tasks = new ArrayList<>();
    private TaskItemEventListener eventListener;

    private Drawable highImportantDrawable;
    private Drawable normalImportantDrawable;
    private Drawable lowImportantDrawable;

    public TaskAdapter(Context context, TaskItemEventListener eventListener) {
        this.eventListener = eventListener;
        highImportantDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.shape_importance_high_rect, null);
        normalImportantDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.shape_importance_normal_rect, null);
        lowImportantDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.shape_importance_low_rect, null);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.onBind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private ItemTaskBinding view;
        ImageView checkBoxIv;
        TextView titleTv;
        View importanceView;

        public TaskViewHolder(@NonNull ItemTaskBinding v) {
            super(v.getRoot());
            this.view = v;
            checkBoxIv = view.checkboxIv;
            titleTv = view.taskCheckBoxTv;
            importanceView = view.importanceView;
        }

        private void onBind(Task task) {
            titleTv.setText(task.getTitle());
            if (task.getComplete()) {
                checkBoxIv.setBackgroundResource(R.drawable.shape_checkbox_checked);
                checkBoxIv.setImageResource(R.drawable.ic_check_white_24dp);
                titleTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                checkBoxIv.setBackgroundResource(R.drawable.shape_checkbox_default);
                checkBoxIv.setImageResource(0);
                titleTv.setPaintFlags(0);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    eventListener.onItemLongClick(task);
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventListener.onItemClick(task);
                }
            });

            switch (task.getImportance()) {
                case Task.IMPORTANCE_HIGH:
                    importanceView.setBackground(highImportantDrawable);
                    break;
                case Task.IMPORTANCE_NORMAL:
                    importanceView.setBackground(normalImportantDrawable);
                    break;
                case Task.IMPORTANCE_LOW:
                    importanceView.setBackground(lowImportantDrawable);
                    break;
            }

        }
    }

    public void addItem(Task task) {
        tasks.add(0, task);
        notifyItemInserted(0);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<Task> tasks) {
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void updateItem(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (task.getId() == tasks.get(i).getId()) {
                tasks.set(i, task);
                notifyItemChanged(i);
            }
        }
    }

    public void deleteItem(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (task.getId() == tasks.get(i).getId()) {
                tasks.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearItems() {
        tasks.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

}
