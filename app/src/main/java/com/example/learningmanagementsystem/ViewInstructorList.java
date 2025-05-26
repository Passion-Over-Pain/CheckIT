package com.example.learningmanagementsystem;

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

public class ViewInstructorList extends AppCompatActivity {

    ListView instructorRecords;
    ArrayList<String> myInstructorRecords = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

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

        final Cursor cursor = db.rawQuery("SELECT * FROM instructors", null);

        int iID = cursor.getColumnIndex("iID");
        int iName = cursor.getColumnIndex("iName");
        int iSurname = cursor.getColumnIndex("iSurname");
        int iEmail = cursor.getColumnIndex("iEmail");

        myInstructorRecords.clear();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myInstructorRecords);
        instructorRecords.setAdapter(arrayAdapter);

        final ArrayList<Instructor> instructors = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Instructor instructor = new Instructor();
                instructor.iID = cursor.getString(iID);
                instructor.iName = cursor.getString(iName);
                instructor.iSurname = cursor.getString(iSurname);
                instructor.iEmail = cursor.getString(iEmail);

                instructors.add(instructor);

                myInstructorRecords.add(
                        instructor.iID + "\t" +
                                instructor.iName + "\t" +
                                instructor.iSurname + "\t" +
                                instructor.iEmail
                );

            } while (cursor.moveToNext());

            arrayAdapter.notifyDataSetChanged();
            instructorRecords.invalidateViews();
        }
    }
}
