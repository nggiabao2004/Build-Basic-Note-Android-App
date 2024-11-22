package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtRegisterAccount, edtRegisterPassword, edtConfirmPassword;
    private Button btnSubmitRegister, btnBackToLogin;
    private DatabaseReminder database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Sử dụng layout activity_register.xml

        // Ánh xạ các view từ XML
        edtRegisterAccount = findViewById(R.id.edtRegisterAccount);  // Ánh xạ EditText cho tài khoản
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword);  // Ánh xạ EditText cho mật khẩu
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);  // Ánh xạ EditText cho xác nhận mật khẩu
        btnSubmitRegister = findViewById(R.id.btnSubmitRegister);  // Ánh xạ nút đăng ký
        btnBackToLogin = findViewById(R.id.btnBackToLogin);  // Ánh xạ nút quay lại màn hình đăng nhập

        // Khởi tạo đối tượng DatabaseReminder để truy cập cơ sở dữ liệu
        database = new DatabaseReminder(this);

        // Xử lý sự kiện khi người dùng nhấn nút "Đăng ký"
        btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount(); // Gọi phương thức registerAccount() để thực hiện đăng ký
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút "Quay lại đăng nhập"
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình đăng nhập
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    // Phương thức đăng ký tài khoản
    private void registerAccount() {
        // Lấy thông tin tài khoản, mật khẩu và xác nhận mật khẩu từ EditText
        String account = edtRegisterAccount.getText().toString().trim();  // Lấy tài khoản và loại bỏ khoảng trắng
        String password = edtRegisterPassword.getText().toString().trim();  // Lấy mật khẩu và loại bỏ khoảng trắng
        String confirmPassword = edtConfirmPassword.getText().toString().trim();  // Lấy mật khẩu xác nhận

        // Kiểm tra xem có trường nào bị bỏ trống không
        if (account.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu phải từ 6 ký tự trở lên và có thể chứa chữ cái, chữ số, chữ hoa, chữ thường
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải đủ 6 ký tự trở lên!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp không
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra tài khoản đã tồn tại
        if (database.isAccountExists(account)) {
            Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu tài khoản vào cơ sở dữ liệu
        boolean success = database.addUser(account, password);
        if (success) {
            // Thông báo đăng ký thành công và quay lại màn hình đăng nhập
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        } else {
            // Thông báo lỗi nếu có vấn đề khi đăng ký
            Toast.makeText(this, "Lỗi khi đăng ký!", Toast.LENGTH_SHORT).show();
        }
    }
}
