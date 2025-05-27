package com.example.learningmanagementsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class ModuleAdapter extends ArrayAdapter<Module> {

    private final Activity context;
    private final ArrayList<Module> modules;

    public ModuleAdapter(Activity context, ArrayList<Module> modules) {
        super(context, R.layout.module_list_item, modules);
        this.context = context;
        this.modules = modules;
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.module_list_item, null, true);

        TextView moduleName = rowView.findViewById(R.id.txtModuleName);
        TextView moduleCode = rowView.findViewById(R.id.txtModuleCode);

        Module current = modules.get(position);

        moduleName.setText(current.mName);
        moduleCode.setText("Code: " + current.mID);

        return rowView;
    }
}
