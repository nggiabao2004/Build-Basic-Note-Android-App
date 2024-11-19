package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginAccount, editTextLoginPassword;
    private Button buttonLogin, buttonRegisterAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo các view
        editTextLoginAccount = findViewById(R.id.editTextLoginAccount);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegisterAccount = findViewById(R.id.buttonRegisterAccount);

        // Xử lý sự kiện đăng nhập
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editTextLoginAccount.getText().toString();
                String password = editTextLoginPassword.getText().toString();

                // Kiểm tra thông tin đăng nhập (có thể thay thế bằng việc kiểm tra trong SQLite)
                if (account.equals("1234567890") && password.equals("123456")) {
                    // Nếu thông tin đúng, chuyển sang màn hình chính
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sự kiện chuyển sang màn hình đăng ký
        buttonRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình đăng ký tài khoản
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
