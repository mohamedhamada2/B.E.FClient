package com.mz.befclient.about;

import android.content.Context;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutViewModel {
    Context context;
    AboutActivity aboutActivity;

    public AboutViewModel(Context context) {
        this.context = context;
        aboutActivity = (AboutActivity) context;
    }

    public void get_about() {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<AppModel> call = getDataService.get_about_app();
            call.enqueue(new Callback<AppModel>() {
                @Override
                public void onResponse(Call<AppModel> call, Response<AppModel> response) {
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()==1){
                            aboutActivity.setData(response.body().getData().getInfo());
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppModel> call, Throwable t) {

                }
            });
        }
    }
}
