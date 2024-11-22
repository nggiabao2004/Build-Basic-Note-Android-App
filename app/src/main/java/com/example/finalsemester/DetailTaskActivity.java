package com.example.finalsemester;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailTaskActivity extends AppCompatActivity {

    private DatabaseReminder database;
    private EditText editTextDetailTaskName, editTextDetailTaskDescription;
    private Button buttonBackFromDetail, buttonDeleteTask, buttonEditTask;
    private String taskName, accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = new DatabaseReminder(this); // Khởi tạo đối tượng DatabaseReminder

        // Ánh xạ các đối tượng UI từ layout
        editTextDetailTaskName = findViewById(R.id.editTextDetailTaskName);
        editTextDetailTaskDescription = findViewById(R.id.editTextDetailTaskDescription);

        buttonBackFromDetail = findViewById(R.id.buttonBackFromDetail);
        buttonDeleteTask = findViewById(R.id.buttonDeleteTask);
        buttonEditTask = findViewById(R.id.buttonEditTask);

        // Lấy tên công việc và ID tài khoản từ Intent
        taskName = getIntent().getStringExtra("task_name");
        accountId = getIntent().getStringExtra("account_id");

        loadTaskDetails();  // Tải thông tin công việc

        // Thiết lập sự kiện quay lại màn hình trước
        buttonBackFromDetail.setOnClickListener(v -> finish());

        // Xử lý sự kiện xóa công việc
        buttonDeleteTask.setOnClickListener(v -> {
            deleteTask();  // Gọi phương thức xóa công việc
            Toast.makeText(this, "Ghi chú đã được xóa!", Toast.LENGTH_SHORT).show();  // Hiển thị thông báo xóa thành công

            // Trả kết quả về màn hình trước
            Intent resultIntent = new Intent();
            resultIntent.putExtra("action", "delete");  // Gửi thông báo xóa công việc
            resultIntent.putExtra("task_name", taskName);
            setResult(RESULT_OK, resultIntent);
            finish();  // Đóng màn hình chi tiết
        });

        // Xử lý sự kiện sửa công việc
        buttonEditTask.setOnClickListener(v -> {
            String updatedDescription = editTextDetailTaskDescription.getText().toString();  // Lấy mô tả đã chỉnh sửa
            updateTask(updatedDescription);  // Gọi phương thức cập nhật công việc
            Toast.makeText(this, "Công việc đã được sửa!", Toast.LENGTH_SHORT).show();  // Thông báo sửa thành công
        });
    }

    // Phương thức tải chi tiết công việc từ cơ sở dữ liệu
    private void loadTaskDetails() {
        Cursor cursor = database.getTasksByAccount(accountId);  // Lấy danh sách công việc của tài khoản
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_NAME));
            if (name.equals(taskName)) {
                // Nếu tên công việc khớp, hiển thị thông tin chi tiết lên UI
                editTextDetailTaskName.setText(name);
                editTextDetailTaskDescription.setText(cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_DESCRIPTION)));
                break;  // Thoát vòng lặp sau khi tìm thấy công việc cần tìm
            }
        }
        cursor.close();  // Đóng con trỏ
    }

    // Phương thức xóa công việc
    private void deleteTask() {
        database.deleteTask(taskName, accountId);  // Xóa công việc từ cơ sở dữ liệu
    }

    // Phương thức cập nhật công việc
    private void updateTask(String description) {
        database.updateTask(taskName, description, accountId);  // Cập nhật công việc với mô tả mới
    }
}
