package com.example.learningmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);


        RelativeLayout btnCreateStudent = findViewById(R.id.btnCreateStudent);

        RelativeLayout btnCreateInstructor = findViewById(R.id.btnCreateInstructor);

        RelativeLayout btnCreateModule = findViewById(R.id.btnCreateModule);

        RelativeLayout btnView = findViewById(R.id.btnViewStudents);

        RelativeLayout btnViewInstructors = findViewById(R.id.btnViewInstructors);

        RelativeLayout btnViewModule = findViewById(R.id.btnViewModules);

        btnViewModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewModuleList.class));
            }
        });

        btnViewInstructors.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ViewInstructorList.class)));


        btnCreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, CreateStudent.class));
            }
        });

        btnCreateInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, CreateInstructor.class));
            }
        });

        btnCreateModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this, CreateModule.class));
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewStudentList.class));
            }
        });

    }
}