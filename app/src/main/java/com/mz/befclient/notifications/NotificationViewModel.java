package com.mz.befclient.notifications;

import android.content.Context;

import com.mz.befclient.R;
import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.categories.CategoriesFragment;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.home.Category;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.products.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel {
    Context context;
    NotificationsFragment notificationsFragment;
    List<Notification> notificationList;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    String user_id;
    NotificationAdapter notificationAdapter;
    public NotificationViewModel(Context context, NotificationsFragment notificationsFragment) {
        this.context = context;
        this.notificationsFragment = notificationsFragment;
    }

    public void get_notifications(Integer page) {
        mySharedPreference = MySharedPreference.getInstance();
        userModel = mySharedPreference.Get_UserData(context);
        try {
            user_id = userModel.getData().getId();
        }catch (Exception e){
            user_id = "0";
        }
        notificationList = new ArrayList<>();
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<NotificationModel> call = getDataService.get_user_notifications(user_id,page,"client");
            call.enqueue(new Callback<NotificationModel>() {
                @Override
                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                    if (response.isSuccessful()){
                        notificationList = response.body().getData().getNotification();
                        notificationAdapter = new NotificationAdapter(notificationList,context,notificationsFragment);
                        notificationsFragment.init_notifications(notificationAdapter);
                    }
                }

                @Override
                public void onFailure(Call<NotificationModel> call, Throwable t) {

                }
            });
        }

    }

    public void PerformPagination(int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<NotificationModel> call = getDataService.get_user_notifications(user_id,page,"client");
            call.enqueue(new Callback<NotificationModel>() {
                @Override
                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                    if (response.isSuccessful()){
                        if (!response.body().getData().getNotification().isEmpty()){
                            notificationList = response.body().getData().getNotification();
                            notificationAdapter.add_product(notificationList);
                            notificationsFragment.init_notifications(notificationAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<NotificationModel> call, Throwable t) {

                }
            });
        }
    }
}
