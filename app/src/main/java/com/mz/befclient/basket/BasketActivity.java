package com.mz.befclient.basket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mz.befclient.R;
import com.mz.befclient.data.DatabaseClass;
import com.mz.befclient.data.MySharedPreference;
import com.mz.befclient.databinding.ActivityBasketBinding;
import com.mz.befclient.login.LoginActivity;
import com.mz.befclient.login.UserModel;
import com.mz.befclient.main.MainActivity;

import java.util.List;

public class BasketActivity extends AppCompatActivity {
    ActivityBasketBinding activityBasketBinding;
    BasketViewModel basketViewModel;
    BasketAdapter basketAdapter;
    RecyclerView.LayoutManager layoutManager;
    DatabaseClass databaseClass;
    List<FatoraDetail> fatoraDetailList;
    Double totalPrice;
    MySharedPreference mySharedPreference;
    UserModel userModel;
    String client_id,mob,notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        activityBasketBinding = DataBindingUtil.setContentView(this,R.layout.activity_basket);
        basketViewModel = new BasketViewModel(this);
        activityBasketBinding.setBasketviewmodel(basketViewModel);
        basketViewModel.get_products();
        UpdateList();
        getsharedpreferanceData();
        activityBasketBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(BasketActivity.this, MainActivity.class));
               finish();
            }
        });
    }

    private void getsharedpreferanceData() {
        mySharedPreference = MySharedPreference.getInstance();
        userModel = mySharedPreference.Get_UserData(this);
        if (userModel != null){
            client_id = userModel.getData().getId();
            mob = userModel.getData().getMob();
        }
        activityBasketBinding.btnSaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes = activityBasketBinding.etNotes.getText().toString();
                if (TextUtils.isEmpty(notes)){
                    notes = "";
                }
                if (userModel != null){
                    BasketModel basketModel = new BasketModel();
                    basketModel.setClientIdFk(client_id);
                    basketModel.setMob(mob);
                    basketModel.setTotalPrice(totalPrice+"");
                    basketModel.setByan(notes);
                    basketModel.setFatoraDetails(fatoraDetailList);
                    basketViewModel.add_order(basketModel);
                    activityBasketBinding.btnSaveOrder.setEnabled(false);
                }else {
                    Toast.makeText(BasketActivity.this, "برجاء تسجيل الدخول", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BasketActivity.this, LoginActivity.class));
                }
            }
        });


    }

    public void init_recycler(List<FatoraDetail> basketList) {
        fatoraDetailList = basketList;
        //Toast.makeText(this, fatoraDetailList.size()+"", Toast.LENGTH_SHORT).show();
        if (!fatoraDetailList.isEmpty()){
            basketAdapter = new BasketAdapter(basketList,this);
            layoutManager =  new LinearLayoutManager(this);
            activityBasketBinding.basketRecycler.setHasFixedSize(true);
            activityBasketBinding.basketRecycler.setAdapter(basketAdapter);
            activityBasketBinding.basketRecycler.setLayoutManager(layoutManager);
        }else {
            activityBasketBinding.txt.setVisibility(View.VISIBLE);
            activityBasketBinding.btnSaveOrder.setVisibility(View.GONE);
            activityBasketBinding.basketRecycler.setVisibility(View.GONE);
            activityBasketBinding.etNotes.setVisibility(View.GONE);
        }
    }

    public void UpdateList() {
        totalPrice = 0.0;
        for (int i = 0; i < fatoraDetailList.size(); i++) {
            totalPrice += Double.parseDouble(fatoraDetailList.get(i).getTotal());
        }
        Log.e("totalprice",totalPrice+"");
        activityBasketBinding.txtTotal.setText(totalPrice+"");
        //activityBasketBinding.txtTotalPrice.setText(" الاجمالي: "+totalPrice+" جنية");
    }
}