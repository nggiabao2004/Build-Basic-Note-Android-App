package com.example.finalsemester;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReminder database;
    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;
    private String accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = new DatabaseReminder(this);

        taskListView = findViewById(R.id.lvTasks);
        taskList = new ArrayList<>();
        accountId = getIntent().getStringExtra("account_id");

        loadTasks(); // Tải danh sách công việc

        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnAddTask = findViewById(R.id.btnAddTask);

        btnLogout.setOnClickListener(v -> finish());  // Đăng xuất
        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
            intent.putExtra("account_id", accountId);
            startActivityForResult(intent, 1);  // Gửi yêu cầu thêm công việc
        });

        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            Task task = taskList.get(position);
            Intent intent = new Intent(HomeActivity.this, DetailTaskActivity.class);
            intent.putExtra("task_name", task.getName());
            intent.putExtra("account_id", accountId);
            startActivityForResult(intent, 1);
        });
    }

    private void loadTasks() {
        Cursor cursor = database.getTasksByAccount(accountId);
        taskList.clear();
        while (cursor.moveToNext()) {
            String taskName = cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_NAME));
            String taskDescription = cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_DESCRIPTION));
            taskList.add(new Task(taskName, taskDescription));
        }
        cursor.close();

        taskAdapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(taskAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String action = data.getStringExtra("action");
            if ("delete".equals(action)) {
                String deletedTaskName = data.getStringExtra("task_name");
                taskList.removeIf(task -> task.getName().equals(deletedTaskName)); // Xóa công việc khỏi danh sách
                taskAdapter.notifyDataSetChanged();
            } else if ("add".equals(action)) {
                loadTasks();  // Tải lại danh sách công việc sau khi thêm
            } else if ("edit".equals(action)) {
                loadTasks();  // Tải lại danh sách công việc sau khi sửa
            }
        }
    }
}
