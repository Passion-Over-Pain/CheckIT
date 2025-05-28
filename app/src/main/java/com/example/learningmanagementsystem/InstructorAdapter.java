package com.example.learningmanagementsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class InstructorAdapter extends ArrayAdapter<Instructor> {
    private final Activity context;
    private final ArrayList<Instructor> instructors;

    public InstructorAdapter(Activity context, ArrayList<Instructor> instructors) {
        super(context, R.layout.instructor_list_item, instructors);
        this.context = context;
        this.instructors = instructors;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.instructor_list_item, null, true);

        TextView nameView = rowView.findViewById(R.id.txtInstructorName);
        TextView emailView = rowView.findViewById(R.id.txtInstructorEmail);
//        ImageView imageView = rowView.findViewById(R.id.);

        Instructor instructor = instructors.get(position);
        nameView.setText(instructor.iName + " " + instructor.iSurname);
        emailView.setText(instructor.iEmail);


        return rowView;
    }
}
