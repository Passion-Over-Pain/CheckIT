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
                String module = cursor.getString(2);
                int studentID = cursor.getInt(3);

                String studentName = getStudentName(db, studentID);

                TextView textView = new TextView(this);
                textView.setText(
                        "Task: " + taskName + "\n" +
                                "Due Date: " + dueDate + "\n" +
                                "Module: " + module + "\n" +
                                "Student: " + studentName + "\n"
                );
                textView.setPadding(0, 0, 0, 20);
                taskListLayout.addView(textView);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    private String getStudentName(SQLiteDatabase db, int studentID) {
        Cursor c = db.rawQuery("SELECT sName, sSurname FROM students WHERE sID = ?", new String[]{String.valueOf(studentID)});
        if (c.moveToFirst()) {
            String fullName = c.getString(0) + " " + c.getString(1);
            c.close();
            return fullName;
        }
        c.close();
        return "Unknown";
    }
}
