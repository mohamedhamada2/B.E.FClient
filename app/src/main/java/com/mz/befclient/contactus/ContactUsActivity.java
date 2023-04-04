package com.mz.befclient.contactus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mz.befclient.R;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.databinding.ActivityContactUsBinding;
import com.mz.befclient.login.LoginActivity;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;
import com.mz.befclient.privacyterms.PrivacyTermsActivity;
import com.mz.befclient.signup.SignupActivity;
import com.mz.befclient.splash.SplashActivity;

import java.util.List;

public class ContactUsActivity extends AppCompatActivity {
    ActivityContactUsBinding activityContactUsBinding;
    ContactUsViewModel contactUsViewModel;
    String message_type;
    List<String> message_types;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    String user_id,user_name,user_phone,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        activityContactUsBinding = DataBindingUtil.setContentView(this,R.layout.activity_contact_us);
        contactUsViewModel = new ContactUsViewModel(this);
        getSharedPreferenceData();
        contactUsViewModel.get_message_types();
        activityContactUsBinding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (message_types.get(i).equals("طلب")) {
                    message_type = "1";
                }else if (message_types.get(i).equals("اقتراح")){
                    message_type = "2";
                }else if (message_types.get(i).equals("مشكلة")){
                    message_type = "3";
                }
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.purple_500));
                //citytitlelist.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        activityContactUsBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUsActivity.this, MainActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getSharedPreferenceData() {
        mySharedPreference = MySharedPreference.getInstance();
        userModel = mySharedPreference.Get_UserData(this);
        if (userModel != null){
            user_id = userModel.getData().getId();
            user_name = userModel.getData().getName();
            user_phone = userModel.getData().getMob();
            //Toast.makeText(this, user_name, Toast.LENGTH_SHORT).show();
            activityContactUsBinding.etUserName.setText(user_name);
            activityContactUsBinding.etUserPhone.setText(user_phone);
        }else{
            Toast.makeText(this, "برجاء تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ContactUsActivity.this, LoginActivity.class));
            finish();
        }
    }

    public void setspinnerData(List<String> message_types) {
        this.message_types = message_types;

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ContactUsActivity.this,R.layout.spinner_item2,message_types);
        activityContactUsBinding.spinnerType.setAdapter(arrayAdapter);
    }

    public void contactus(View view) {
        user_name = activityContactUsBinding.etUserName.getText().toString();
        user_phone = activityContactUsBinding.etUserPhone.getText().toString();
        message = activityContactUsBinding.etMessage.getText().toString();
        if (!TextUtils.isEmpty(user_name)&&!TextUtils.isEmpty(user_phone)&&!TextUtils.isEmpty(message)){
            contactUsViewModel.contact_us(user_id,user_name,user_phone,message_type,message);
        }else {
            if (TextUtils.isEmpty(user_name)){
                activityContactUsBinding.etUserName.setError("أدخل اسمك من فضلك");
            }else {
                activityContactUsBinding.etUserName.setError(null);
            }
            if (TextUtils.isEmpty(user_phone)){
                activityContactUsBinding.etUserPhone.setError("أدخل رقم الموبايل من فضلك");
            }else {
                activityContactUsBinding.etUserPhone.setError(null);
            }
            if (TextUtils.isEmpty(message)){
                activityContactUsBinding.etUserName.setError("أدخل الرسالة التي تود إرسالها من فضلك");
            }else {
                activityContactUsBinding.etUserName.setError(null);
            }
        }
    }
}