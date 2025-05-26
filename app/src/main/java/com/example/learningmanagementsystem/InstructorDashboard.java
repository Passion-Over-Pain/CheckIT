package com.example.learningmanagementsystem;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

public class InstructorDashboard extends AppCompatActivity {

    EditText edtTaskName, edtDueDate;
    Spinner spinnerModules, spinnerStudents;
    Button btnCreateTask;

    ArrayList<String> moduleNames = new ArrayList<>();
    ArrayList<Integer> moduleIDs = new ArrayList<>();

    ArrayList<String> studentNames = new ArrayList<>();
    ArrayList<Integer> studentIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_dashboard);

        edtTaskName = findViewById(R.id.edtTaskName);
        edtDueDate = findViewById(R.id.edtDueDate);
        spinnerModules = findViewById(R.id.spinnerModules);
        spinnerStudents = findViewById(R.id.spinnerStudents);
        btnCreateTask = findViewById(R.id.btnCreateTask);

        loadModules();
        loadStudents();

        edtDueDate.setOnClickListener(v -> showDatePicker());

        btnCreateTask.setOnClickListener(v -> createTask());
    }

    private void loadModules() {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        Cursor cursor = db.rawQuery("SELECT mID, mName FROM modules", null);
        moduleNames.clear();
        moduleIDs.clear();

        while (cursor.moveToNext()) {
            moduleIDs.add(cursor.getInt(0));
            moduleNames.add(cursor.getString(1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moduleNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModules.setAdapter(adapter);

        cursor.close();
    }

    private void loadStudents() {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        Cursor cursor = db.rawQuery("SELECT sID, sName, sSurname FROM students", null);
        studentNames.clear();
        studentIDs.clear();

        while (cursor.moveToNext()) {
            studentIDs.add(cursor.getInt(0));
            String fullName = cursor.getString(1) + " " + cursor.getString(2);
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
        String taskName = edtTaskName.getText().toString().trim();
        String dueDate = edtDueDate.getText().toString().trim();

        if (taskName.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedModuleID = moduleIDs.get(spinnerModules.getSelectedItemPosition());
        int selectedStudentID = studentIDs.get(spinnerStudents.getSelectedItemPosition());

        SQLiteDatabase db = DatabaseManager.getDB(this);
        db.execSQL("INSERT INTO tasks (tName, tDate, tModule, tStudent, tStatus) VALUES (?, ?, ?, ?, ?)",
                new Object[]{taskName, dueDate, selectedModuleID, selectedStudentID, "incomplete"});

        Toast.makeText(this, "Task created and assigned.", Toast.LENGTH_SHORT).show();

        // Clear fields
        edtTaskName.setText("");
        edtDueDate.setText("");
        spinnerModules.setSelection(0);
        spinnerStudents.setSelection(0);
    }
}
