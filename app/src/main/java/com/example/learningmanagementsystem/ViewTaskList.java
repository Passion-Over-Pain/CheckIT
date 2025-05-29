package com.example.learningmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewTaskList extends AppCompatActivity {

    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_list);

        taskListView = findViewById(R.id.taskListView);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(taskAdapter);

        loadTasks();
        taskListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Task selectedTask = taskList.get(position);

            Intent intent = new Intent(ViewTaskList.this, UpdateTask.class);
            intent.putExtra("tID", selectedTask.tID);
            intent.putExtra("tName", selectedTask.tName);
            intent.putExtra("tDate", selectedTask.tDate);
            intent.putExtra("tModule", selectedTask.tModule);
            intent.putExtra("tStudent", selectedTask.tStudent);
            intent.putExtra("tStatus", selectedTask.tStatus);
            startActivity(intent);
        });
    }

    private void loadTasks() {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        Cursor cursor = db.rawQuery("SELECT tID, tName, tDate, tModule, tStudent, tStatus FROM tasks", null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.tID = cursor.getString(0);
                task.tName = cursor.getString(1);
                task.tDate = cursor.getString(2);
                task.tModule = cursor.getString(3);
                task.tStudent = cursor.getString(4);
                task.tStatus = cursor.getString(5);

                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        taskAdapter.notifyDataSetChanged();
    }
}
