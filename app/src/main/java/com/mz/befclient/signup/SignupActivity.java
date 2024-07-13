package com.mz.befclient.signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mz.befclient.R;
import com.mz.befclient.databinding.ActivitySignupBinding;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding activitySignupBinding;
    SignupViewModel signupViewModel;
    List<Govern> governList;
    String govern_id,city_id,name,shop,mobile,address,password;
    List<City> cityList;
    List<String> governtitlelist,citytitlelist;
    Double lat=0.0,lon=0.0;
    String deviceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        activitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
        signupViewModel = new SignupViewModel(this);
        activitySignupBinding.setSignupviewmodel(signupViewModel);
        signupViewModel.getgovern();
        deviceId = getSystemDetail();
        activitySignupBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        activitySignupBinding.spinnerGovernment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                govern_id = governList.get(i).getId();
                signupViewModel.getCities(govern_id);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.purple_500));
                //citytitlelist.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        activitySignupBinding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    city_id = cityList.get(i).getId();
                    TextView textView = (TextView) view;
                    textView.setTextColor(getResources().getColor(R.color.purple_500));
                    //citytitlelist.clear();
                }catch (Exception e){
                    TextView textView = (TextView) view;
                    textView.setVisibility(View.INVISIBLE);
                    citytitlelist.clear();
                    Toast.makeText(SignupActivity.this, "لا يوحد مدينة", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        activitySignupBinding.txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,MapsActivity.class);
                intent.putExtra("flag",1);
                startActivityForResult(intent,1);
            }
        });
        activitySignupBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
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

    private void validation() {
        name = activitySignupBinding.etName.getText().toString();
        password = activitySignupBinding.etPassword.getText().toString();
        shop = activitySignupBinding.etShop.getText().toString();
        mobile = activitySignupBinding.etPhone.getText().toString();
        address = activitySignupBinding.etAddress.getText().toString();
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(shop)&&
        !TextUtils.isEmpty(mobile)&&!TextUtils.isEmpty(address)&&lat !=0.0&&lon!= 0.0){
            Log.e("deviceId",deviceId);
            signupViewModel.signup(name,govern_id,city_id,shop,mobile,address,lat,lon,password,deviceId);
        }else{
            if (TextUtils.isEmpty(name)){
                activitySignupBinding.etName.setError("أدخل إسمك");
            }else {
                activitySignupBinding.etName.setError(null);
            }
            if (TextUtils.isEmpty(password)){
                activitySignupBinding.etPassword.setError("أدخل كلمة المرور");
            }else {
                activitySignupBinding.etPassword.setError(null);
            }
            if (TextUtils.isEmpty(shop)){
                activitySignupBinding.etShop.setError("أدخل اسم المحل");
            }else {
                activitySignupBinding.etShop.setError(null);
            }
            if (TextUtils.isEmpty(mobile)){
                activitySignupBinding.etPhone.setError("أدخل رقم الموبايل");
            }else {
                activitySignupBinding.etPhone.setError(null);
            }
            if (TextUtils.isEmpty(address)){
                activitySignupBinding.etAddress.setError("أدخل العنوان");
            }else {
                activitySignupBinding.etAddress.setError(null);
            }
            if (lat == 0.0){
                Toast.makeText(this, "حدد موقع المحل من الخريطة", Toast.LENGTH_SHORT).show();
            }
            if (lon == 0.0){
                Toast.makeText(this, "حدد موقع المحل من الخريطة", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setGovernsspinnerData(List<String> governtitlelist, List<Govern> governList) {
        this.governtitlelist = governtitlelist;
        this.governList = governList;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SignupActivity.this,R.layout.spinner_item2,governtitlelist);
        activitySignupBinding.spinnerGovernment.setAdapter(arrayAdapter);
    }

    public void setCitiesspinnerData(List<String> citytitlelist, List<City> cityList) {
        this.cityList = cityList;
        this.citytitlelist = citytitlelist;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SignupActivity.this,R.layout.spinner_item2,citytitlelist);
        activitySignupBinding.spinnerCity.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            lat = data.getDoubleExtra("lat",0);
            lon = data.getDoubleExtra("lon",0);
            //Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
            //activityAddClientBinding.etAddress.setText(address);
        }
    }
}