package com.example.learningmanagementsystem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {

    EditText edtStudentID, edtStudentName, edtStudentSurname, edtStudentDOB, edtStudentPassword;
    Button btnUpdateStudent, btnDeleteStudent;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        db = DatabaseManager.getDB(this);

        edtStudentID = findViewById(R.id.edtStudentID);
        edtStudentName = findViewById(R.id.edtStudentName);
        edtStudentSurname = findViewById(R.id.edtStudentSurname);
        edtStudentDOB = findViewById(R.id.edtStudentDOB);
        edtStudentPassword = findViewById(R.id.edtStudentPassword);
        btnUpdateStudent = findViewById(R.id.btnSubmitStudent); // reuse existing button
        btnDeleteStudent = new Button(this); // or define in XML

        // Pre-fill fields
        Intent intent = getIntent();
        String sID = intent.getStringExtra("sID");
        String sName = intent.getStringExtra("sName");
        String sSurname = intent.getStringExtra("sSurname");
        String sDOB = intent.getStringExtra("sDOB");

        edtStudentID.setText(sID);
        edtStudentName.setText(sName);
        edtStudentSurname.setText(sSurname);
        edtStudentDOB.setText(sDOB);
        edtStudentPassword.setText(""); // password may not be passed

        // Disable editing Student ID (Primary Key)
        edtStudentID.setEnabled(false);



        btnUpdateStudent.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            values.put("sName", edtStudentName.getText().toString());
            values.put("sSurname", edtStudentSurname.getText().toString());
            values.put("sDOB", edtStudentDOB.getText().toString());
            values.put("sPassword", edtStudentPassword.getText().toString());

            int rows = db.update("students", values, "sID = ?", new String[]{sID});
            if (rows > 0) {
                Toast.makeText(this, "Student updated.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Update failed.", Toast.LENGTH_SHORT).show();
            }
        });


        btnDeleteStudent.setOnClickListener(v -> {
            int rows = db.delete("students", "sID = ?", new String[]{sID});
            if (rows > 0) {
                Toast.makeText(this, "Student deleted.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Delete failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
