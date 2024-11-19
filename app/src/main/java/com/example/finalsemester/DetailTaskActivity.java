package com.example.finalsemester;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private String taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Khởi tạo các view
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

        // Xử lý nút "Hoàn thành"
        btnComplete.setOnClickListener(v -> {
            markTaskAsComplete();
            Toast.makeText(this, "Công việc đã hoàn thành!", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Xử lý nút "Xóa"
        btnDelete.setOnClickListener(v -> {
            deleteTask();
            Toast.makeText(this, "Công việc đã được xóa!", Toast.LENGTH_SHORT).show();
            finish();
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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseReminder.TABLE_TASK,
                new String[]{DatabaseReminder.COLUMN_TASK_NAME, DatabaseReminder.COLUMN_TASK_DESCRIPTION},
                DatabaseReminder.COLUMN_TASK_NAME + " = ?",
                new String[]{taskName}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String taskDescription = cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_DESCRIPTION));
            edtTaskName.setText(taskName);
            edtTaskDescription.setText(taskDescription);
            cursor.close();
        }
        db.close();
    }

    // Đánh dấu công việc hoàn thành
    private void markTaskAsComplete() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseReminder.COLUMN_TASK_STATUS, 1); // Trạng thái hoàn thành
        db.update(DatabaseReminder.TABLE_TASK, values, DatabaseReminder.COLUMN_TASK_NAME + " = ?", new String[]{taskName});
        db.close();
    }

    // Xóa công việc
    private void deleteTask() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseReminder.TABLE_TASK, DatabaseReminder.COLUMN_TASK_NAME + " = ?", new String[]{taskName});
        db.close();
    }

    // Sửa chi tiết công việc
    private void editTask() {
        String updatedDescription = edtTaskDescription.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseReminder.COLUMN_TASK_DESCRIPTION, updatedDescription);

        db.update(DatabaseReminder.TABLE_TASK, values, DatabaseReminder.COLUMN_TASK_NAME + " = ?", new String[]{taskName});
        db.close();
    }
}
