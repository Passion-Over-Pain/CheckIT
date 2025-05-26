package com.example.learningmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
//Focusing on design now
    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseManager.initDatabase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();

            // TODO: Validate credentials from SQL Server using JDBC
            // For now, assume dummy logic:
            if(username.equals("admin") && password.equals("password")){
                saveLogin("admin", username);
             startActivity(new Intent(Login.this, AdminDashboard.class));
            }
            else if(username.equals("instructor") && password.equals("password")){
                saveLogin("instructor", username);
               startActivity(new Intent(Login.this, InstructorDashboard.class));
            }
            else if(username.equals("student") && password.equals("password")){
                saveLogin("student", username);
                startActivity(new Intent(Login.this, StudentDashboard.class));
            }
            else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLogin(String role, String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("LMSAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("role", role);
        editor.apply();
    }

}