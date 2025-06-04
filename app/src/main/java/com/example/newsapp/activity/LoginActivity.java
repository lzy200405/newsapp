package com.example.newsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newsapp.MainActivity;
import com.example.newsapp.R;
import com.example.newsapp.utils.SharedPrefsUtils;

public class LoginActivity extends AppCompatActivity {
    private EditText etUser, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 判断是否已经登录
        if (SharedPrefsUtils.isLoggedIn(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPassword);
        Button login = findViewById(R.id.btnLogin);
        Button guest = findViewById(R.id.btnGuest);
        TextView register = findViewById(R.id.tvRegister);

        // 从注册返回填充值
        String regUser = getIntent().getStringExtra("username");
        String regPass = getIntent().getStringExtra("password");
        if (regUser != null) etUser.setText(regUser);
        if (regPass != null) etPass.setText(regPass);

        login.setOnClickListener(v -> {
            String username = etUser.getText().toString();
            String password = etPass.getText().toString();

            if (SharedPrefsUtils.checkLogin(this, username, password)) {
                SharedPrefsUtils.saveLoginStatus(this, true, false);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        });

        guest.setOnClickListener(v -> {
            SharedPrefsUtils.saveLoginStatus(this, false, true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        register.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
