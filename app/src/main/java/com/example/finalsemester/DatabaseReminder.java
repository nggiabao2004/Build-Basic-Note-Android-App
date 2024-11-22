package com.example.finalsemester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseReminder extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_manager.db"; // Tên cơ sở dữ liệu
    private static final int DATABASE_VERSION = 3;  // Cập nhật phiên bản cơ sở dữ liệu

    // Tên bảng và cột tài khoản
    public static final String TABLE_USER = "user";  // Bảng người dùng
    public static final String COLUMN_ACCOUNT = "account";  // Cột tài khoản
    public static final String COLUMN_PASSWORD = "password";  // Cột mật khẩu

    // Tên bảng và cột công việc
    public static final String TABLE_TASK = "task";  // Bảng công việc
    public static final String COLUMN_TASK_NAME = "task_name";  // Cột tên công việc
    public static final String COLUMN_TASK_DESCRIPTION = "task_description";  // Cột mô tả công việc
    public static final String COLUMN_ACCOUNT_ID = "account_id"; // Cột liên kết với tài khoản

    // Constructor
    public DatabaseReminder(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  // Khởi tạo cơ sở dữ liệu
    }

    // Tạo cơ sở dữ liệu ban đầu
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Lệnh tạo bảng người dùng (user)
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ACCOUNT + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT NOT NULL);";

        // Lệnh tạo bảng công việc (task)
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
            // Thêm các sửa đổi vào cơ sở dữ liệu ở đây nếu cần khi nâng cấp phiên bản
        }
    }

    // Thêm tài khoản mới
    public boolean addUser(String account, String password) {
        SQLiteDatabase db = this.getWritableDatabase();  // Lấy cơ sở dữ liệu để ghi
        ContentValues values = new ContentValues();  // Lưu trữ giá trị cần thêm vào bảng
        values.put(COLUMN_ACCOUNT, account);  // Thêm tài khoản
        values.put(COLUMN_PASSWORD, password);  // Thêm mật khẩu

        // Thêm tài khoản vào bảng và trả về true nếu thêm thành công
        long result = db.insert(TABLE_USER, null, values);
        db.close();  // Đóng cơ sở dữ liệu
        return result != -1;  // Trả về true nếu insert thành công
    }

    // Kiểm tra tài khoản đăng nhập
    public boolean checkUser(String account, String password) {
        SQLiteDatabase db = this.getReadableDatabase();  // Lấy cơ sở dữ liệu để đọc
        String query = "SELECT * FROM " + TABLE_USER +
                " WHERE " + COLUMN_ACCOUNT + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{account, password});
        boolean userExists = cursor.getCount() > 0;  // Kiểm tra nếu tài khoản tồn tại
        cursor.close();  // Đóng con trỏ
        db.close();  // Đóng cơ sở dữ liệu
        return userExists;  // Trả về kết quả kiểm tra
    }

    // Kiểm tra tài khoản đã tồn tại
    public boolean isAccountExists(String account) {
        SQLiteDatabase db = this.getReadableDatabase();  // Lấy cơ sở dữ liệu để đọc
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_ACCOUNT + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{account});
        boolean exists = cursor.getCount() > 0;  // Kiểm tra tài khoản có tồn tại không
        cursor.close();  // Đóng con trỏ
        db.close();  // Đóng cơ sở dữ liệu
        return exists;  // Trả về kết quả kiểm tra
    }

    // Thêm công việc mới
    public boolean addTask(String taskName, String taskDescription, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();  // Lấy cơ sở dữ liệu để ghi
        ContentValues values = new ContentValues();  // Lưu trữ giá trị công việc
        values.put(COLUMN_TASK_NAME, taskName);  // Thêm tên công việc
        values.put(COLUMN_TASK_DESCRIPTION, taskDescription);  // Thêm mô tả công việc
        values.put(COLUMN_ACCOUNT_ID, accountId);  // Liên kết với tài khoản

        // Thêm công việc vào bảng và trả về true nếu thành công
        long result = db.insert(TABLE_TASK, null, values);
        db.close();  // Đóng cơ sở dữ liệu
        return result != -1;  // Trả về true nếu insert thành công
    }

    // Lấy danh sách công việc theo tài khoản
    public Cursor getTasksByAccount(String accountId) {
        SQLiteDatabase db = this.getReadableDatabase();  // Lấy cơ sở dữ liệu để đọc
        return db.query(TABLE_TASK,
                new String[]{COLUMN_TASK_NAME, COLUMN_TASK_DESCRIPTION},
                COLUMN_ACCOUNT_ID + " = ?",  // Lọc công việc theo tài khoản
                new String[]{accountId},
                null, null, null);
    }

    // Xóa công việc
    public void deleteTask(String taskName, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();  // Lấy cơ sở dữ liệu để ghi
        db.delete(TABLE_TASK,
                COLUMN_TASK_NAME + " = ? AND " + COLUMN_ACCOUNT_ID + " = ?",
                new String[]{taskName, accountId});
        db.close();  // Đóng cơ sở dữ liệu
    }

    // Cập nhật công việc
    public void updateTask(String taskName, String taskDescription, String accountId) {
        SQLiteDatabase db = this.getWritableDatabase();  // Lấy cơ sở dữ liệu để ghi
        ContentValues values = new ContentValues();  // Lưu trữ giá trị cần cập nhật
        values.put(COLUMN_TASK_DESCRIPTION, taskDescription);  // Cập nhật mô tả công việc

        // Cập nhật công việc trong bảng
        db.update(TABLE_TASK, values,
                COLUMN_TASK_NAME + " = ? AND " + COLUMN_ACCOUNT_ID + " = ?",
                new String[]{taskName, accountId});
        db.close();  // Đóng cơ sở dữ liệu
    }
}
