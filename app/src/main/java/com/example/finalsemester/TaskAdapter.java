package com.example.finalsemester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Context context;
    private ArrayList<Task> taskList;

    public TaskAdapter(Context context, ArrayList<Task> taskList) {
        super(context, 0, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        }

        // Lấy công việc tại vị trí hiện tại
        Task task = taskList.get(position);

        // Gán dữ liệu vào các view trong layout item_task
        TextView taskNameTextView = convertView.findViewById(R.id.taskName);
        TextView taskDescriptionTextView = convertView.findViewById(R.id.taskDescription);

        taskNameTextView.setText(task.getName());
        taskDescriptionTextView.setText(task.getDescription());

        return convertView;
    }
}
