package com.example.finalsemester;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

// HomeActivity: Hiển thị danh sách công việc và điều hướng
public class HomeActivity extends AppCompatActivity {

    private Button btnLogout, btnAddTask;
    private ListView lvTasks;
    private DatabaseReminder dbHelper;
    private ArrayAdapter<String> taskAdapter;   // Adapter để hiển thị danh sách
    private List<String> taskList;  // Danh sách các công việc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Sử dụng đúng layout activity_home.xml

        // Ánh xạ các view từ XML
        btnLogout = findViewById(R.id.btnLogout);
        btnAddTask = findViewById(R.id.btnAddTask);
        lvTasks = findViewById(R.id.lvTasks);

        // Khởi tạo database và danh sách
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
            Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
            intent.putExtra("account_id", "current_account_id"); // Thay "current_account_id" bằng giá trị thật
            startActivity(intent);
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
    // Phương thức tải công việc theo tài khoản
    private void loadTasks() {
        DatabaseReminder dbHelper = new DatabaseReminder(this);
        String accountId = "current_account_id";  // Thay bằng account ID từ login (ví dụ từ Intent)

        Cursor cursor = dbHelper.getTasksByAccount(accountId);  // Lấy công việc của tài khoản hiện tại
        taskList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String taskName = cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_NAME));
                taskList.add(taskName);  // Thêm tên công việc vào danh sách
            } while (cursor.moveToNext());
            cursor.close();
        }
        taskAdapter.notifyDataSetChanged();  // Cập nhật ListView với danh sách công việc mới
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String action = data.getStringExtra("action");
            String taskName = data.getStringExtra("task_name");

            if ("delete".equals(action)) {
                // Xóa công việc khỏi taskList và cập nhật ListView
                taskList.remove(taskName);
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Công việc đã được xóa!", Toast.LENGTH_SHORT).show();
            } else if ("complete".equals(action)) {
                // Hoàn thành công việc (có thể cập nhật trạng thái nếu cần)
                Toast.makeText(this, "Công việc đã được đánh dấu hoàn thành!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Hàm để xóa công việc khỏi danh sách hiển thị
    private void removeTaskFromList(String taskName) {
        taskList.remove(taskName); // Xóa công việc theo tên
        taskAdapter.notifyDataSetChanged();  // Cập nhật lại danh sách hiển thị
    }
}
