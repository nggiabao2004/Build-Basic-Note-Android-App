package com.example.finalsemester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextAccountRegister, editTextPasswordRegister, editTextConfirmPasswordRegister;
    private Button buttonRegister, buttonBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Khởi tạo các view
        editTextAccountRegister = findViewById(R.id.editTextAccountRegister);
        editTextPasswordRegister = findViewById(R.id.editTextPasswordRegister);
        editTextConfirmPasswordRegister = findViewById(R.id.editTextConfirmPasswordRegister);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonBackToLogin = findViewById(R.id.buttonBackToLogin);

        // Xử lý sự kiện đăng ký tài khoản
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editTextAccountRegister.getText().toString();
                String password = editTextPasswordRegister.getText().toString();
                String confirmPassword = editTextConfirmPasswordRegister.getText().toString();

                if (password.equals(confirmPassword)) {
                    // Kiểm tra tài khoản đã tồn tại hay chưa (có thể sử dụng SQLite ở đây)
                    if (account.equals("1234567890")) { // Giả sử tài khoản đã tồn tại
                        Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        // Đăng ký thành công
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sự kiện quay lại màn hình đăng nhập
        buttonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
