package com.mz.befclient.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.mz.befclient.R;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.login.LoginActivity;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    ImageView logo_img;
    MySharedPreference mySharedPreference;
    UserModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo_img = findViewById(R.id.logo_img);
        mySharedPreference = MySharedPreference.getInstance();
        loginModel = mySharedPreference.Get_UserData(SplashActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logo_img.animate().rotationYBy(360f);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginModel != null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 2500);
    }
}