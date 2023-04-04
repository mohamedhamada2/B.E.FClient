package com.mz.befclient.home;

import android.content.Context;

import com.mz.befclient.R;
import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.products.Product;
import com.mz.befclient.products.ProductModel;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel {
    Context context;
    HomeFragment homeFragment;
    List<Category> categoryList;
    List<Image> sliderList;
    List<Product> productList,productList2;
    ProductsAdapter productsAdapter;

    public HomeViewModel(Context context, HomeFragment homeFragment) {
        this.context = context;
        this.homeFragment = homeFragment;
    }

    public void getCategories() {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Category> call = getDataService.get_categories_home();
            call.enqueue(new Callback<Category>() {
                @Override
                public void onResponse(Call<Category> call, Response<Category> response) {
                    if (response.isSuccessful()){
                        homeFragment.init_category(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<Category> call, Throwable t) {

                }
            });
        }
        /*categoryList = new ArrayList<>();
        Category category1 = new Category(R.drawable.store_pic,"فلاتر زيت");
        Category category2 = new Category(R.drawable.air_filter,"فلاتر هواء");
        categoryList.add(category1);
        categoryList.add(category2);
        homeFragment.init_category(categoryList);*/
    }

    public void getSlider() {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ImageModel> call = getDataService.get_images();
            call.enqueue(new Callback<ImageModel>() {
                @Override
                public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                    if (response.isSuccessful()){
                        sliderList = response.body().getData().getImages();
                        homeFragment.init_slider(sliderList);
                    }
                }

                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {

                }
            });
        }
    }

    public void get_products() {
        productList = new ArrayList<>();
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.get_products_home();
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        Product product =  response.body().getData().getProducts().get(0);
                        Product product2 =  response.body().getData().getProducts().get(1);
                        productList.add(product);
                        productList.add(product2);
                        productsAdapter = new ProductsAdapter(context,productList,homeFragment);
                        homeFragment.init_products(productsAdapter);
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void PerformPagination() {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.get_products_home();
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        if (!response.body().getData().getProducts().isEmpty()){
                            productList = response.body().getData().getProducts();
                            productsAdapter.add_product(productList2);
                            homeFragment.init_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void get_offers() {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<OfferModel> call = getDataService.get_offers();
            call.enqueue(new Callback<OfferModel>() {
                @Override
                public void onResponse(Call<OfferModel> call, Response<OfferModel> response) {
                    if (response.isSuccessful()){
                        homeFragment.init_offers(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<OfferModel> call, Throwable t) {

                }
            });
        }
    }

    public void search_all_products(String word, int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.search_product(word,"",page);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        productList2 = response.body().getData().getProducts();
                        productsAdapter = new ProductsAdapter(context,productList2,homeFragment);
                        homeFragment.init_all_products(productsAdapter);
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void PerformPagination3(int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.get_products(page);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        if (!response.body().getData().getProducts().isEmpty()){
                            productList2 = response.body().getData().getProducts();
                            productsAdapter.add_product(productList2);
                            homeFragment.init_all_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }
}
