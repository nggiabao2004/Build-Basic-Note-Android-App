package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private DatabaseReminder database; // Đối tượng DatabaseReminder để tương tác với cơ sở dữ liệu
    private EditText edtTaskName, edtTaskDescription; // Các EditText để nhập tên và mô tả công việc
    private String accountId; // Biến lưu trữ id của tài khoản đang đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add); // Gán layout cho activity

        database = new DatabaseReminder(this); // Khởi tạo đối tượng DatabaseReminder

        edtTaskName = findViewById(R.id.edtTaskName); // Ánh xạ EditText cho tên công việc
        edtTaskDescription = findViewById(R.id.edtTaskDescription); // Ánh xạ EditText cho mô tả công việc

        // Lấy id của tài khoản từ Intent được truyền qua
        accountId = getIntent().getStringExtra("account_id");

        // Ánh xạ các nút trong layout
        Button btnBackToMain = findViewById(R.id.btnBackToMain); // Nút quay lại màn hình chính
        Button btnAddTaskSubmit = findViewById(R.id.btnAddTaskSubmit); // Nút thêm công việc mới

        // Xử lý sự kiện nhấn nút quay lại màn hình chính
        btnBackToMain.setOnClickListener(view -> finish());  // Quay lại màn hình chính

        // Xử lý sự kiện nhấn nút thêm công việc
        btnAddTaskSubmit.setOnClickListener(view -> {
            String taskName = edtTaskName.getText().toString(); // Lấy tên công việc từ EditText
            String taskDescription = edtTaskDescription.getText().toString(); // Lấy mô tả công việc từ EditText

            // Kiểm tra nếu tên công việc rỗng
            if (taskName.isEmpty()) {
                Toast.makeText(AddTaskActivity.this, "Tên công việc không thể trống", Toast.LENGTH_SHORT).show();
            } else {
                // Lưu công việc vào cơ sở dữ liệu nếu tên công việc không trống
                saveTask(taskName, taskDescription);
                Toast.makeText(AddTaskActivity.this, "Công việc đã được thêm thành công", Toast.LENGTH_SHORT).show();

                // Trả kết quả về màn hình chính để cập nhật danh sách công việc
                Intent intent = new Intent();
                intent.putExtra("action", "add"); // Gửi thông báo đã thêm công việc
                setResult(RESULT_OK, intent); // Thiết lập kết quả trả về
                finish(); // Kết thúc activity này
            }
        });
    }

    // Phương thức lưu công việc vào cơ sở dữ liệu
    private void saveTask(String taskName, String taskDescription) {
        database.addTask(taskName, taskDescription, accountId); // Thêm công việc vào cơ sở dữ liệu
    }
}
