package com.example.finalsemester;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// DetailTaskActivity: Quản lý sửa, xóa, hoàn thành công việc
public class DetailTaskActivity extends AppCompatActivity {

    private EditText edtTaskName, edtTaskDescription;
    private Button btnBack, btnComplete, btnDelete, btnEdit;
    private DatabaseReminder dbHelper;
    private String taskName; // Tên công việc được truyền vào

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ánh xạ các view từ XML
        edtTaskName = findViewById(R.id.editTextDetailTaskName);
        edtTaskDescription = findViewById(R.id.editTextDetailTaskDescription);
        btnBack = findViewById(R.id.buttonBackFromDetail);
        btnComplete = findViewById(R.id.buttonCompleteTask);
        btnDelete = findViewById(R.id.buttonDeleteTask);
        btnEdit = findViewById(R.id.buttonEditTask);

        dbHelper = new DatabaseReminder(this);
        taskName = getIntent().getStringExtra("task_name"); // Lấy tên công việc từ Intent

        loadTaskDetails();

        // Xử lý nút "Quay lại"
        btnBack.setOnClickListener(v -> finish());

        btnComplete.setOnClickListener(v -> {
            markTaskAsComplete(); // Đánh dấu hoàn thành công việc
            Toast.makeText(this, "Công việc đã hoàn thành!", Toast.LENGTH_SHORT).show();

            // Gửi kết quả về HomeActivity
            returnResult("complete");  // Trả kết quả về HomeActivity
        });

        btnDelete.setOnClickListener(v -> {
            deleteTask(); // Xóa công việc
            Toast.makeText(this, "Công việc đã được xóa!", Toast.LENGTH_SHORT).show();

            // Gửi kết quả về HomeActivity
            returnResult("delete");  // Trả kết quả về HomeActivity
        });


        // Xử lý nút "Sửa"
        btnEdit.setOnClickListener(v -> {
            editTask();
            Toast.makeText(this, "Công việc đã được cập nhật!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Load chi tiết công việc từ database
    private void loadTaskDetails() {
        Cursor cursor = dbHelper.getReadableDatabase().query(DatabaseReminder.TABLE_TASK,
                new String[]{DatabaseReminder.COLUMN_TASK_NAME, DatabaseReminder.COLUMN_TASK_DESCRIPTION},
                DatabaseReminder.COLUMN_TASK_NAME + " = ?",
                new String[]{taskName}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String taskDescription = cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_DESCRIPTION));
            edtTaskName.setText(taskName);
            edtTaskDescription.setText(taskDescription);
            cursor.close();
        }
    }

    // Phương thức đánh dấu công việc hoàn thành
    private void markTaskAsComplete() {
        String accountId = "current_account_id";  // Thay bằng account ID thực tế
        dbHelper.updateTask(taskName, edtTaskDescription.getText().toString(), 1, accountId);  // 1 là trạng thái hoàn thành
    }

    // Phương thức xóa công việc
    private void deleteTask() {
        String accountId = "current_account_id";  // Thay bằng account ID thực tế
        dbHelper.deleteTask(taskName, accountId);  // Xóa công việc khỏi cơ sở dữ liệu
    }

    // Sửa chi tiết công việc
    // Phương thức chỉnh sửa công việc
    private void editTask() {
        String updatedDescription = edtTaskDescription.getText().toString();

        // Cập nhật công việc trong cơ sở dữ liệu
        String accountId = "current_account_id";  // Thay bằng account ID thực tế
        dbHelper.updateTask(taskName, updatedDescription, 0, accountId);  // 0 là trạng thái chưa hoàn thành
    }

    // Trả kết quả về HomeActivity
    private void returnResult(String action) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("action", action);
        resultIntent.putExtra("task_name", taskName);
        setResult(RESULT_OK, resultIntent); // Trả kết quả
        finish();
    }
}
