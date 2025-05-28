package com.example.learningmanagementsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

public class InstructorDashboard extends AppCompatActivity {

    EditText edtTaskID, edtTaskName, edtDueDate;
    Spinner spinnerModules, spinnerStudents;
    Button btnCreateTask, btnViewTasks;

    ArrayList<String> moduleNames = new ArrayList<>();
    ArrayList<String> studentNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_dashboard);

        edtTaskID = findViewById(R.id.edtTaskID);
        edtTaskName = findViewById(R.id.edtTaskName);
        edtDueDate = findViewById(R.id.edtDueDate);
        spinnerModules = findViewById(R.id.spinnerModules);
        spinnerStudents = findViewById(R.id.spinnerStudents);
        btnCreateTask = findViewById(R.id.btnCreateTask);
        btnViewTasks = findViewById(R.id.btnViewTasks);

        loadModules();
        loadStudents();

        edtDueDate.setOnClickListener(v -> showDatePicker());

        btnCreateTask.setOnClickListener(v -> createTask());

        btnViewTasks.setOnClickListener(v -> {
            Intent intent = new Intent(InstructorDashboard.this, ViewTaskList.class);
            startActivity(intent);
        });
        EditText edtDeleteTaskID = findViewById(R.id.edtDeleteTaskID);
        Button btnDeleteTask = findViewById(R.id.btnDeleteTask);

        btnDeleteTask.setOnClickListener(v -> {
            String deleteID = edtDeleteTaskID.getText().toString().trim();

            if (deleteID.isEmpty()) {
                Toast.makeText(this, "Please enter Task ID to delete.", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = DatabaseManager.getDB(this);
            int deletedRows = db.delete("tasks", "tID = ?", new String[]{deleteID});

            if (deletedRows > 0) {
                Toast.makeText(this, "Task deleted successfully.", Toast.LENGTH_SHORT).show();
                edtDeleteTaskID.setText("");
            } else {
                Toast.makeText(this, "Task ID not found.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadModules() {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        Cursor cursor = db.rawQuery("SELECT mName FROM modules", null);
        moduleNames.clear();

        while (cursor.moveToNext()) {
            moduleNames.add(cursor.getString(0));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moduleNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModules.setAdapter(adapter);

        cursor.close();
    }

    private void loadStudents() {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        Cursor cursor = db.rawQuery("SELECT sName, sSurname FROM students", null);
        studentNames.clear();

        while (cursor.moveToNext()) {
            String fullName = cursor.getString(0) + " " + cursor.getString(1);
            studentNames.add(fullName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudents.setAdapter(adapter);

        cursor.close();
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String date = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
            edtDueDate.setText(date);
        }, year, month, day).show();
    }

    private void createTask() {
        String taskID = edtTaskID.getText().toString().trim();
        String taskName = edtTaskName.getText().toString().trim();
        String dueDate = edtDueDate.getText().toString().trim();

        if (taskID.isEmpty() || taskName.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedModule = spinnerModules.getSelectedItem().toString();
        String selectedStudent = spinnerStudents.getSelectedItem().toString();

        SQLiteDatabase db = DatabaseManager.getDB(this);
        db.execSQL("INSERT INTO tasks (tID, tName, tDate, tModule, tStudent, tStatus) VALUES (?, ?, ?, ?, ?, ?)",
                new Object[]{taskID, taskName, dueDate, selectedModule, selectedStudent, "incomplete"});

        Toast.makeText(this, "Task created and assigned.", Toast.LENGTH_SHORT).show();

        // Clear fields
        edtTaskID.setText("");
        edtTaskName.setText("");
        edtDueDate.setText("");
        spinnerModules.setSelection(0);
        spinnerStudents.setSelection(0);
    }
}
