package com.example.learningmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseManager.initDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUseremail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.equals("admin") && password.equals("password")) {
                saveLogin("admin", username,"","");
                startActivity(new Intent(Login.this, AdminDashboard.class));
            } else {
                checkStudentLogin(username, password);
            }
        });
    }

    private void checkStudentLogin(String email, String password) {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        Cursor cursor = db.rawQuery("SELECT sName, sSurname FROM students WHERE sEmail=? AND sPassword=?", new String[]{email, password});

        if (cursor.moveToFirst()) {
            String sName = cursor.getString(0);
            String sSurname = cursor.getString(1);
            saveLogin("student", email,sName,sSurname);

            Intent intent = new Intent(Login.this, StudentDashboard.class);
            intent.putExtra("firstname", sName);
            intent.putExtra("lastname", sSurname);
            startActivity(intent);

        } else {
            checkInstructorLogin(email, password);
        }

        cursor.close();
    }

    private void checkInstructorLogin(String email, String password) {
        SQLiteDatabase db = DatabaseManager.getDB(this);
        Cursor cursor = db.rawQuery("SELECT iName FROM instructors WHERE iEmail=? AND iPassword=?", new String[]{email, password});

        if (cursor.moveToFirst()) {
            saveLogin("instructor", email,"","");
            startActivity(new Intent(Login.this, InstructorDashboard.class));
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    private void saveLogin(String role, String username, String firstname, String lastname) {
        SharedPreferences sharedPreferences = getSharedPreferences("LMSAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("role", role);
        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.apply();
    }
}
