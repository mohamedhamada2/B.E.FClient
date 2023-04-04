package com.mz.befclient.contactus;

import android.content.Context;
import android.widget.Toast;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsViewModel {
    Context context;
    ContactUsActivity contactUsActivity;
    List<String> message_types;

    public ContactUsViewModel(Context context) {
        this.context = context;
        contactUsActivity = (ContactUsActivity) context;

    }

    public void get_message_types() {
        message_types = new ArrayList<>();
        message_types.add("طلب");
        message_types.add("اقتراح");
        message_types.add("مشكلة");
        contactUsActivity.setspinnerData(message_types);
    }

    public void contact_us(String user_id, String user_name, String user_phone, String message_type, String message) {
         if (Utilities.isNetworkAvailable(context)){
             GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
             Call<ContactusModel> call = getDataService.contact_us(user_id,user_name,user_phone,message_type,message);
             call.enqueue(new Callback<ContactusModel>() {
                 @Override
                 public void onResponse(Call<ContactusModel> call, Response<ContactusModel> response) {
                     if (response.isSuccessful()){
                         if (response.body().getSuccess()==1){
                             Toast.makeText(context, "تم إرسال رسالتك بنجاح", Toast.LENGTH_SHORT).show();
                             contactUsActivity.finish();
                         }
                     }
                 }

                 @Override
                 public void onFailure(Call<ContactusModel> call, Throwable t) {

                 }
             });
         }
    }
}
