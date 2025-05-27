package com.example.learningmanagementsystem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class CreateStudent extends AppCompatActivity {

    EditText edtStudentID, edtStudentName, edtStudentSurname, edtStudentDOB;
    Button btnSubmit, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_student);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        edtStudentID = findViewById(R.id.edtStudentID);
        edtStudentName = findViewById(R.id.edtStudentName);
        edtStudentSurname = findViewById(R.id.edtStudentSurname);
        edtStudentDOB = findViewById(R.id.edtStudentDOB);
        btnSubmit = findViewById(R.id.btnSubmitStudent);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudent();
            }
        });

        EditText edtStudentDOB = findViewById(R.id.edtStudentDOB);

        edtStudentDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateStudent.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            // Format: yyyy-MM-dd
                            String dob = selectedYear + "-" + String.format("%02d", (selectedMonth + 1)) + "-" + String.format("%02d", selectedDay);
                            edtStudentDOB.setText(dob);
                        }, year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();
            }


        });



    }


    private void insertStudent() {
        try {
            String sID = edtStudentID.getText().toString();
            String sName = edtStudentName.getText().toString();
            String sSurname = edtStudentSurname.getText().toString();
            String sDOB = edtStudentDOB.getText().toString();

            SQLiteDatabase db = DatabaseManager.getDB(this);
            db.execSQL("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY AUTOINCREMENT, sID VARCHAR, sName VARCHAR, sSurname VARCHAR, sDOB VARCHAR)");

            String sql = "INSERT INTO students (sID, sName, sSurname, sDOB) VALUES (?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, sID);
            statement.bindString(2, sName);
            statement.bindString(3, sSurname);
            statement.bindString(4, sDOB);

            statement.executeInsert();
            Toast.makeText(this, "SUCCESS: Student Record Added", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(this, "FAILED: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
