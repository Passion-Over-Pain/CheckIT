package com.example.learningmanagementsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdateTask extends AppCompatActivity {

    EditText edtTaskID, edtTaskName, edtTaskDate;
    Spinner spnTaskModule, spnTaskStudent, spnTaskStatus;
    Button btnUpdateTask, btnDeleteTask;
    SQLiteDatabase db;
    String tID;

    ArrayList<String> moduleList = new ArrayList<>();
    ArrayList<String> studentList = new ArrayList<>();
    ArrayList<String> statusList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        db = DatabaseManager.getDB(this);

        // Bind UI
        edtTaskID = findViewById(R.id.edtUpdateTaskID);
        edtTaskName = findViewById(R.id.edtUpdateTaskName);
        edtTaskDate = findViewById(R.id.edtUpdateTaskDueDate);
        spnTaskModule = findViewById(R.id.spinnerUpdateTaskModule);
        spnTaskStudent = findViewById(R.id.spinnerUpdateTaskStudent);
        spnTaskStatus = findViewById(R.id.spinnerUpdateTaskStatus);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);

        // Populate Spinners
        loadModules();
        loadStudents();
        loadStatusOptions();

        // Get task data from intent
        Intent intent = getIntent();
        tID = intent.getStringExtra("tID");
        String tName = intent.getStringExtra("tName");
        String tDate = intent.getStringExtra("tDate");
        String tModule = intent.getStringExtra("tModule");
        String tStudent = intent.getStringExtra("tStudent");
        String tStatus = intent.getStringExtra("tStatus");

        // Prefill inputs
        edtTaskID.setText(tID);
        edtTaskID.setEnabled(false);
        edtTaskName.setText(tName);
        edtTaskDate.setText(tDate);

        setSpinnerSelection(spnTaskModule, tModule);
        setSpinnerSelection(spnTaskStudent, tStudent);
        setSpinnerSelection(spnTaskStatus, tStatus);

        btnUpdateTask.setOnClickListener(v -> updateTask());
        btnDeleteTask.setOnClickListener(v -> deleteTask());
    }

    private void loadModules() {
        Cursor cursor = db.rawQuery("SELECT mName FROM modules", null);
        if (cursor.moveToFirst()) {
            do {
                moduleList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        spnTaskModule.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleList));
    }

    private void loadStudents() {
        Cursor cursor = db.rawQuery("SELECT sName,sSurname FROM students", null);
        if (cursor.moveToFirst()) {
            do {
                String StudentName = cursor.getString(0);
                String StudentSurname = cursor.getString(1);
                String StudentFullName = StudentName + " " + StudentSurname;
                studentList.add(StudentFullName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        spnTaskStudent.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, studentList));
    }

    private void loadStatusOptions() {
        statusList.add("Pending");
        statusList.add("In Progress");
        statusList.add("Completed");
        spnTaskStatus.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusList));
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            }
        }
    }

    private void updateTask() {
        ContentValues values = new ContentValues();
        values.put("tName", edtTaskName.getText().toString().trim());
        values.put("tDate", edtTaskDate.getText().toString().trim());
        values.put("tModule", spnTaskModule.getSelectedItem().toString());
        values.put("tStudent", spnTaskStudent.getSelectedItem().toString());
        values.put("tStatus", spnTaskStatus.getSelectedItem().toString());

        int rows = db.update("tasks", values, "tID = ?", new String[]{tID});
        if (rows > 0) {
            Toast.makeText(this, "Task updated successfully.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Failed to update task.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTask() {
        int rows = db.delete("tasks", "tID = ?", new String[]{tID});
        if (rows > 0) {
            Toast.makeText(this, "Task deleted successfully.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Failed to delete task.", Toast.LENGTH_SHORT).show();
        }
    }
}
