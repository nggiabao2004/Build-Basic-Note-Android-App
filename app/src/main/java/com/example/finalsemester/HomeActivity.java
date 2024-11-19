package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnLogout, btnAddTask;
    private ListView lvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogout = findViewById(R.id.btnLogout);
        btnAddTask = findViewById(R.id.btnAddTask);
        lvTasks = findViewById(R.id.lvTasks);

        // Sự kiện đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đăng xuất và quay lại màn hình đăng nhập
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        // Sự kiện thêm công việc
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến màn hình thêm công việc
                startActivity(new Intent(HomeActivity.this, AddTaskActivity.class));
            }
        });

        // Hiển thị danh sách công việc (sử dụng ListView, có thể cần thêm Adapter cho nó)
    }
}
