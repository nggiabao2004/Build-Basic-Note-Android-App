package com.example.finalsemester;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseReminder extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reminder_db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseReminder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng tài khoản và công việc
        String createTableAccount = "CREATE TABLE accounts (account TEXT PRIMARY KEY, password TEXT)";
        String createTableTasks = "CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT, task_description TEXT, task_deadline TEXT)";
        db.execSQL(createTableAccount);
        db.execSQL(createTableTasks);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Thực hiện thay đổi cấu trúc cơ sở dữ liệu khi cần
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }
}
