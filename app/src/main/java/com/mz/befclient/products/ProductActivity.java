package com.mz.befclient.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.mz.befclient.R;
import com.mz.befclient.basket.BasketActivity;
import com.mz.befclient.data.DatabaseClass;
import com.mz.befclient.databinding.ActivityProductBinding;

public class ProductActivity extends AppCompatActivity {
    String category_id;
    ProductsViewModel productsViewModel;
    ActivityProductBinding activityProductBinding;
    GridLayoutManager layoutManager;
    private boolean isloading;
    private int pastvisibleitem,visibleitemcount,totalitemcount,previous_total=0;
    int view_threshold = 20;
    int page =1 ;
    String offer_id;
    Integer flag;
    DatabaseClass databaseClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        productsViewModel = new ProductsViewModel(this);
        activityProductBinding = DataBindingUtil.setContentView(this,R.layout.activity_product);
        activityProductBinding.setProductsviewmodel(productsViewModel);
        databaseClass =  Room.databaseBuilder(getApplicationContext(), DatabaseClass.class,"basket").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        activityProductBinding.txtBasket.setText(databaseClass.getDao().getallproducts().size()+"");
        layoutManager = new GridLayoutManager(this,2);
        getDataIntent();
        activityProductBinding.basketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this, BasketActivity.class));
            }
        });
        if (flag == 1){
            activityProductBinding.productsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visibleitemcount = layoutManager.getChildCount();
                    totalitemcount = layoutManager.getItemCount();
                    pastvisibleitem = layoutManager.findFirstVisibleItemPosition();
                    if(dy>0){
                        if(isloading){
                            if(totalitemcount>previous_total){
                                isloading = false;
                                previous_total = totalitemcount;
                            }
                        }
                        if(!isloading &&(totalitemcount-visibleitemcount)<= pastvisibleitem+view_threshold){
                            page++;
                            productsViewModel.PerformPagination(category_id,page);
                            isloading = true;
                        }
                    }
                }
            });
        }else if (flag == 2){
            activityProductBinding.productsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visibleitemcount = layoutManager.getChildCount();
                    totalitemcount = layoutManager.getItemCount();
                    pastvisibleitem = layoutManager.findFirstVisibleItemPosition();
                    if(dy>0){
                        if(isloading){
                            if(totalitemcount>previous_total){
                                isloading = false;
                                previous_total = totalitemcount;
                            }
                        }
                        if(!isloading &&(totalitemcount-visibleitemcount)<= pastvisibleitem+view_threshold){
                            page++;
                            productsViewModel.PerformOfferPagination(offer_id,page);
                            isloading = true;
                        }
                    }
                }
            });
        }else if (flag == 3){
            activityProductBinding.productsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visibleitemcount = layoutManager.getChildCount();
                    totalitemcount = layoutManager.getItemCount();
                    pastvisibleitem = layoutManager.findFirstVisibleItemPosition();
                    if(dy>0){
                        if(isloading){
                            if(totalitemcount>previous_total){
                                isloading = false;
                                previous_total = totalitemcount;
                            }
                        }
                        if(!isloading &&(totalitemcount-visibleitemcount)<= pastvisibleitem+view_threshold){
                            page++;
                            productsViewModel.PerformPagination3(page);
                            isloading = true;
                        }
                    }
                }
            });
        }
        activityProductBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getDataIntent() {
        flag = getIntent().getIntExtra("flag",0);
        if (flag == 1){
            category_id = getIntent().getStringExtra("category_id");
            productsViewModel.get_category_products(category_id,1);
            activityProductBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().equals("")){
                        productsViewModel.get_category_products(category_id,1);
                    }else {
                        //Toast.makeText(ProductActivity.this, "search", Toast.LENGTH_SHORT).show();
                        productsViewModel.search_category_products(charSequence.toString(),category_id,1);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else if (flag == 2){
            activityProductBinding.txtProducts.setText("منتجات العرض");
            offer_id = getIntent().getStringExtra("offer_id");
            productsViewModel.get_offer_products(offer_id,1);
            activityProductBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().equals("")){
                        productsViewModel.get_offer_products(offer_id,1);
                    }else {
                        productsViewModel.search_offer_products(charSequence.toString(),offer_id,1);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else if (flag == 3){
            productsViewModel.get_all_products(1);
            //Toast.makeText(ProductActivity.this, "search", Toast.LENGTH_SHORT).show();
            activityProductBinding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().equals("")){
                        Toast.makeText(ProductActivity.this, "search", Toast.LENGTH_SHORT).show();
                        productsViewModel.get_all_products(1);
                    }else {
                        productsViewModel.search_all_products(charSequence.toString(),1);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }

    public void init_products(ProductsAdapter productsAdapter) {
        activityProductBinding.productsRecycler.setVisibility(View.VISIBLE);
        activityProductBinding.txtNoProducts.setVisibility(View.GONE);
        activityProductBinding.productsRecycler.setHasFixedSize(true);
        activityProductBinding.productsRecycler.setAdapter(productsAdapter);
        activityProductBinding.productsRecycler.setLayoutManager(layoutManager);
    }

    public void setbasketsize(String basket_size) {
        activityProductBinding.txtBasket.setText(basket_size);
    }

    public void setTextNoProductVisible() {
        activityProductBinding.linearProducts.setVisibility(View.GONE);
        activityProductBinding.txtNoProducts.setVisibility(View.VISIBLE);
    }

    public void setTextNoProductVisiblewithSearch() {
        activityProductBinding.productsRecycler.setVisibility(View.GONE);
        activityProductBinding.txtNoProducts.setVisibility(View.VISIBLE);
    }
}