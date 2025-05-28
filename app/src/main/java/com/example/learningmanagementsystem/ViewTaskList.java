package com.example.learningmanagementsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewTaskList extends AppCompatActivity {

    ListView taskListView;
    ArrayList<Task> taskList;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_list);

        taskListView = findViewById(R.id.taskListView);
        taskList = new ArrayList<>();

        loadTasks();
        taskAdapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(taskAdapter);
    }

    private void loadTasks() {
        SQLiteDatabase db = DatabaseManager.getDB(this);

        // Fetch all needed fields including status and tID if needed
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
    }
}
