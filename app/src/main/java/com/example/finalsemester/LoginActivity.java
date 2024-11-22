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
        edtAccount = findViewById(R.id.edtAccount);  // Ánh xạ EditText cho tài khoản
        edtPassword = findViewById(R.id.edtPassword);  // Ánh xạ EditText cho mật khẩu
        btnLogin = findViewById(R.id.btnLogin);  // Ánh xạ nút đăng nhập
        btnRegister = findViewById(R.id.btnRegister);  // Ánh xạ nút đăng ký

        // Khởi tạo đối tượng DatabaseReminder để truy cập cơ sở dữ liệu
        database = new DatabaseReminder(this);

        // Xử lý sự kiện khi người dùng nhấn nút "Đăng nhập"
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(); // Gọi phương thức loginUser() để thực hiện đăng nhập
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút "Đăng ký"
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình đăng ký người dùng mới
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    // Phương thức xử lý đăng nhập người dùng
    private void loginUser() {
        // Lấy tài khoản và mật khẩu từ EditText
        String account = edtAccount.getText().toString().trim();  // Lấy tài khoản và loại bỏ khoảng trắng
        String password = edtPassword.getText().toString().trim();  // Lấy mật khẩu và loại bỏ khoảng trắng

        // Kiểm tra nếu tài khoản hoặc mật khẩu trống
        if (account.isEmpty() || password.isEmpty()) {
            // Thông báo nếu tài khoản hoặc mật khẩu chưa được nhập
            Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra tài khoản và mật khẩu trong cơ sở dữ liệu
        if (database.checkUser(account, password)) {
            // Nếu đăng nhập thành công, chuyển sang HomeActivity và truyền tài khoản vào
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("account_id", account);  // Truyền tài khoản vào HomeActivity
            startActivity(intent);  // Mở HomeActivity
        } else {
            // Thông báo nếu tài khoản hoặc mật khẩu không chính xác
            Toast.makeText(this, "Thông tin không chính xác!", Toast.LENGTH_SHORT).show();
        }
    }
}
