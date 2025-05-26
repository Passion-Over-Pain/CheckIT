package com.example.learningmanagementsystem;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateInstructor extends AppCompatActivity {

    EditText edtID, edtName, edtSurname, edtEmail;
    Button btnSubmit, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_instructor);


        edtID = findViewById(R.id.edtInstructorID);
        edtName = findViewById(R.id.edtInstructorName);
        edtSurname = findViewById(R.id.edtInstructorSurname);
        edtEmail = findViewById(R.id.edtInstructorEmail);
        btnSubmit = findViewById(R.id.btnSubmitInstructor);
        btnView = findViewById(R.id.btnViewInstructors);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertInstructor();
            }
        });

        btnView.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ViewInstructorList.class)));
    }

    private void insertInstructor() {
        try {
            String ID = edtID.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String surname = edtSurname.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            if (ID.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = DatabaseManager.getDB(this);
            db.execSQL("CREATE TABLE IF NOT EXISTS instructors (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "iID TEXT," +
                    "iName TEXT, " +
                    "iSurname TEXT, " +
                    "iEmail TEXT)");

            String sql = "INSERT INTO instructors (iID, iName, iSurname, iEmail) VALUES (?, ?, ?, ?)";
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1,ID);
            stmt.bindString(2, name);
            stmt.bindString(3, surname);
            stmt.bindString(4, email);

            stmt.executeInsert();
            Toast.makeText(this, "SUCCESS: Instructor Added", Toast.LENGTH_LONG).show();

            // Clear the fields after successful insert
            edtName.setText("");
            edtSurname.setText("");
            edtEmail.setText("");

        } catch (Exception e) {
            Toast.makeText(this, "FAILED: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
