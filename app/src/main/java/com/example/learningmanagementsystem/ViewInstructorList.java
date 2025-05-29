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
        adapter = new InstructorAdapter(this, instructors);
        instructorRecords.setAdapter(adapter);

        refreshInstructorList(); // Initial load

        instructorRecords.setOnItemClickListener((parent, view, position, id) -> {
            Instructor selectedInstructor = instructors.get(position);
            Intent intent = new Intent(ViewInstructorList.this, UpdateInstructor.class);
            intent.putExtra("iID", selectedInstructor.iID);
            intent.putExtra("iName", selectedInstructor.iName);
            intent.putExtra("iSurname", selectedInstructor.iSurname);
            intent.putExtra("iPassword", selectedInstructor.iPassword); // Assuming this exists
            intent.putExtra("iEmail", selectedInstructor.iEmail);
            startActivity(intent); // No need for startActivityForResult
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshInstructorList(); // Refresh list when activity regains focus
    }

    private void refreshInstructorList() {
        instructors.clear(); // Clear the list

        SQLiteDatabase db = DatabaseManager.getDB(this);
        Cursor cursor = db.rawQuery("SELECT * FROM instructors", null);

        int iID = cursor.getColumnIndex("iID");
        int iName = cursor.getColumnIndex("iName");
        int iSurname = cursor.getColumnIndex("iSurname");
        int iEmail = cursor.getColumnIndex("iEmail");
        int iPassword = cursor.getColumnIndex("iPassword"); // Assuming you store this

        if (cursor.moveToFirst()) {
            do {
                Instructor instructor = new Instructor();
                instructor.iID = cursor.getString(iID);
                instructor.iName = cursor.getString(iName);
                instructor.iSurname = cursor.getString(iSurname);
                instructor.iEmail = cursor.getString(iEmail);
                instructor.iPassword = cursor.getString(iPassword); // Assuming this field exists

                instructors.add(instructor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        adapter.notifyDataSetChanged();
        instructorRecords.invalidateViews();
    }
}
