package com.example.finalsemester;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailTaskActivity extends AppCompatActivity {

    private DatabaseReminder database;
    private EditText editTextDetailTaskName, editTextDetailTaskDescription;
    private Button buttonBackFromDetail, buttonDeleteTask, buttonEditTask;
    private String taskName, accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = new DatabaseReminder(this);

        editTextDetailTaskName = findViewById(R.id.editTextDetailTaskName);
        editTextDetailTaskDescription = findViewById(R.id.editTextDetailTaskDescription);

        buttonBackFromDetail = findViewById(R.id.buttonBackFromDetail);
        buttonDeleteTask = findViewById(R.id.buttonDeleteTask);
        buttonEditTask = findViewById(R.id.buttonEditTask);

        taskName = getIntent().getStringExtra("task_name");
        accountId = getIntent().getStringExtra("account_id");

        loadTaskDetails();

        buttonBackFromDetail.setOnClickListener(v -> finish());

        buttonDeleteTask.setOnClickListener(v -> {
            deleteTask();
            Toast.makeText(this, "Ghi chú đã được xóa!", Toast.LENGTH_SHORT).show();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("action", "delete");
            resultIntent.putExtra("task_name", taskName);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        buttonEditTask.setOnClickListener(v -> {
            String updatedDescription = editTextDetailTaskDescription.getText().toString();
            updateTask(updatedDescription);
            Toast.makeText(this, "Công việc đã được sửa!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadTaskDetails() {
        Cursor cursor = database.getTasksByAccount(accountId);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_NAME));
            if (name.equals(taskName)) {
                editTextDetailTaskName.setText(name);
                editTextDetailTaskDescription.setText(cursor.getString(cursor.getColumnIndex(DatabaseReminder.COLUMN_TASK_DESCRIPTION)));
                break;
            }
        }
        cursor.close();
    }

    private void deleteTask() {
        database.deleteTask(taskName, accountId);
    }

    private void updateTask(String description) {
        database.updateTask(taskName, description, accountId);
    }
}
