package com.example.learningmanagementsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateInstructor extends AppCompatActivity {

    EditText edtInstructorID, edtInstructorName, edtInstructorSurname, edtInstructorEmail, edtInstructorPassword;
    Button btnUpdateInstructor, btnDeleteInstructor;
    SQLiteDatabase db;
    String iID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_instructor);

        // Initialize DB
        db = DatabaseManager.getDB(this);

        // Bind UI elements
        edtInstructorID = findViewById(R.id.edtUpdateInstructorID);
        edtInstructorName = findViewById(R.id.edtUpdateInstructorName);
        edtInstructorSurname = findViewById(R.id.edtUpdateInstructorSurname);
        edtInstructorEmail = findViewById(R.id.edtUpdateInstructorEmail);
        edtInstructorPassword = findViewById(R.id.edtUpdateInstructorPassword);
        btnUpdateInstructor = findViewById(R.id.btnUpdateInstructor);
        btnDeleteInstructor = findViewById(R.id.btnDeleteInstructor);

        // Get intent extras
        Intent intent = getIntent();
        iID = intent.getStringExtra("iID");
        String iName = intent.getStringExtra("iName");
        String iSurname = intent.getStringExtra("iSurname");
        String iEmail = intent.getStringExtra("iEmail");
        String iPassword = intent.getStringExtra("iPassword");

        // Prefill inputs
        edtInstructorID.setText(iID);
        edtInstructorName.setText(iName);
        edtInstructorSurname.setText(iSurname);
        edtInstructorEmail.setText(iEmail);
        edtInstructorPassword.setText(iPassword);

        // Update logic
        btnUpdateInstructor.setOnClickListener(v -> updateInstructor());

        // Delete logic
        btnDeleteInstructor.setOnClickListener(v -> deleteInstructor());
    }

    private void updateInstructor() {
        ContentValues values = new ContentValues();
        values.put("iID", edtInstructorID.getText().toString().trim());
        values.put("iName", edtInstructorName.getText().toString().trim());
        values.put("iSurname", edtInstructorSurname.getText().toString().trim());
        values.put("iEmail", edtInstructorEmail.getText().toString().trim());
        values.put("iPassword", edtInstructorPassword.getText().toString());

        int rows = db.update("instructors", values, "iID = ?", new String[]{iID});
        if (rows > 0) {
            Toast.makeText(this, "Instructor updated successfully.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        } else {
            Toast.makeText(this, "Failed to update instructor.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteInstructor() {
        int rows = db.delete("instructors", "iID = ?", new String[]{iID});
        if (rows > 0) {
            Toast.makeText(this, "Instructor deleted successfully.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        } else {
            Toast.makeText(this, "Failed to delete instructor.", Toast.LENGTH_SHORT).show();
        }
    }
}
