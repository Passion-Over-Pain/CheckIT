package com.example.learningmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CreateModule extends AppCompatActivity {

    EditText edtModuleID, edtModuleName, edtModuleDuration;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_module);

        edtModuleID = findViewById(R.id.edtModuleID);
        edtModuleName = findViewById(R.id.edtModuleName);
        edtModuleDuration = findViewById(R.id.edtModuleDuration);
        btnSubmit = findViewById(R.id.btnSubmitModule);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertModule();
            }
        });


    }

    private void insertModule() {
        try {
            String mID = edtModuleID.getText().toString();
            String mName = edtModuleName.getText().toString();
            String mDuration = edtModuleDuration.getText().toString();

            SQLiteDatabase db = DatabaseManager.getDB(this);
            db.execSQL("CREATE TABLE IF NOT EXISTS modules (id INTEGER PRIMARY KEY AUTOINCREMENT, mID VARCHAR, mName VARCHAR, mDuration VARCHAR)");

            String sql = "INSERT INTO modules (mID, mName, mDuration) VALUES (?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, mID);
            statement.bindString(2, mName);
            statement.bindString(3, mDuration);

            statement.executeInsert();
            Toast.makeText(this, "SUCCESS: Module Record Added", Toast.LENGTH_LONG).show();

            edtModuleID.setText("");
            edtModuleName.setText("");
            edtModuleDuration.setText("");

        } catch (Exception ex) {
            Toast.makeText(this, "FAILED: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
