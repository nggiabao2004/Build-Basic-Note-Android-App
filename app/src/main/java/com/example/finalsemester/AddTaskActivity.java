package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTaskName, editTextTaskDescription, editTextTaskDeadline;
    private Button buttonBackToMainFromAdd, buttonAddTaskToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Khởi tạo các view
        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        editTextTaskDeadline = findViewById(R.id.editTextTaskDeadline);
        buttonBackToMainFromAdd = findViewById(R.id.buttonBackToMainFromAdd);
        buttonAddTaskToList = findViewById(R.id.buttonAddTaskToList);

        // Sự kiện quay lại màn hình chính
        buttonBackToMainFromAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
            }
        });

        // Sự kiện thêm công việc
        buttonAddTaskToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = editTextTaskName.getText().toString();
                String taskDescription = editTextTaskDescription.getText().toString();
                String taskDeadline = editTextTaskDeadline.getText().toString();

                // Kiểm tra nếu tất cả các trường đều có dữ liệu
                if (!taskName.isEmpty() && !taskDescription.isEmpty() && !taskDeadline.isEmpty()) {
                    // Lưu công việc vào database
                    // DatabaseReminder.getInstance(AddTaskActivity.this).addTask(new Task(taskName, taskDescription, taskDeadline));

                    Toast.makeText(AddTaskActivity.this, "Công việc đã được thêm", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(AddTaskActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
