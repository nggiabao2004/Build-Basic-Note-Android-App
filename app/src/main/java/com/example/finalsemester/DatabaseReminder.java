package com.example.finalsemester;

import android.content.ContentValues; // Lưu dữ liệu dưới dạng cặp key-value
import android.content.Context; // Cung cấp thông tin ngữ cảnh ứng dụng
import android.database.Cursor; // Truy vấn dữ liệu từ SQLite
import android.database.sqlite.SQLiteDatabase; // Thao tác với cơ sở dữ liệu SQLite
import android.database.sqlite.SQLiteOpenHelper; // Hỗ trợ tạo và nâng cấp cơ sở dữ liệu

// DatabaseReminder: Quản lý cơ sở dữ liệu tài khoản và công việc
public class DatabaseReminder extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_manager.db"; // Tên cơ sở dữ liệu
    private static final int DATABASE_VERSION = 2; // Phiên bản cơ sở dữ liệu

    // Tên bảng và cột tài khoản
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ACCOUNT = "account";
    public static final String COLUMN_PASSWORD = "password";

    // Tên bảng và cột công việc
    public static final String TABLE_TASK = "task";
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_DESCRIPTION = "task_description";
    public static final String COLUMN_TASK_STATUS = "task_status"; // 0: chưa hoàn thành, 1: hoàn thành
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
                COLUMN_TASK_STATUS + " INTEGER DEFAULT 0, " +
                COLUMN_ACCOUNT_ID + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_ACCOUNT_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ACCOUNT + "));";

        db.execSQL(createUserTable); // Tạo bảng user
        db.execSQL(createTaskTable); // Tạo bảng task
    }

    // Cập nhật cơ sở dữ liệu khi thay đổi phiên bản
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            String alterTaskTable = "ALTER TABLE " + TABLE_TASK +
                    " ADD COLUMN " + COLUMN_ACCOUNT_ID + " TEXT NOT NULL DEFAULT '';"; // Thêm cột liên kết
            db.execSQL(alterTaskTable);
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
        return result != -1; // Trả về true nếu thêm thành công
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
        return userExists; // Trả về true nếu thông tin tài khoản hợp lệ
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
        return result != -1; // Trả về true nếu thêm thành công
    }

    // Lấy danh sách công việc theo tài khoản
    public Cursor getTasksByAccount(String accountId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TASK,
                new String[]{COLUMN_TASK_NAME, COLUMN_TASK_DESCRIPTION, COLUMN_TASK_STATUS},
                COLUMN_ACCOUNT_ID + " = ?",
                new String[]{accountId},
                null, null, null);
    }

    // Lấy danh sách công việc hoàn thành/chưa hoàn thành
    public Cursor getTasksByStatus(String accountId, int status) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TASK,
                new String[]{COLUMN_TASK_NAME, COLUMN_TASK_DESCRIPTION},
                COLUMN_ACCOUNT_ID + " = ? AND " + COLUMN_TASK_STATUS + " = ?",
                new String[]{accountId, String.valueOf(status)},
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
    public void updateTask(String taskName, String taskDescription, int status, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_DESCRIPTION, taskDescription);
        values.put(COLUMN_TASK_STATUS, status);

        db.update(TABLE_TASK, values,
                COLUMN_TASK_NAME + " = ? AND " + COLUMN_ACCOUNT_ID + " = ?",
                new String[]{taskName, accountId});
        db.close();
    }
}
