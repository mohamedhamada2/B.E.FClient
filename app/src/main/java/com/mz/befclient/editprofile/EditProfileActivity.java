package com.mz.befclient.editprofile;

import androidx.annotation.Nullable;
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
import com.mz.befclient.databinding.ActivityEditProfileBinding;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;
import com.mz.befclient.orders.OrdersActivity;
import com.mz.befclient.signup.City;
import com.mz.befclient.signup.Govern;
import com.mz.befclient.signup.MapsActivity;
import com.mz.befclient.signup.SignupActivity;

import java.util.List;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding activityEditProfileBinding;
    EditProfileViewModel editProfileViewModel;
    String govern_id,city_id,user_name,user_phone,user_address,shop,user_id;
    List<Govern> governList;
    List<City> cityList;
    List<String> citytitlelist,governtitlelist;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    String lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        activityEditProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);
        editProfileViewModel = new EditProfileViewModel(this);
        activityEditProfileBinding.setEditprofileviewmodel(editProfileViewModel);
        editProfileViewModel.getgovern();
        getuserData();
        activityEditProfileBinding.spinnerGovernment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                govern_id = governList.get(i).getId();
                editProfileViewModel.getCities(govern_id);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.purple_500));
                //citytitlelist.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        activityEditProfileBinding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Toast.makeText(EditProfileActivity.this, "لا يوحد مدينة", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        activityEditProfileBinding.txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this,MapsActivity.class);
                intent.putExtra("flag",2);
                startActivityForResult(intent,1);
            }
        });
        activityEditProfileBinding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        activityEditProfileBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                intent.putExtra("flag",1);
                finish();
                startActivity(intent);
            }
        });
    }

    private void validation() {
        user_name = activityEditProfileBinding.etUserName.getText().toString();
        user_phone = activityEditProfileBinding.etUserPhone.getText().toString();
        user_address = activityEditProfileBinding.etAddress.getText().toString();
        shop = activityEditProfileBinding.etShopName.getText().toString();
        if (!TextUtils.isEmpty(user_name)&&!TextUtils.isEmpty(user_address)&&!TextUtils.isEmpty(shop)){
            //Toast.makeText(this, lat+"", Toast.LENGTH_SHORT).show();
            editProfileViewModel.edit_profile(user_id,user_name,user_phone,user_address,shop,govern_id,city_id,lat,lon);
        }else {
            if (TextUtils.isEmpty(user_name)){
                activityEditProfileBinding.etUserName.setError("أدخل إسمك");
            }else {
                activityEditProfileBinding.etUserName.setError(null);
            }
            if (TextUtils.isEmpty(user_address)){
                activityEditProfileBinding.etAddress.setError("أدخل عنوانك");
            }else {
                activityEditProfileBinding.etAddress.setError(null);
            }
            if (TextUtils.isEmpty(shop)){
                activityEditProfileBinding.etShopName.setError("أدخل إسم المتجر");
            }else {
                activityEditProfileBinding.etShopName.setError(null);
            }
        }
    }

    private void getuserData() {
        mySharedPreference = MySharedPreference.getInstance();
        userModel = mySharedPreference.Get_UserData(this);
        user_id = userModel.getData().getId();
        user_name = userModel.getData().getName();
        user_phone = userModel.getData().getMob();
        user_address = userModel.getData().getAdress();
        shop = userModel.getData().getShop();
        govern_id = userModel.getData().getGovernIdFk();
        city_id = userModel.getData().getCityIdFk();
        lat = userModel.getData().getLatitude();
        lon = userModel.getData().getLongitude();
        activityEditProfileBinding.etShopName.setText(shop);
        activityEditProfileBinding.etUserName.setText(user_name);
        activityEditProfileBinding.etUserPhone.setText(user_phone);
        activityEditProfileBinding.etAddress.setText(user_address);
    }

    public void setGovernsspinnerData(List<String> governtitlelist, List<Govern> governList) {
        this.governtitlelist = governtitlelist;
        this.governList = governList;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditProfileActivity.this,R.layout.spinner_item2,governtitlelist);
        for (int i = 0; i < governList.size(); i++) {
            if(governList.get(i).getId().equals(userModel.getData().getGovernIdFk())) {
                //Toast.makeText(this, governList.get(i).getId(), Toast.LENGTH_SHORT).show();
                activityEditProfileBinding.spinnerGovernment.setAdapter(arrayAdapter);
                activityEditProfileBinding.spinnerGovernment.setSelection(i);
                return;
            }else {
                //Toast.makeText(this, governList.get(2).getId(), Toast.LENGTH_SHORT).show();
                activityEditProfileBinding.spinnerGovernment.setAdapter(arrayAdapter);
            }
        }
    }

    public void setCitiesspinnerData(List<String> citytitlelist, List<City> cityList) {
        this.cityList = cityList;
        this.citytitlelist = citytitlelist;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditProfileActivity.this,R.layout.spinner_item2,citytitlelist);
        for (int i = 0; i < cityList.size(); i++) {
            if(cityList.get(i).getId().equals(userModel.getData().getCityIdFk())){
                activityEditProfileBinding.spinnerCity.setAdapter(arrayAdapter);
                activityEditProfileBinding.spinnerCity.setSelection(i);
                return;
            }
        }
        activityEditProfileBinding.spinnerCity.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            lat = data.getDoubleExtra("lat",0)+"";
            lon = data.getDoubleExtra("lon",0)+"";
            //Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
            //activityAddClientBinding.etAddress.setText(address);
        }
    }
}