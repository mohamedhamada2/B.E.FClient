package com.mz.befclient.editpassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.mz.befclient.R;
import com.mz.befclient.contactus.ContactUsActivity;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.databinding.ActivityEditPasswordBinding;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;

public class EditPasswordActivity extends AppCompatActivity {
    ActivityEditPasswordBinding activityEditPasswordBinding;
    EditPasswordViewModel editPasswordViewModel;
    String new_password,old_password,confirm_new_password;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        activityEditPasswordBinding = DataBindingUtil.setContentView(this,R.layout.activity_edit_password);
        editPasswordViewModel = new EditPasswordViewModel(this);
        activityEditPasswordBinding.setEditviewmodel(editPasswordViewModel);
        mySharedPreference = MySharedPreference.getInstance();
        userModel = mySharedPreference.Get_UserData(this);
        user_id = userModel.getData().getId();
        activityEditPasswordBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPasswordActivity.this, MainActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                finish();
            }
        });
        activityEditPasswordBinding.btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }

    private void validation() {
        old_password = activityEditPasswordBinding.etOldPassword.getText().toString();
        new_password = activityEditPasswordBinding.etNewPassword.getText().toString();
        confirm_new_password = activityEditPasswordBinding.etConfirmPassword.getText().toString();
        if (!TextUtils.isEmpty(old_password)&&!TextUtils.isEmpty(new_password)&&new_password.equals(confirm_new_password)){
            editPasswordViewModel.Edit_password(old_password,new_password,user_id);
        }else{
            if (TextUtils.isEmpty(old_password)){
                activityEditPasswordBinding.etOldPassword.setError("ادخل كلمة المرور القديمة");
            }else{
                activityEditPasswordBinding.etOldPassword.setError(null);
            }
            if (TextUtils.isEmpty(new_password)){
                activityEditPasswordBinding.etNewPassword.setError("ادخل كلمة المرور الجديدة");
            }else{
                activityEditPasswordBinding.etNewPassword.setError(null);
            }
            if (!new_password.equals(confirm_new_password)){
                activityEditPasswordBinding.etConfirmPassword.setError("كلمة المرور الجديدة غير متطابقة");
            }else{
                activityEditPasswordBinding.etConfirmPassword.setError(null);
            }
        }
    }
}