package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtAccount, edtPassword;
    private Button btnLogin, btnRegister;
    private DatabaseReminder database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Sử dụng layout activity_login.xml

        // Ánh xạ các view từ XML
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Khởi tạo đối tượng DatabaseReminder để truy cập cơ sở dữ liệu
        database = new DatabaseReminder(this);

        // Xử lý sự kiện khi người dùng nhấn nút "Đăng nhập"
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(); // Gọi phương thức loginUser() để đăng nhập
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút "Đăng ký"
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang activity đăng ký
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    // Phương thức đăng nhập người dùng
    private void loginUser() {
        // Lấy tài khoản và mật khẩu từ EditText
        String account = edtAccount.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Kiểm tra nếu tài khoản hoặc mật khẩu trống
        if (account.isEmpty() || password.isEmpty()) {
            // Thông báo nếu tài khoản hoặc mật khẩu chưa được nhập
            Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra tài khoản và mật khẩu với cơ sở dữ liệu
        if (database.checkUser(account, password)) {
            // Nếu đăng nhập thành công, chuyển sang HomeActivity và truyền tài khoản
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("account_id", account);  // Truyền account ID sang HomeActivity
            startActivity(intent);
        } else {
            // Thông báo nếu tài khoản hoặc mật khẩu không đúng
            Toast.makeText(this, "Thông tin không chính xác!", Toast.LENGTH_SHORT).show();
        }
    }
}
