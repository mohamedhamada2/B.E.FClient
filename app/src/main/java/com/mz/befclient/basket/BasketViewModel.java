package com.mz.befclient.basket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.data.DatabaseClass;
import com.mz.befclient.orders.OrdersActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BasketViewModel {
    Context context;
    BasketActivity basketActivity;
    List<Basket> basketList;
    DatabaseClass databaseClass;
    List<FatoraDetail> fatoraDetailList;

    public BasketViewModel(Context context) {
        this.context = context;
        basketActivity = (BasketActivity) context;
    }

    public void get_products() {
        databaseClass =  Room.databaseBuilder(context.getApplicationContext(), DatabaseClass.class,"basket").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        fatoraDetailList = databaseClass.getDao().getallproducts();

        basketActivity.init_recycler(fatoraDetailList);
    }

    public void add_order(BasketModel basketModel) {
        if (Utilities.isNetworkAvailable(context)){
            ProgressDialog pd = new ProgressDialog(basketActivity);
            pd.setMessage("تحميل ... ");
            pd.show();
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<SuccessModel> call = getDataService.save_basket(basketModel);
            call.enqueue(new Callback<SuccessModel>() {
                @Override
                public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                    pd.dismiss();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    databaseClass.getDao().deleteAllproduct();
                    context.startActivity(new Intent(basketActivity, OrdersActivity.class));
                    basketActivity.finish();

                }

                @Override
                public void onFailure(Call<SuccessModel> call, Throwable t) {

                }
            });
        }
    }
}
