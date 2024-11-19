package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailTaskActivity extends AppCompatActivity {

    private EditText edtTaskNameDetail, edtTaskDescriptionDetail;
    private Button btnBackFromDetail, btnCompleteTask, btnDeleteTask, btnEditTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        edtTaskNameDetail = findViewById(R.id.editTextDetailTaskName);
        edtTaskDescriptionDetail = findViewById(R.id.editTextDetailTaskDescription);
        btnBackFromDetail = findViewById(R.id.buttonBackFromDetail);
        btnCompleteTask = findViewById(R.id.buttonCompleteTask);
        btnDeleteTask = findViewById(R.id.buttonDeleteTask);
        btnEditTask = findViewById(R.id.buttonEditTask);

        // Sự kiện quay lại màn hình chính
        btnBackFromDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailTaskActivity.this, HomeActivity.class));
            }
        });

        // Sự kiện đánh dấu công việc hoàn thành
        btnCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailTaskActivity.this, "Công việc đã hoàn thành", Toast.LENGTH_SHORT).show();
                // Lưu lại trạng thái công việc hoàn thành vào database
            }
        });

        // Sự kiện xóa công việc
        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailTaskActivity.this, "Công việc đã xóa", Toast.LENGTH_SHORT).show();
                // Xóa công việc từ database
                startActivity(new Intent(DetailTaskActivity.this, HomeActivity.class));
            }
        });

        // Sự kiện sửa công việc
        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = edtTaskNameDetail.getText().toString();
                String taskDescription = edtTaskDescriptionDetail.getText().toString();

                if (taskName.isEmpty()) {
                    Toast.makeText(DetailTaskActivity.this, "Tên công việc không thể trống", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật công việc trong database
                    Toast.makeText(DetailTaskActivity.this, "Thông tin công việc đã được sửa", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailTaskActivity.this, HomeActivity.class));
                }
            }
        });
    }
}
