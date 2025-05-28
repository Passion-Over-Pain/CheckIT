package com.example.learningmanagementsystem;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends AppCompatActivity {

    ListView taskListView;
    String studentFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_dashboard);

        taskListView = findViewById(R.id.taskListView);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstName = prefs.getString("firstname", "DefaultFirst");
        String lastName = prefs.getString("lastname", "DefaultLast");

        if (firstName != null && lastName != null) {
            studentFullName = firstName + " " + lastName;
            loadTasksForStudent(studentFullName);
        } else {
            Toast.makeText(this, "Error: Student name missing", Toast.LENGTH_SHORT).show();
        }

        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = (Task) parent.getItemAtPosition(position);

            // Only update if the task is not already complete
            if (!"Complete".equalsIgnoreCase(selectedTask.tStatus)) {
                markTaskAsComplete(selectedTask.tID);
                loadTasksForStudent(studentFullName); // Refresh the list
                Toast.makeText(this, "Task marked as complete!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task is already complete.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void markTaskAsComplete(String taskID) {
        SQLiteDatabase db = DatabaseManager.getDB(this);

        db.execSQL("UPDATE tasks SET tStatus = ? WHERE tID = ?", new Object[]{"Complete", taskID});
    }


    private void loadTasksForStudent(String studentFullName) {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        List<Task> taskList = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT tID, tName, tDate, tModule, tStudent, tStatus FROM tasks WHERE tStudent = ?",
                new String[]{studentFullName}
        );

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

        if (taskList.isEmpty()) {
            Toast.makeText(this, "No tasks assigned yet.", Toast.LENGTH_SHORT).show();
        }

        TaskAdapter adapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(adapter);
    }
}
