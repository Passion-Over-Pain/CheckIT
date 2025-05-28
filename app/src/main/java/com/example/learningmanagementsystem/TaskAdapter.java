package com.example.learningmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);

        Task task = taskList.get(position);

        TextView txtName = view.findViewById(R.id.txtTaskName);
        TextView txtDate = view.findViewById(R.id.txtTaskDate);
        TextView txtModule = view.findViewById(R.id.txtTaskModule);
        TextView txtStudent = view.findViewById(R.id.txtTaskStudent);
        TextView txtStatus = view.findViewById(R.id.txtTaskStatus);
//        ImageView icon = view.findViewById(R.drawable.task);

        txtName.setText(task.tName);
        txtDate.setText("Due: " + task.tDate);
        txtModule.setText("Module: " + task.tModule);
        txtStudent.setText("Assigned To: " + task.tStudent);
        txtStatus.setText("Status: " + task.tStatus);
//        icon.setImageResource(R.drawable.task); // or your task icon

        return view;
    }
}
