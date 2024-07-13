package com.mz.befclient.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import com.mz.befclient.R;
import com.mz.befclient.databinding.ActivityLoginBinding;
import com.mz.befclient.forgetpassword.ForgetPasswordActivity;
import com.mz.befclient.main.MainActivity;
import com.mz.befclient.signup.SignupActivity;

public class LoginActivity extends AppCompatActivity {
    String phone,password,deviceId;
    ActivityLoginBinding activityLoginBinding;
    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginViewModel = new LoginViewModel(this);
        activityLoginBinding.setLoginviewmodel(loginViewModel);
        deviceId = getSystemDetail();
        activityLoginBinding.txtRegisterNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        activityLoginBinding.txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        activityLoginBinding.txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

    }

    @SuppressLint("HardwareIds")
    private String getSystemDetail() {
        return Settings.Secure.getString(
                getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    }

    public void Login(View view) {
        password = activityLoginBinding.etPassword.getText().toString();
        phone = activityLoginBinding.etPhone.getText().toString();

        if (!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(password)){
            loginViewModel.login_user(phone,password,deviceId);
        }else {
            if (TextUtils.isEmpty(phone)){
                activityLoginBinding.etPhone.setError("أدخل رقم الهاتف");
            }else {
                activityLoginBinding.etPhone.setError(null);
            }
            if (TextUtils.isEmpty(password)){
                activityLoginBinding.etPassword.setError("أدخل كلمة المرور");
            }else {
                activityLoginBinding.etPassword.setError(null);
            }
        }
       // startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}