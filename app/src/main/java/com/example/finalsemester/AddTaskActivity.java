package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private DatabaseReminder database;
    private EditText edtTaskName, edtTaskDescription;
    private String accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        database = new DatabaseReminder(this);

        edtTaskName = findViewById(R.id.edtTaskName);
        edtTaskDescription = findViewById(R.id.edtTaskDescription);

        accountId = getIntent().getStringExtra("account_id");

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        Button btnAddTaskSubmit = findViewById(R.id.btnAddTaskSubmit);

        btnBackToMain.setOnClickListener(view -> finish());  // Quay lại màn hình chính

        btnAddTaskSubmit.setOnClickListener(view -> {
            String taskName = edtTaskName.getText().toString();
            String taskDescription = edtTaskDescription.getText().toString();

            if (taskName.isEmpty()) {
                Toast.makeText(AddTaskActivity.this, "Tên công việc không thể trống", Toast.LENGTH_SHORT).show();
            } else {
                saveTask(taskName, taskDescription);
                Toast.makeText(AddTaskActivity.this, "Công việc đã được thêm thành công", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("action", "add");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void saveTask(String taskName, String taskDescription) {
        database.addTask(taskName, taskDescription, accountId);
    }
}
