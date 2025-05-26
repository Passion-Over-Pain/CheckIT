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
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT, " +
                "course TEXT);");

        // Create Instructors table
        db.execSQL("CREATE TABLE IF NOT EXISTS instructors (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT, " +
                "department TEXT);");

        // Create Modules table
        db.execSQL("CREATE TABLE IF NOT EXISTS modules (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "description TEXT, " +
                "instructor_id INTEGER);");
    }

    public static SQLiteDatabase getDB(Context context) {
        return context.openOrCreateDatabase(DB_NAME, DB_MODE, null);
    }
}
