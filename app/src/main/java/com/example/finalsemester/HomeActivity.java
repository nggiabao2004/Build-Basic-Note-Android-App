package com.example.finalsemester;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

// HomeActivity: Hiển thị danh sách công việc và điều hướng
public class HomeActivity extends AppCompatActivity {

    private Button btnLogout, btnAddTask;
    private ListView lvTasks;
    private DatabaseReminder dbHelper;
    private ArrayAdapter<String> taskAdapter;
    private List<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Sử dụng đúng layout activity_home.xml

        // Ánh xạ các view từ XML
        btnLogout = findViewById(R.id.btnLogout);
        btnAddTask = findViewById(R.id.btnAddTask);
        lvTasks = findViewById(R.id.lvTasks);

        dbHelper = new DatabaseReminder(this);
        taskList = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        lvTasks.setAdapter(taskAdapter);

        // Tải danh sách công việc từ database
        loadTasks();

        // Xử lý sự kiện nút Đăng xuất
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish(); // Đóng HomeActivity để không quay lại khi bấm back
        });

        // Xử lý sự kiện nút Thêm công việc
        btnAddTask.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AddTaskActivity.class));
        });

        // Xử lý khi click vào một công việc trong danh sách
        lvTasks.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTaskName = taskList.get(position);
            Intent intent = new Intent(HomeActivity.this, DetailTaskActivity.class);
            intent.putExtra("task_name", selectedTaskName);
            startActivity(intent);
        });
    }

    // Tải danh sách công việc từ database
    private void loadTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseReminder.TABLE_TASK,
                new String[]{DatabaseReminder.COLUMN_TASK_NAME},
                null, null, null, null, null);

        taskList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String taskName = cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_NAME));
                taskList.add(taskName);
            } while (cursor.moveToNext());
            cursor.close();
        }

        taskAdapter.notifyDataSetChanged();
        db.close();
    }
}
