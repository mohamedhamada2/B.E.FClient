package com.mz.befclient.orders;

import android.content.Context;

import com.mz.befclient.R;
import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.home.Category;
import com.mz.befclient.products.ProductModel;
import com.mz.befclient.products.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersViewModel {
    Context context;
    OrdersActivity ordersActivity;
    List<Fatora> orderList;
    OrderAdapter orderAdapter;
    List<OrderStatus> orderStatusList;
    OrderStatusAdapter orderStatusAdapter;
    String order_status;

    public OrdersViewModel(Context context) {
        this.context = context;
        ordersActivity = (OrdersActivity) context;
    }

    public void getOrders(String user_id,String order_status,Integer page) {
        /*orderList = new ArrayList<>();
        Order order = new Order("#11249487","تم التوصيل");
        Order order2 = new Order("#11249488","مرفوض");
        orderList.add(order);
        orderList.add(order2);
        ordersActivity.init_Orders(orderList);*/
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Order> call = getDataService.get_user_orders(user_id,order_status,page);
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if (response.isSuccessful()){
                        orderList = response.body().getData().getFatora();
                        orderAdapter = new OrderAdapter(orderList,ordersActivity);
                        ordersActivity.init_Orders(orderAdapter);
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        }
    }

    public void PerformPagination(String user_id,String order_status ,int page) {
        if (Utilities.isNetworkAvailable(context)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Order> call = getDataService.get_user_orders(user_id,order_status,page);
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if (response.isSuccessful()){
                        if (!response.body().getData().getFatora().isEmpty()){
                            orderList = response.body().getData().getFatora();
                            orderAdapter.add_order(orderList);
                            ordersActivity.init_Orders(orderAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        }
    }

    public void get_orders_status() {
        orderStatusList = new ArrayList<>();
        orderStatusList.add(new OrderStatus("neworder","طلبات جديدة"));
        orderStatusList.add(new OrderStatus("inprogress","طلبات قيد التنفيذ"));
        orderStatusList.add(new OrderStatus("completed","طلبات مكتملة"));
        orderStatusList.add(new OrderStatus("Cancelled","طلبات ملغية"));
        ordersActivity.init_order_status(orderStatusList);
    }
}
