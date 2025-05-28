package com.example.learningmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    private final Context context;
    private final ArrayList<Task> tasks;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = tasks.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
        }

        TextView txtName = convertView.findViewById(R.id.txtTaskName);
        TextView txtDate = convertView.findViewById(R.id.txtTaskDate);
        TextView txtModule = convertView.findViewById(R.id.txtTaskModule);
        TextView txtStudent = convertView.findViewById(R.id.txtTaskStudent);
        TextView txtStatus = convertView.findViewById(R.id.txtTaskStatus);

        txtName.setText(task.tName);
        txtDate.setText("Due: " + task.tDate);
        txtModule.setText("Module: " + task.tModule);
        txtStudent.setText("Student: " + task.tStudent);
        txtStatus.setText("Status: " + task.tStatus);

        return convertView;
    }
}
