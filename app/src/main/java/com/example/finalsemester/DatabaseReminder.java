package com.example.finalsemester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseReminder extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_manager.db";
    private static final int DATABASE_VERSION = 3;  // Cập nhật phiên bản cơ sở dữ liệu

    // Tên bảng và cột tài khoản
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ACCOUNT = "account";
    public static final String COLUMN_PASSWORD = "password";

    // Tên bảng và cột công việc
    public static final String TABLE_TASK = "task";
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_DESCRIPTION = "task_description";
    public static final String COLUMN_ACCOUNT_ID = "account_id"; // Liên kết với tài khoản

    // Constructor
    public DatabaseReminder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo cơ sở dữ liệu ban đầu
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ACCOUNT + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT NOT NULL);";

        String createTaskTable = "CREATE TABLE " + TABLE_TASK + " (" +
                COLUMN_TASK_NAME + " TEXT NOT NULL, " +
                COLUMN_TASK_DESCRIPTION + " TEXT, " +
                COLUMN_ACCOUNT_ID + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_ACCOUNT_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ACCOUNT + "));";

        db.execSQL(createUserTable); // Tạo bảng user
        db.execSQL(createTaskTable); // Tạo bảng task
    }

    // Cập nhật cơ sở dữ liệu khi thay đổi phiên bản
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            // Giả sử bạn chỉ cần thêm tính năng mới hoặc thay đổi bảng mà không làm mất dữ liệu
            // Thêm các sửa đổi vào cơ sở dữ liệu ở đây nếu cần
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

    // Kiểm tra tài khoản đăng nhập
    public boolean checkUser(String account, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER +
                " WHERE " + COLUMN_ACCOUNT + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{account, password});
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return userExists;
    }

    // Kiểm tra tài khoản đã tồn tại
    public boolean isAccountExists(String account) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_ACCOUNT + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{account});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Thêm công việc mới
    public boolean addTask(String taskName, String taskDescription, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, taskName);
        values.put(COLUMN_TASK_DESCRIPTION, taskDescription);
        values.put(COLUMN_ACCOUNT_ID, accountId);

        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        return result != -1;
    }

    // Lấy danh sách công việc theo tài khoản
    public Cursor getTasksByAccount(String accountId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TASK,
                new String[]{COLUMN_TASK_NAME, COLUMN_TASK_DESCRIPTION},
                COLUMN_ACCOUNT_ID + " = ?",
                new String[]{accountId},
                null, null, null);
    }

    // Xóa công việc
    public void deleteTask(String taskName, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK,
                COLUMN_TASK_NAME + " = ? AND " + COLUMN_ACCOUNT_ID + " = ?",
                new String[]{taskName, accountId});
        db.close();
    }

    // Cập nhật công việc
    public void updateTask(String taskName, String taskDescription, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_DESCRIPTION, taskDescription);

        db.update(TABLE_TASK, values,
                COLUMN_TASK_NAME + " = ? AND " + COLUMN_ACCOUNT_ID + " = ?",
                new String[]{taskName, accountId});
        db.close();
    }
}
