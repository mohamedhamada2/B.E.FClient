package com.mz.befclient.categories;

import android.content.Context;

import com.mz.befclient.R;
import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.home.Category;
import com.mz.befclient.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel {
    Context context;
    CategoriesFragment categoriesFragment;
    List<Category> categoryList;

    public CategoryViewModel(Context context, CategoriesFragment categoriesFragment) {
        this.context = context;
        this.categoriesFragment = categoriesFragment;
    }

    public void getCategories() {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<com.mz.befclient.categories.Category> call = getDataService.get_categories();
            call.enqueue(new Callback<com.mz.befclient.categories.Category>() {
                @Override
                public void onResponse(Call<com.mz.befclient.categories.Category> call, Response<com.mz.befclient.categories.Category> response) {
                    if (response.isSuccessful()){
                        categoriesFragment.init_category(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<com.mz.befclient.categories.Category> call, Throwable t) {

                }
            });
        }
       /* categoryList = new ArrayList<>();
        com.mz.befclient.home.Category category1 = new com.mz.befclient.home.Category(R.drawable.store_pic,"فلاتر زيت");
        com.mz.befclient.home.Category category2 = new Category(R.drawable.air_filter,"فلاتر هواء");
        categoryList.add(category1);
        categoryList.add(category2);
        categoriesFragment.init_category(categoryList);*/
    }
}
