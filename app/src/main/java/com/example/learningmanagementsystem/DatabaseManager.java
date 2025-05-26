package com.example.learningmanagementsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private static final String DB_NAME = "LMS.db";
    private static final int DB_MODE = Context.MODE_PRIVATE;

    public static void initDatabase(Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, DB_MODE, null);

        // Create Students table
        db.execSQL("CREATE TABLE IF NOT EXISTS students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Internal DB ID
                "sID TEXT, " +                              // Logical student ID
                "sName TEXT, " +
                "sSurname TEXT, " +
                "sDOB TEXT);");

        // Create Instructors table
        db.execSQL("CREATE TABLE IF NOT EXISTS instructors (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Internal DB ID
                "iID TEXT, " +                              // Logical instructor ID
                "iName TEXT, " +
                "iSurname TEXT, " +
                "iEmail TEXT);");

        // Create Modules table
        db.execSQL("CREATE TABLE IF NOT EXISTS modules (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Internal DB ID
                "mID TEXT, " +                              // Logical module ID
                "mName TEXT, " +
                "mDuration TEXT, " +
                "instructor_id TEXT, " +                    // Refers to instructors.iID
                "FOREIGN KEY (instructor_id) REFERENCES instructors(iID));");

        // Create Tasks table
        db.execSQL("CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Internal DB ID
                "tID TEXT, " +                              // Logical task ID
                "tName TEXT NOT NULL, " +
                "tDate TEXT NOT NULL, " +
                "tModule TEXT NOT NULL, " +                 // Refers to modules.mID
                "tStudent TEXT NOT NULL, " +                // Refers to students.sID
                "tStatus TEXT DEFAULT 'Incomplete', " +
                "FOREIGN KEY (tModule) REFERENCES modules(mID), " +
                "FOREIGN KEY (tStudent) REFERENCES students(sID));");
    }

    public static void dropAllTables(Context context) {
        SQLiteDatabase db = getDB(context);

        db.execSQL("DROP TABLE IF EXISTS tasks;");
        db.execSQL("DROP TABLE IF EXISTS modules;");
        db.execSQL("DROP TABLE IF EXISTS instructors;");
        db.execSQL("DROP TABLE IF EXISTS students;");
    }

    public static SQLiteDatabase getDB(Context context) {
        return context.openOrCreateDatabase(DB_NAME, DB_MODE, null);
    }
}
