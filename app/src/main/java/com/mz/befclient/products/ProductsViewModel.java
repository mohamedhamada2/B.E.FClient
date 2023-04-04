package com.mz.befclient.products;

import android.content.Context;
import android.widget.Toast;

import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductsViewModel {
    Context context;
    ProductActivity productActivity;
    ProductsAdapter productsAdapter;
    List<Product> productList;
    public ProductsViewModel(Context context) {
        this.context = context;
        productActivity = (ProductActivity) context;
    }

    public void get_category_products(String category_id, int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.get_products(category_id,page);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        productList = response.body().getData().getProducts();
                        if (productList.isEmpty()){
                            productActivity.setTextNoProductVisible();
                            //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        }else {
                            productsAdapter = new ProductsAdapter(productActivity,productList);
                            productActivity.init_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void PerformPagination(String category_id, int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.get_products(category_id,page);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        if (!response.body().getData().getProducts().isEmpty()){
                            productList = response.body().getData().getProducts();
                            productsAdapter.add_product(productList);
                            productActivity.init_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void get_offer_products(String offer_id, int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.get_products_offer(offer_id,page);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        productList = response.body().getData().getProducts();
                        if (productList.isEmpty()){
                            productActivity.setTextNoProductVisible();
                            //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        }else {
                            productsAdapter = new ProductsAdapter(productActivity,productList);
                            productActivity.init_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void PerformOfferPagination(String offer_id, int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.get_products_offer(offer_id,page);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        if (!response.body().getData().getProducts().isEmpty()){
                            productList = response.body().getData().getProducts();
                            productsAdapter.add_product(productList);
                            productActivity.init_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void get_all_products(int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.get_products(page);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        productList = response.body().getData().getProducts();
                        if (productList.isEmpty()){
                            productActivity.setTextNoProductVisible();
                            //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        }else {
                            productsAdapter = new ProductsAdapter(productActivity,productList);
                            productActivity.init_products(productsAdapter);
                        }
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
                            productList = response.body().getData().getProducts();
                            productsAdapter.add_product(productList);
                            productActivity.init_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void search_category_products(String word,String category_id, int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.search_product(word,category_id,page);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        productList = response.body().getData().getProducts();
                        if (productList.isEmpty()){
                            productActivity.setTextNoProductVisiblewithSearch();
                            //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        }else {
                            productsAdapter = new ProductsAdapter(productActivity,productList);
                            productActivity.init_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

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
                        productList = response.body().getData().getProducts();
                        if (productList.isEmpty()){
                            productActivity.setTextNoProductVisiblewithSearch();
                            //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        }else {
                            productsAdapter = new ProductsAdapter(productActivity,productList);
                            productActivity.init_products(productsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                }
            });
        }
    }

    public void search_offer_products(String word, String offer_id, int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = getDataService.search_products_offer(offer_id,page,word);
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    if (response.isSuccessful()){
                        if (response.isSuccessful()){
                            productList = response.body().getData().getProducts();
                            if (productList.isEmpty()){
                                productActivity.setTextNoProductVisiblewithSearch();
                                //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            }else {
                                productsAdapter = new ProductsAdapter(productActivity,productList);
                                productActivity.init_products(productsAdapter);
                            }
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
