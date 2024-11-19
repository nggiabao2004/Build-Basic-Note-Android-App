package com.example.finalsemester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseReminder extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_manager.db";
    private static final int DATABASE_VERSION = 2;  // Tăng version khi thay đổi cấu trúc database

    // Tạo bảng tài khoản
    private static final String TABLE_USER = "user";
    private static final String COLUMN_ACCOUNT = "account";
    private static final String COLUMN_PASSWORD = "password";

    // Tạo bảng công việc
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";
    private static final String COLUMN_TASK_STATUS = "task_status";  // 0: Chưa hoàn thành, 1: Hoàn thành
    private static final String COLUMN_ACCOUNT_ID = "account_id";  // Thêm cột liên kết với tài khoản

    public DatabaseReminder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng tài khoản
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ACCOUNT + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT NOT NULL);";

        // Tạo bảng công việc (có thêm cột account_id)
        String createTaskTable = "CREATE TABLE " + TABLE_TASK + " (" +
                COLUMN_TASK_NAME + " TEXT NOT NULL, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_TASK_STATUS + " INTEGER DEFAULT 0, " +
                COLUMN_ACCOUNT_ID + " TEXT NOT NULL, " +  // Thêm cột account_id
                "FOREIGN KEY(" + COLUMN_ACCOUNT_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ACCOUNT + "));";

        db.execSQL(createUserTable);
        db.execSQL(createTaskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Thêm cột account_id nếu upgrade từ version cũ lên
            String alterTable = "ALTER TABLE " + TABLE_TASK + " ADD COLUMN " + COLUMN_ACCOUNT_ID + " TEXT NOT NULL;";
            db.execSQL(alterTable);
        }
    }

    // Thêm tài khoản mới
    public boolean addUser(String account, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT, account);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }

    // Kiểm tra tài khoản và mật khẩu
    public boolean checkUser(String account, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_ACCOUNT + " =? AND " + COLUMN_PASSWORD + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{account, password});
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return userExists;
    }
}
