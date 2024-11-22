package com.example.finalsemester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Context context; // Biến lưu trữ ngữ cảnh (context) của ứng dụng
    private ArrayList<Task> taskList; // Danh sách các công việc (tasks)

    // Constructor: Khởi tạo đối tượng Adapter với ngữ cảnh và danh sách công việc
    public TaskAdapter(Context context, ArrayList<Task> taskList) {
        super(context, 0, taskList); // Gọi constructor của ArrayAdapter
        this.context = context; // Gán ngữ cảnh cho biến context
        this.taskList = taskList; // Gán danh sách công việc cho biến taskList
    }

    // Phương thức getView() để tạo và hiển thị mỗi item trong danh sách
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra xem view có được tái sử dụng hay không
        if (convertView == null) {
            // Nếu chưa có view, tạo một view mới từ layout item_task.xml
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        }

        // Lấy công việc tại vị trí hiện tại trong danh sách
        Task task = taskList.get(position);

        // Ánh xạ các TextView từ layout item_task
        TextView taskNameTextView = convertView.findViewById(R.id.taskName); // Tên công việc
        TextView taskDescriptionTextView = convertView.findViewById(R.id.taskDescription); // Mô tả công việc

        // Gán dữ liệu từ công việc vào các TextView
        taskNameTextView.setText(task.getName()); // Gán tên công việc vào TextView
        taskDescriptionTextView.setText(task.getDescription()); // Gán mô tả công việc vào TextView

        // Trả về view đã được tạo và điền dữ liệu
        return convertView;
    }
}
