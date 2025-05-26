package com.example.learningmanagementsystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ViewStudentList extends AppCompatActivity {

    ListView studentRecords;
    ArrayList<String> myStudentRecords = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
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
        SQLiteDatabase db = openOrCreateDatabase("lmsDB", Context.MODE_PRIVATE, null);
        studentRecords = findViewById(R.id.studentRecordList);

        final Cursor cursor = db.rawQuery("select * from students", null);

        int sID = cursor.getColumnIndex("sID");
        int sName = cursor.getColumnIndex("sName");
        int sSurname =  cursor.getColumnIndex("sSurname");
        int sDOB = cursor.getColumnIndex("sDOB");

        myStudentRecords.clear();

        arrayAdapter = new ArrayAdapter(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, myStudentRecords);
        studentRecords.setAdapter(arrayAdapter);

        final ArrayList<Student> stud = new ArrayList<Student>();

        if((cursor.moveToFirst()))
        {
            do


            {
                    Student stu = new Student();
                    stu.sID = cursor.getString(sID);
                    stu.sName = cursor.getString(sName);
                    stu.sSurname = cursor.getString(sSurname);
                    stu.sDOB = cursor.getString(sDOB);

                    stud.add(stu);

                    myStudentRecords.add(cursor.getString(sID) + "\t" + cursor.getString(sName) + "\t" + cursor.getString(sSurname) + "\t" + cursor.getString(sDOB));
            }
                while (cursor.moveToNext());
                arrayAdapter.notifyDataSetChanged();
                studentRecords.invalidateViews();

        }
    }
}