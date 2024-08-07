package com.mz.befclient.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationCodeViewModel {
    Context context;
    MySharedPreference mprefs;
    UserModel userModel;
    VerificationCodeActivity verificationCodeActivity;

    public VerificationCodeViewModel(Context context) {
        this.context = context;
        verificationCodeActivity = (VerificationCodeActivity) context;
    }

    public void signup(String name, String govern_id, String city_id, String shop, String phone, String address, Double lat, Double lon, String password,String deviceId) {
        Log.e("deviceId",deviceId);
        mprefs = MySharedPreference.getInstance();
        if (Utilities.isNetworkAvailable(context)){
            ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("تحميل ...");
            pd.show();
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<UserModel> call = getDataService.signup(name,govern_id,city_id,shop,phone,address,lat+"",lon+"",password,deviceId);
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
                            verificationCodeActivity.finish();
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
        }
    }
}
