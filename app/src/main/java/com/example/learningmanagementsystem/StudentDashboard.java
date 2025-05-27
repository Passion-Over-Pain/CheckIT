package com.example.learningmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
                "SELECT tName, tDate, tModule FROM tasks WHERE tStudent = ?",new String[]{studentFullName}

        );

        if (cursor.moveToFirst()) {
            do {
                String taskName = cursor.getString(0);
                String dueDate = cursor.getString(1);
                String moduleName = cursor.getString(2);

                TextView textView = new TextView(this);
                textView.setText(
                        "Task: " + taskName + "\n" +
                                "Due Date: " + dueDate + "\n" +
                                "Module: " + moduleName + "\n"
                );
                textView.setPadding(0, 0, 0, 30);
                taskListLayout.addView(textView);

            } while (cursor.moveToNext());
        } else {
            TextView noTasksText = new TextView(this);
            noTasksText.setText("No tasks assigned yet.");
            taskListLayout.addView(noTasksText);
        }

        cursor.close();
    }
}
