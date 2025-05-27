package com.example.learningmanagementsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewModuleList extends AppCompatActivity {

    ListView moduleListView;
    ArrayList<Module> moduleArrayList;
    ModuleAdapter moduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_module_list);

        moduleListView = findViewById(R.id.moduleRecordList);
        moduleArrayList = new ArrayList<>();
        moduleAdapter = new ModuleAdapter(this, moduleArrayList);
        moduleListView.setAdapter(moduleAdapter);

        loadModules();
    }

    private void loadModules() {
        try {
            SQLiteDatabase db = DatabaseManager.getDB(this);
            db.execSQL("CREATE TABLE IF NOT EXISTS modules (id INTEGER PRIMARY KEY AUTOINCREMENT, mID VARCHAR, mName VARCHAR, mDuration VARCHAR)");

            Cursor cursor = db.rawQuery("SELECT * FROM modules", null);

            moduleArrayList.clear();

            while (cursor.moveToNext()) {
                Module module = new Module();
                module.mID = cursor.getString(cursor.getColumnIndexOrThrow("mID"));
                module.mName = cursor.getString(cursor.getColumnIndexOrThrow("mName"));
                module.mDuration = cursor.getString(cursor.getColumnIndexOrThrow("mDuration")); // Assuming mDuration is used as mCode

                moduleArrayList.add(module);
            }

            cursor.close();
            moduleAdapter.notifyDataSetChanged();
            moduleListView.invalidateViews();

        } catch (Exception e) {
            Toast.makeText(this, "FAILED: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
