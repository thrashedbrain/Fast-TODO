package com.fast.todoapp.Adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fast.todoapp.Model.Task;
import com.fast.todoapp.R;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<Task> tasks = new ArrayList<>();
    private setChecked setChecked;

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, parent, false);
        return new TaskHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskHolder holder, final int position) {
        Task currentTask = tasks.get(position);
        holder.taskTxt.setText(currentTask.getTitle());
        holder.checkBox.setChecked(currentTask.isChecked());
        setTextStyle(tasks.get(position).isChecked(), holder.taskTxt);
        holder.taskLay.setOnLongClickListener(view -> {
            setChecked.remove(tasks.get(position));
            return false;
        });
        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (setChecked != null){
                Task task = tasks.get(position);
                task.checked = b;
                setTextStyle(b, holder.taskTxt);
                setChecked.check(task);
            }

        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks (List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void setInterface(setChecked setChecked){
        this.setChecked = setChecked;
    }

    static class TaskHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private TextView taskTxt;
        private LinearLayout taskLay;

        TaskHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            taskTxt = itemView.findViewById(R.id.textTask);
            taskLay = itemView.findViewById(R.id.taskLay);
        }
    }

    private void setTextStyle(boolean b, TextView textView){
        if (b){
            textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            textView.setPaintFlags(textView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public interface setChecked{
        void check(Task task);
        void remove(Task task);
    }
}
