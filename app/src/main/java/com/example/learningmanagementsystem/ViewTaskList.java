package com.example.learningmanagementsystem;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewTaskList extends AppCompatActivity {

    LinearLayout taskListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_list);

        taskListLayout = findViewById(R.id.taskListLayout);

        loadTasks();
    }

    @SuppressLint("SetTextI18n")
    private void loadTasks() {
        SQLiteDatabase db = DatabaseManager.getDB(this);

        Cursor cursor = db.rawQuery("SELECT tName, tDate, tModule, tStudent FROM tasks", null);

        if (cursor.moveToFirst()) {
            do {
                String taskName = cursor.getString(0);
                String dueDate = cursor.getString(1);
                String moduleName = cursor.getString(2);     // Now a string
                String studentFullName = cursor.getString(3); // Now a string

                TextView textView = new TextView(this);
                textView.setText(
                        "Task: " + taskName + "\n" +
                                "Due Date: " + dueDate + "\n" +
                                "Module: " + moduleName + "\n" +
                                "Student: " + studentFullName + "\n"
                );
                textView.setPadding(0, 0, 0, 20);
                taskListLayout.addView(textView);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
