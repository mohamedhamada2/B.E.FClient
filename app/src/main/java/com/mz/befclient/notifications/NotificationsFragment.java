package com.mz.befclient.notifications;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mz.befclient.R;
import com.mz.befclient.Utilities.Utilities;
import com.mz.befclient.api.GetDataService;
import com.mz.befclient.api.RetrofitClientInstance;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.databinding.FragmentNotificationsBinding;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.orders.BillDetailsAdapter;
import com.mz.befclient.orders.BillDetailsModel;
import com.mz.befclient.orders.OrdersActivity;

import java.util.List;


public class NotificationsFragment extends Fragment {
    FragmentNotificationsBinding fragmentNotificationsBinding;
    NotificationViewModel notificationViewModel;
    NotificationAdapter notificationAdapter;
    LinearLayoutManager layoutManager;
    private boolean isloading;
    private int pastvisibleitem,visibleitemcount,totalitemcount,previous_total=0;
    int view_threshold = 20;
    int page =1 ,basket_size;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         fragmentNotificationsBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_notifications, container, false);
         notificationViewModel = new NotificationViewModel(getContext(),this);
         fragmentNotificationsBinding.setNotificationviewmodel(notificationViewModel);
         notificationViewModel.get_notifications(1);
         View view = fragmentNotificationsBinding.getRoot();
        fragmentNotificationsBinding.notificationsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        notificationViewModel.PerformPagination(page);
                        isloading = true;
                    }
                }
            }
        });
         return view;
    }

    public void init_notifications(NotificationAdapter notificationAdapter) {
        fragmentNotificationsBinding.notificationsRecycler.setHasFixedSize(true);
        fragmentNotificationsBinding.notificationsRecycler.setAdapter(notificationAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        fragmentNotificationsBinding.notificationsRecycler.setLayoutManager(layoutManager);

    }

    public void setBills_product(String orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = inflater.inflate(R.layout.details_dialog, null);
        RecyclerView products_recycler = view2.findViewById(R.id.product_recycler);
        TextView txt_client_name = view2.findViewById(R.id.txt_client_name);
        TextView txt_bill_num = view2.findViewById(R.id.txt_bill_num);
        TextView client_name = view2.findViewById(R.id.client_name);
        TextView bill_num = view2.findViewById(R.id.bill_num);
        txt_bill_num.setVisibility(View.GONE);
        txt_client_name.setVisibility(View.GONE);
        client_name.setVisibility(View.GONE);
        bill_num.setVisibility(View.GONE);
        TextView txt_no_data = view2.findViewById(R.id.txt_no_data);
        if (Utilities.isNetworkAvailable(getActivity())) {
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<BillDetailsModel> call = getDataService.get_bill_details2(orderId);
            call.enqueue(new Callback<BillDetailsModel>() {
                @Override
                public void onResponse(Call<BillDetailsModel> call, Response<BillDetailsModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getDetails().isEmpty()) {
                            txt_no_data.setVisibility(View.VISIBLE);
                            products_recycler.setVisibility(View.GONE);
                        } else {
                            BillDetailsAdapter billDetailsAdapter = new BillDetailsAdapter(response.body().getDetails(), getContext());
                            products_recycler.setAdapter(billDetailsAdapter);
                            RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
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
        ImageView cancel_img = view2.findViewById(R.id.cancel_img);
        builder.setView(view2);
        Dialog dialog3 = builder.create();
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
}