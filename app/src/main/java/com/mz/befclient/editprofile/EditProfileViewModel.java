package com.mz.befclient.editprofile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;
import com.mz.befclient.signup.City;
import com.mz.befclient.signup.Govern;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileViewModel {
    Context context;
    EditProfileActivity editProfileActivity;
    List<Govern> governList;
    List<City> cityList;
    List<String> governtitlelist,citytitlelist;
    UserModel userModel;
    MySharedPreference mprefs;
    public EditProfileViewModel(Context context) {
        this.context = context;
        editProfileActivity = (EditProfileActivity) context;
    }
    public void getgovern() {
        governtitlelist = new ArrayList<>();
        if(Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Govern>> call = getDataService.get_govern();
            call.enqueue(new Callback<List<Govern>>() {
                @Override
                public void onResponse(Call<List<Govern>> call, Response<List<Govern>> response) {
                    if(response.isSuccessful()){
                        governList = response.body();
                        for (Govern govern:governList){
                            governtitlelist.add(govern.getTitle());
                        }
                        editProfileActivity.setGovernsspinnerData(governtitlelist,governList);
                    }
                }

                @Override
                public void onFailure(Call<List<Govern>> call, Throwable t) {

                }
            });
        }
    }

    public void getCities(String govern_id) {
        citytitlelist = new ArrayList<>();
        if (Utilities.isNetworkAvailable(context)) {
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<City>> call = getDataService.get_city(govern_id);
            call.enqueue(new Callback<List<City>>() {
                @Override
                public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                    if (response.isSuccessful()) {
                        cityList = response.body();
                        for (City city : cityList) {
                            citytitlelist.add(city.getTitle());
                        }
                        editProfileActivity.setCitiesspinnerData(citytitlelist, cityList);
                    }
                }

                @Override
                public void onFailure(Call<List<City>> call, Throwable t) {

                }
            });
        }
    }
    public void edit_profile(String user_id, String user_name, String user_phone, String user_address, String shop, String govern_id, String city_id, String lat, String lon) {
        mprefs = MySharedPreference.getInstance();
        if (Utilities.isNetworkAvailable(context)){
            ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("تحميل ...");
            pd.show();
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<UserModel> call = getDataService.update_profile(user_name,govern_id,city_id,shop,"0"+user_phone,user_address,lat,lon,user_id);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()==1){
                            pd.dismiss();
                            userModel = response.body();
                            mprefs.Create_Update_UserData(context,userModel);
                            Toast.makeText(editProfileActivity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, MainActivity.class));
                            //Animatoo.animateFade(context);
                            editProfileActivity.finish();
                        }else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {

                }
            });
        }
    }
}
