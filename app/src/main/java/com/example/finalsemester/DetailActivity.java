package com.example.finalsemester;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private EditText editTextTaskName, editTextTaskDescription, editTextTaskDeadline;
    private Button buttonEdit, buttonDelete, buttonBack, buttonComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ánh xạ các view với ID đã có sẵn trong XML
        editTextTaskName = findViewById(R.id.editTextDetailTaskName);  // Đảm bảo ID này đã có trong activity_detail.xml
        editTextTaskDescription = findViewById(R.id.editTextDetailTaskDescription);  // ID này cũng phải tồn tại trong XML
        editTextTaskDeadline = findViewById(R.id.editTextDetailTaskDeadline);  // Kiểm tra lại ID này trong XML
        buttonEdit = findViewById(R.id.buttonEditTask);  // Đảm bảo ID này trong XML
        buttonDelete = findViewById(R.id.buttonDeleteTask);  // ID trong XML
        buttonBack = findViewById(R.id.buttonBackFromDetail);  // ID này phải khớp trong XML
        buttonComplete = findViewById(R.id.buttonCompleteTask);  // ID này phải khớp trong XML

        // TODO: Nhận dữ liệu từ Intent và hiển thị lên giao diện
        // Giả sử bạn nhận dữ liệu từ MainActivity
        String taskName = getIntent().getStringExtra("taskName");
        String taskDescription = getIntent().getStringExtra("taskDescription");
        String taskDeadline = getIntent().getStringExtra("taskDeadline");

        // Gán dữ liệu vào các EditText
        editTextTaskName.setText(taskName);
        editTextTaskDescription.setText(taskDescription);
        editTextTaskDeadline.setText(taskDeadline);

        // Nút Chỉnh sửa công việc
        buttonEdit.setOnClickListener(v -> {
            // Cập nhật công việc trong cơ sở dữ liệu
            String updatedName = editTextTaskName.getText().toString();
            String updatedDescription = editTextTaskDescription.getText().toString();
            String updatedDeadline = editTextTaskDeadline.getText().toString();

            // Thực hiện cập nhật trong cơ sở dữ liệu (sử dụng SQLite hoặc phương thức khác)
            SQLiteDatabase db = new DatabaseReminder(DetailActivity.this).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("task_name", updatedName);
            values.put("task_description", updatedDescription);
            values.put("task_deadline", updatedDeadline);

            // Giả sử có trường ID cho mỗi công việc, truyền vào để cập nhật
            int taskId = getIntent().getIntExtra("taskId", -1);
            if (taskId != -1) {
                db.update("tasks", values, "id = ?", new String[]{String.valueOf(taskId)});
                Toast.makeText(DetailActivity.this, "Cập nhật công việc thành công", Toast.LENGTH_SHORT).show();
            }

            // Quay lại màn hình chính sau khi cập nhật
            startActivity(new Intent(DetailActivity.this, MainActivity.class));
            finish();
        });

        // Nút Xóa công việc
        buttonDelete.setOnClickListener(v -> {
            // Xóa công việc trong cơ sở dữ liệu
            SQLiteDatabase db = new DatabaseReminder(DetailActivity.this).getWritableDatabase();
            int taskId = getIntent().getIntExtra("taskId", -1);
            if (taskId != -1) {
                db.delete("tasks", "id = ?", new String[]{String.valueOf(taskId)});
                Toast.makeText(DetailActivity.this, "Công việc đã được xóa", Toast.LENGTH_SHORT).show();
            }

            // Quay lại màn hình chính sau khi xóa
            startActivity(new Intent(DetailActivity.this, MainActivity.class));
            finish();
        });

        // Nút Hoàn thành công việc
        buttonComplete.setOnClickListener(v -> {
            // Đánh dấu công việc là hoàn thành (có thể cập nhật trường trạng thái trong cơ sở dữ liệu)
            SQLiteDatabase db = new DatabaseReminder(DetailActivity.this).getWritableDatabase();
            int taskId = getIntent().getIntExtra("taskId", -1);
            if (taskId != -1) {
                ContentValues values = new ContentValues();
                values.put("status", "completed"); // Giả sử có trường status để lưu trạng thái công việc
                db.update("tasks", values, "id = ?", new String[]{String.valueOf(taskId)});
                Toast.makeText(DetailActivity.this, "Công việc đã hoàn thành", Toast.LENGTH_SHORT).show();
            }

            // Quay lại màn hình chính sau khi hoàn thành công việc
            startActivity(new Intent(DetailActivity.this, MainActivity.class));
            finish();
        });

        // Nút Quay lại
        buttonBack.setOnClickListener(v -> {
            // Quay lại màn hình chính mà không thay đổi gì
            startActivity(new Intent(DetailActivity.this, MainActivity.class));
            finish();
        });
    }
}
