package com.mz.befclient.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel {
    Context context;
    LoginActivity loginActivity;
    UserModel userModel;
    MySharedPreference mprefs;
    public LoginViewModel(Context context) {
        this.context = context;
        loginActivity = (LoginActivity) context;
    }

    public void login_user(String phone, String password,String deviceId) {
        mprefs = MySharedPreference.getInstance();
        if (Utilities.isNetworkAvailable(context)){
            ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("تحميل ...");
            pd.show();
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<UserModel> call = getDataService.login(phone, password,deviceId);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()){
                        if (response.body().getSuccess() == 1){
                            pd.dismiss();
                            userModel = response.body();
                            mprefs.Create_Update_UserData(context,userModel);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, MainActivity.class));
                            //Animatoo.animateFade(context);
                            loginActivity.finish();
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
