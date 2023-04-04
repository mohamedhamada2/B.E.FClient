package com.mz.befclient.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mz.befclient.R;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.signup.SignupActivity;
import com.mz.befclient.signup.VerificationCodeActivity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    String phone;
    EditText et_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        et_phone = findViewById(R.id.et_phone);
    }

    public void forget_password(View view) {
        phone = et_phone.getText().toString();

        if (!TextUtils.isEmpty(phone)){
            check_phone(phone);
        }
    }

    private void check_phone(String phone) {
        GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<NewPassword> call = getDataService.check_phone(phone);
        call.enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess()==1){
                        Toast.makeText(ForgetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgetPasswordActivity.this, VerificationCodeActivity.class);
                        intent.putExtra("phone",phone);
                        intent.putExtra("flag",3);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ForgetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPasswordActivity.this, SignupActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {

            }
        });
    }
}