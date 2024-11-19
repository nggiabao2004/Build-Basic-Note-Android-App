package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogout, buttonAddTask;
    private ListView listViewTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các view
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        listViewTasks = findViewById(R.id.listViewTasks);

        // Sự kiện đăng xuất
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình đăng nhập
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        // Sự kiện thêm công việc
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình thêm công việc
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
            }
        });

        // Thiết lập ListView cho danh sách công việc
        // Giả sử sử dụng một ArrayAdapter để hiển thị công việc
    }
}
