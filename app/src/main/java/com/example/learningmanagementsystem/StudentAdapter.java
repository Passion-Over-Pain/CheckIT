package com.example.learningmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {
    private final Context context;
    private final ArrayList<Student> students;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        super(context, 0, students);
        this.context = context;
        this.students = students;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = students.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false);
        }

        TextView txtName = convertView.findViewById(R.id.txtStudentName);
        TextView txtDOB = convertView.findViewById(R.id.txtStudentDOB);
//        ImageView imgIcon = convertView.findViewById(R.id.createstudentIcon); // optional if needed by ID

        txtName.setText(student.sName + " " + student.sSurname);
        txtDOB.setText("DOB: " + student.sDOB);

        return convertView;
    }
}

