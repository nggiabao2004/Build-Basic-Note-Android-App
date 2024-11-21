package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText edtTaskName, edtTaskDescription;
    private Button btnBackToMain, btnAddTaskSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtTaskName = findViewById(R.id.edtTaskName);
        edtTaskDescription = findViewById(R.id.edtTaskDescription);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnAddTaskSubmit = findViewById(R.id.btnAddTaskSubmit);

        // Sự kiện quay lại màn hình chính
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddTaskActivity.this, HomeActivity.class));
            }
        });

        // Sự kiện thêm công việc
        btnAddTaskSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = edtTaskName.getText().toString();
                String taskDescription = edtTaskDescription.getText().toString();

                if (taskName.isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Tên công việc không thể trống", Toast.LENGTH_SHORT).show();
                } else {
                    // Lưu công việc vào database (sử dụng SQLite)
                    saveTask(taskName, taskDescription);
                    Toast.makeText(AddTaskActivity.this, "Công việc đã được thêm thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTaskActivity.this, HomeActivity.class));
                }
            }
        });
    }

    // Phương thức lưu công việc vào database (ví dụ)
    private void saveTask(String taskName, String taskDescription) {
        DatabaseReminder dbHelper = new DatabaseReminder(this);
        // Lấy accountId từ thông tin đăng nhập hiện tại (thêm logic nếu cần)
        String accountId = getIntent().getStringExtra("account_id");
        boolean isSuccess = dbHelper.addTask(taskName, taskDescription, accountId);
        if (!isSuccess) {
            Toast.makeText(this, "Lỗi khi thêm công việc", Toast.LENGTH_SHORT).show();
        }
        dbHelper.close();
    }
}
