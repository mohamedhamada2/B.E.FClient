package com.mz.befclient.editpassword;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPasswordViewModel {
    Context context;
    EditPasswordActivity editPasswordActivity;
    MySharedPreference mprefs;
    UserModel userModel;

    public EditPasswordViewModel(Context context) {
        this.context = context;
        editPasswordActivity = (EditPasswordActivity) context;
    }

    public void Edit_password(String old_password, String new_password,String user_id) {
        mprefs = MySharedPreference.getInstance();
        if (Utilities.isNetworkAvailable(context)){
            ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("تحميل ...");
            pd.show();
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<UserModel> call = getDataService.edit_password(old_password,new_password,user_id);
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
                            editPasswordActivity.finish();
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
