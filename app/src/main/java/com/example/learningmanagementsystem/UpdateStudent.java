package com.example.learningmanagementsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudent extends AppCompatActivity {

    EditText edtStudentID, edtStudentName, edtStudentSurname, edtStudentDOB, edtStudentPassword;
    Button btnUpdateStudent, btnDeleteStudent;
    SQLiteDatabase db;
    String sID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        // Initialize DB
        db = DatabaseManager.getDB(this);

        // Bind UI elements
        edtStudentID = findViewById(R.id.edtStudentID);
        edtStudentName = findViewById(R.id.edtStudentName);
        edtStudentSurname = findViewById(R.id.edtStudentSurname);
        edtStudentDOB = findViewById(R.id.edtStudentDOB);
        edtStudentPassword = findViewById(R.id.edtStudentPassword);
        btnUpdateStudent = findViewById(R.id.btnSubmitStudent);
        btnDeleteStudent = findViewById(R.id.btnDeleteStudent);

        // Get intent extras
        Intent intent = getIntent();
        sID = intent.getStringExtra("sID");
        String sName = intent.getStringExtra("sName");
        String sSurname = intent.getStringExtra("sSurname");
        String sDOB = intent.getStringExtra("sDOB");

        // Prefill inputs
        edtStudentID.setText(sID);
        edtStudentID.setEnabled(false); // Primary Key - not editable
        edtStudentName.setText(sName);
        edtStudentSurname.setText(sSurname);
        edtStudentDOB.setText(sDOB);
        edtStudentPassword.setText("password");

        // Update student logic
        btnUpdateStudent.setOnClickListener(v -> updateStudent());

        // Delete student logic
        btnDeleteStudent.setOnClickListener(v -> deleteStudent());
    }

    private void updateStudent() {
        ContentValues values = new ContentValues();
        values.put("sID", edtStudentID.getText().toString().trim());
        values.put("sName", edtStudentName.getText().toString().trim());
        values.put("sSurname", edtStudentSurname.getText().toString().trim());
        values.put("sDOB", edtStudentDOB.getText().toString().trim());
        values.put("sPassword", edtStudentPassword.getText().toString());

        int rows = db.update("students", values, "sID = ?", new String[]{sID});
        if (rows > 0) {
            Toast.makeText(this, "Student updated successfully.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        } else {
            Toast.makeText(this, "Failed to update student.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteStudent() {
        int rows = db.delete("students", "sID = ?", new String[]{sID});
        if (rows > 0) {
            Toast.makeText(this, "Student deleted successfully.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        } else {
            Toast.makeText(this, "Failed to delete student.", Toast.LENGTH_SHORT).show();
        }
    }
}
