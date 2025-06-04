package com.example.newsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newsapp.MainActivity;
import com.example.newsapp.R;
import com.example.newsapp.utils.SharedPrefsUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextInputLayout usernameLayout = findViewById(R.id.etRegUsername);
        TextInputLayout passwordLayout = findViewById(R.id.etRegPassword);
        etUsername = usernameLayout.getEditText();
        etPassword = passwordLayout.getEditText();
        MaterialButton btnRegister = findViewById(R.id.btnRegister);
        TextView tvBackToLogin = findViewById(R.id.tvBackToLogin);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (SharedPrefsUtils.userExists(this, username)) {
                Toast.makeText(this, "用户已存在", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPrefsUtils.registerUser(this, username, password);
            SharedPrefsUtils.saveLoginStatus(this, true, false);

            Toast.makeText(this, "注册成功，自动登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        tvBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("username", etUsername.getText().toString());
            intent.putExtra("password", etPassword.getText().toString());
            startActivity(intent);
            finish();
        });
    }
}
