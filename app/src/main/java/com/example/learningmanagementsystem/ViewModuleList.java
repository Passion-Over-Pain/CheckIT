package com.example.learningmanagementsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ViewModuleList extends AppCompatActivity {

    TextView txtModuleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_module_list);

        txtModuleList = findViewById(R.id.txtModuleList);
        loadModules();
    }

    private void loadModules() {
        try {
            SQLiteDatabase db = DatabaseManager.getDB(this);
            db.execSQL("CREATE TABLE IF NOT EXISTS modules (id INTEGER PRIMARY KEY AUTOINCREMENT, mID VARCHAR, mName VARCHAR, mDuration VARCHAR)");

            Cursor cursor = db.rawQuery("SELECT * FROM modules", null);

            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                String mID = cursor.getString(cursor.getColumnIndexOrThrow("mID"));
                String mName = cursor.getString(cursor.getColumnIndexOrThrow("mName"));
                String mDuration = cursor.getString(cursor.getColumnIndexOrThrow("mDuration"));

                builder.append("Module ID: ").append(mID).append("\n")
                        .append("Name: ").append(mName).append("\n")
                        .append("Duration: ").append(mDuration).append("\n\n");
            }

            cursor.close();

            if (builder.length() == 0) {
                builder.append("No modules found.");
            }

            txtModuleList.setText(builder.toString());

        } catch (Exception e) {
            Toast.makeText(this, "FAILED: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
