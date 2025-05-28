package com.example.learningmanagementsystem;

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

public class ViewInstructorList extends AppCompatActivity {

    ListView instructorRecords;
    ArrayList<Instructor> instructors = new ArrayList<>();
    InstructorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_instructor_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        instructorRecords = findViewById(R.id.instructorRecordList);
        SQLiteDatabase db = DatabaseManager.getDB(this);

        Cursor cursor = db.rawQuery("SELECT * FROM instructors", null);

        int iID = cursor.getColumnIndex("iID");
        int iName = cursor.getColumnIndex("iName");
        int iSurname = cursor.getColumnIndex("iSurname");
        int iEmail = cursor.getColumnIndex("iEmail");

        instructors.clear();
        adapter = new InstructorAdapter(this, instructors);
        instructorRecords.setAdapter(adapter);

        if (cursor.moveToFirst()) {
            do {
                Instructor instructor = new Instructor();
                instructor.iID = cursor.getString(iID);
                instructor.iName = cursor.getString(iName);
                instructor.iSurname = cursor.getString(iSurname);
                instructor.iEmail = cursor.getString(iEmail);

                instructors.add(instructor);
            } while (cursor.moveToNext());

            adapter.notifyDataSetChanged();
            instructorRecords.invalidateViews();
        }

        cursor.close();
    }
}
