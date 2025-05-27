package com.example.learningmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentDashboard extends AppCompatActivity {

    LinearLayout taskListLayout;
    String studentFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskListLayout = findViewById(R.id.taskListLayout);

        // Get student full name from intent extras (sent from login activity)


        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String firstName = prefs.getString("firstname", "DefaultFirst");
        String lastName = prefs.getString("lastname", "DefaultLast");


        if (firstName != null && lastName != null) {
            studentFullName = firstName + " " + lastName;
            loadTasksForStudent(studentFullName);

        } else {
            // Show error if name not passed properly
            TextView errorText = new TextView(this);
            errorText.setText("Student name not found: "+ firstName + " "+ lastName);
            taskListLayout.addView(errorText);
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadTasksForStudent(String studentFullName) {
        SQLiteDatabase db = DatabaseManager.getDB(this);

        Cursor cursor = db.rawQuery(
                "SELECT tID, tName, tDate, tModule, tStatus FROM tasks WHERE tStudent = ?",
                new String[]{studentFullName}
        );

        if (cursor.moveToFirst()) {
            do {
                int taskId = cursor.getInt(0); // Primary key (assumed column name: tID)
                String taskName = cursor.getString(1);
                String dueDate = cursor.getString(2);
                String moduleName = cursor.getString(3);
                String status = cursor.getString(4);

                // Layout container for the task
                LinearLayout taskRow = new LinearLayout(this);
                taskRow.setOrientation(LinearLayout.HORIZONTAL);
                taskRow.setPadding(0, 0, 0, 30);
                taskRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                // TextView for task info
                TextView taskInfo = new TextView(this);
                taskInfo.setText("Task: " + taskName + "\nDue: " + dueDate + "\nModule: " + moduleName);
                taskInfo.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                ));

                if ("completed".equalsIgnoreCase(status)) {
                    taskInfo.setAlpha(0.5f);
                    taskInfo.setPaintFlags(taskInfo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

                // Checkbox for marking completion
                CheckBox checkBox = new CheckBox(this);
                checkBox.setChecked("completed".equalsIgnoreCase(status));
                checkBox.setEnabled(!"completed".equalsIgnoreCase(status)); // Disable if already completed

                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        markTaskAsCompleted(taskId);
                        checkBox.setEnabled(false);
                        taskInfo.setAlpha(0.5f);
                        taskInfo.setPaintFlags(taskInfo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        Toast.makeText(this, "Task marked as completed", Toast.LENGTH_SHORT).show();
                    }
                });

                taskRow.addView(taskInfo);
                taskRow.addView(checkBox);

                taskListLayout.addView(taskRow);

            } while (cursor.moveToNext());
        } else {
            TextView noTasksText = new TextView(this);
            noTasksText.setText("No tasks assigned yet.");
            taskListLayout.addView(noTasksText);
        }

        cursor.close();
    }
    private void markTaskAsCompleted(int taskId) {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        db.execSQL("UPDATE tasks SET tStatus = 'completed' WHERE tID = ?", new Object[]{taskId});
    }


}
