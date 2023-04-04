package com.mz.befclient.main;

import android.content.Context;
import android.widget.Toast;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.login.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel {
    Context context;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    String user_id;

    public MainViewModel(Context context) {
        this.context = context;
    }

    public void update_token(String firebase_token) {
        mySharedPreference = MySharedPreference.getInstance();
        userModel = mySharedPreference.Get_UserData(context);
        if (userModel != null){
            user_id = userModel.getData().getId();
            if (Utilities.isNetworkAvailable(context)){
                GetDataService getDataService= RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<Token> call = getDataService.update_user_token(user_id,firebase_token);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        //Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {

                    }
                });
            }
        }
    }
}
