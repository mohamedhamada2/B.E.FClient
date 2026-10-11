package com.mz.befclient.productDetails;

import android.content.Context;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsViewModel {
    Context context;
    ProductDetailsActivity productDetailsActivity;

    public ProductDetailsViewModel(Context context) {
        this.context = context;
        productDetailsActivity = (ProductDetailsActivity) context;
    }

    public void get_product_details(String product_id) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductDetailsModel> call = getDataService.get_product_details(product_id);
            call.enqueue(new Callback<ProductDetailsModel>() {
                @Override
                public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                    if (response.isSuccessful() && response.body() != null
                            && Integer.valueOf(1).equals(response.body().getSuccess())
                            && response.body().getData() != null){
                        productDetailsActivity.setProductDetails(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                    // "data" comes back as [] when the product is not found, which Gson can't parse into an object
                }
            });
        }
    }
}
