package com.mz.befclient.signup;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupViewModel {
    Context context;
    SignupActivity signupActivity;
    List<String> governtitlelist,citytitlelist;
    List<City> cityList;
    List<Govern> governList;
    UserModel userModel;
    MySharedPreference mprefs;

    public SignupViewModel(Context context) {
        this.context = context;
        signupActivity = (SignupActivity) context;
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
                        signupActivity.setGovernsspinnerData(governtitlelist,governList);
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
                        signupActivity.setCitiesspinnerData(citytitlelist, cityList);
                    }
                }

                @Override
                public void onFailure(Call<List<City>> call, Throwable t) {

                }
            });
        }
    }

    public void signup(String name, String govern_id, String city_id, String shop, String mobile, String address, Double lat, Double lon, String password) {
        Intent intent = new Intent(signupActivity,VerificationCodeActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("govern_id",govern_id);
        intent.putExtra("city_id",city_id);
        intent.putExtra("shop",shop);
        intent.putExtra("mobile",mobile);
        intent.putExtra("address",address);
        intent.putExtra("lat",lat);
        intent.putExtra("lon",lon);
        intent.putExtra("password",password);
        intent.putExtra("flag",1);
        context.startActivity(intent);
        /*mprefs = MySharedPreference.getInstance();
        if (Utilities.isNetworkAvailable(context)){
            ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("تحميل ...");
            pd.show();
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<UserModel> call = getDataService.signup(name,govern_id,city_id,shop,mobile,address,lat+"",lon+"",password);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()==1){
                            pd.dismiss();
                            userModel = response.body();
                            mprefs.Create_Update_UserData(context,userModel);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, MainActivity.class));
                            //Animatoo.animateFade(context);
                            signupActivity.finish();
                        }else {
                            pd.dismiss();
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {

                }
            });
        }*/
    }
}
