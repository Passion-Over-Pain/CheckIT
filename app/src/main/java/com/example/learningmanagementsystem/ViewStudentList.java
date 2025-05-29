package com.example.learningmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ViewStudentList extends AppCompatActivity {

    ListView studentRecords;
    ArrayList<Student> stud = new ArrayList<>();
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_student_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SQLiteDatabase db = DatabaseManager.getDB(this);
        studentRecords = findViewById(R.id.studentRecordList);

        adapter = new StudentAdapter(this, stud);
        studentRecords.setAdapter(adapter);

        final Cursor cursor = db.rawQuery("SELECT * FROM students", null);

        int sID = cursor.getColumnIndex("sID");
        int sName = cursor.getColumnIndex("sName");
        int sSurname = cursor.getColumnIndex("sSurname");
        int sDOB = cursor.getColumnIndex("sDOB");

        if (cursor.moveToFirst()) {
            do {
                Student stu = new Student();
                stu.sID = cursor.getString(sID);
                stu.sName = cursor.getString(sName);
                stu.sSurname = cursor.getString(sSurname);
                stu.sDOB = cursor.getString(sDOB);
                stud.add(stu);
            } while (cursor.moveToNext());

            adapter.notifyDataSetChanged(); // Notify custom adapter
            studentRecords.invalidateViews();
        }

        studentRecords.setOnItemClickListener((parent, view, position, id) -> {
            Student selectedStudent = stud.get(position);

            Intent intent = new Intent(ViewStudentList.this, UpdateStudent.class);
            intent.putExtra("sID", selectedStudent.sID);
            intent.putExtra("sName", selectedStudent.sName);
            intent.putExtra("sSurname", selectedStudent.sSurname);
            intent.putExtra("sDOB", selectedStudent.sDOB);
            startActivity(intent);
        });


        cursor.close(); // Always close cursor
    }
}
