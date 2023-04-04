package com.mz.befclient.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mz.befclient.R;
import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.databinding.ActivityOrdersBinding;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    ActivityOrdersBinding activityOrdersBinding;
    OrdersViewModel ordersViewModel;
    OrderAdapter orderAdapter;
    LinearLayoutManager layoutManager,orders_status_manager;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    String user_id;
    private boolean isloading;
    private int pastvisibleitem,visibleitemcount,totalitemcount,previous_total=0;
    int view_threshold = 20;
    int page =1 ;
    Dialog dialog3;
    OrderStatusAdapter orderStatusAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        activityOrdersBinding = DataBindingUtil.setContentView(this,R.layout.activity_orders);
        ordersViewModel = new OrdersViewModel(this);
        activityOrdersBinding.setOrdersviewmodel(ordersViewModel);
        mySharedPreference = MySharedPreference.getInstance();
        userModel = mySharedPreference.Get_UserData(this);
        user_id = userModel.getData().getId();
        ordersViewModel.get_orders_status();
        ordersViewModel.getOrders(user_id,"neworder",1);
        activityOrdersBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersActivity.this, MainActivity.class);
                intent.putExtra("flag",1);
                finish();
                startActivity(intent);
            }
        });
    }

    public void init_Orders(OrderAdapter orderAdapter) {
        layoutManager = new LinearLayoutManager(this);
        activityOrdersBinding.ordersRecycler.setHasFixedSize(true);
        activityOrdersBinding.ordersRecycler.setLayoutManager(layoutManager);
        activityOrdersBinding.ordersRecycler.setAdapter(orderAdapter);
    }

    public void setBills_product(Fatora fatora) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = inflater.inflate(R.layout.details_dialog, null);
        RecyclerView products_recycler = view2.findViewById(R.id.product_recycler);
        TextView txt_client_name = view2.findViewById(R.id.txt_client_name);
        TextView txt_bill_num = view2.findViewById(R.id.txt_bill_num);
        TextView txt_total = view2.findViewById(R.id.txt_total);
        TextView txt_no_data = view2.findViewById(R.id.txt_no_data);
        txt_bill_num.setText(fatora.getRkmFatora());
        txt_client_name.setText(fatora.getClientName());
        txt_total.setText(fatora.getFatoraCostAfterDiscount());
        if (Utilities.isNetworkAvailable(this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<BillDetailsModel> call = getDataService.get_bill_details2(fatora.getId());
            call.enqueue(new Callback<BillDetailsModel>() {
                @Override
                public void onResponse(Call<BillDetailsModel> call, Response<BillDetailsModel> response) {
                    if (response.isSuccessful()){
                        if (response.body().getDetails().isEmpty()){
                            txt_no_data.setVisibility(View.VISIBLE);
                            products_recycler.setVisibility(View.GONE);
                        }else {
                            BillDetailsAdapter billDetailsAdapter = new BillDetailsAdapter(response.body().getDetails(),OrdersActivity.this);
                            products_recycler.setAdapter(billDetailsAdapter);
                            RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(OrdersActivity.this);
                            products_recycler.setLayoutManager(layoutmanager);
                            products_recycler.setHasFixedSize(true);
                            txt_no_data.setVisibility(View.GONE);
                            products_recycler.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<BillDetailsModel> call, Throwable t) {

                }
            });
        }
        //fatoraDetailList = databaseClass.getDao().getallbills();
        //billsAdapter = new BillsAdapter(fatoraDetailList,getContext(),this);

        ImageView cancel_img = view2.findViewById(R.id.cancel_img);
        builder.setView(view2);
        dialog3 = builder.create();
        dialog3.show();
        Window window = dialog3.getWindow();
        //Toast.makeText(homeActivity, loginModel.getData().getUser().getId()+"", Toast.LENGTH_SHORT).show();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        cancel_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.dismiss();
            }
        });
    }

    public void init_order_status(List<OrderStatus> orderStatusList) {
        orders_status_manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
        orders_status_manager.setReverseLayout(true);
        orderStatusAdapter = new OrderStatusAdapter(orderStatusList,this);
        activityOrdersBinding.orderStatusRecycler.setAdapter(orderStatusAdapter);
        activityOrdersBinding.orderStatusRecycler.setLayoutManager(orders_status_manager);
        activityOrdersBinding.orderStatusRecycler.setHasFixedSize(true);
    }

    public void get_order(OrderStatus orderStatus) {
        page = 1;
        ordersViewModel.getOrders(user_id,orderStatus.getEn_name(),page);
        activityOrdersBinding.ordersRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        ordersViewModel.PerformPagination(user_id,orderStatus.getEn_name(),page);
                        isloading = true;
                    }
                }
            }
        });
    }
}