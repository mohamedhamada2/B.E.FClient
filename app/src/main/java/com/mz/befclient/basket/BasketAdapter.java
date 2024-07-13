package com.mz.befclient.basket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mz.befclient.R;
import com.mz.befclient.data.DatabaseClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketHolder> {
    List<FatoraDetail> basketList;
    Context context;
    DatabaseClass databaseClass;
    BasketActivity basketActivity;
    int count;

    public BasketAdapter(List<FatoraDetail> basketList, Context context) {
        databaseClass = Room.databaseBuilder(context.getApplicationContext(), DatabaseClass.class,"basket").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.basketList = basketList;
        this.context = context;
        basketActivity = (BasketActivity) context;
    }

    @NonNull
    @NotNull
    @Override
    public BasketHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.basket_item,parent,false);
        return new BasketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BasketHolder holder, int position) {
        holder.setData(basketList.get(position));
        holder.add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double total_price;
                FatoraDetail fatoraDetail = basketList.get(position);
                count = Integer.parseInt(holder.txt_amount.getText().toString())+1;
                if (basketList.get(position).getOfferIdFk().equals("0")){
                    total_price = Double.parseDouble(basketList.get(position).getSellPrice())* count;
                    holder.product_price_offer.setText(total_price+"");
                }else {
                    total_price = Double.parseDouble(basketList.get(position).getOfferPrice())* count;
                    holder.product_price_offer.setText(total_price+"");
                    holder.product_price.setText(Double.parseDouble(basketList.get(position).getSellPrice())* count+"");
                }
                fatoraDetail.setTotal(total_price+"");
                holder.txt_amount.setText(count+"");
                fatoraDetail.setAmount(count+"");
                databaseClass.getDao().editproduct(fatoraDetail);
                basketActivity.UpdateList();
            }
        });
        holder.minus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double total_price;
                FatoraDetail orderDetails = basketList.get(position);
                if (Integer.parseInt(holder.txt_amount.getText().toString())==1){
                    count = 1;
                }else {
                    count = Integer.parseInt(holder.txt_amount.getText().toString())-1;
                }
                holder.txt_amount.setText(count+"");
                if (basketList.get(position).getOfferIdFk().equals("0")){
                    total_price = Double.parseDouble(basketList.get(position).getSellPrice())* count;
                    holder.product_price_offer.setText(total_price+"");
                }else {
                    total_price = Double.parseDouble(basketList.get(position).getOfferPrice())* count;
                    holder.product_price_offer.setText(total_price+"");
                    holder.product_price.setText(Double.parseDouble(basketList.get(position).getSellPrice())* count+"");
                }
                orderDetails.setTotal(total_price+"");
                orderDetails.setAmount(count+"");
                databaseClass.getDao().editproduct(orderDetails);
                basketActivity.UpdateList();
            }
        });
        holder.remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseClass.getDao().DeleteProduct(basketList.get(position).getProductIdFk());
                basketActivity.UpdateList();
                basketActivity.finish();
                basketActivity.overridePendingTransition( 0, 0);
                basketActivity.startActivity(basketActivity.getIntent());
                basketActivity.overridePendingTransition( 0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    class BasketHolder extends RecyclerView.ViewHolder{

        TextView product_name;
        TextView product_price;

        TextView product_price_offer;

        TextView txt_amount;

        ImageView add_img;

        ImageView minus_img;

        ImageView product_img;

        ImageView remove_img;
        public BasketHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_price_offer = itemView.findViewById(R.id.product_price_offer);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            add_img = itemView.findViewById(R.id.add_img);
            minus_img = itemView.findViewById(R.id.minus_img);
            product_img = itemView.findViewById(R.id.product_img);
            remove_img   = itemView.findViewById(R.id.remove_img);

        }

        public void setData(FatoraDetail basket) {
            product_name.setText(basket.getProductName());
            txt_amount.setText(basket.getAmount());
            if (basket.getOfferIdFk().equals("0")){
                product_price.setVisibility(View.GONE);
                product_price_offer.setText(Double.parseDouble(basket.getSellPrice())*Double.parseDouble(basket.getAmount())+"");
            }else {
                product_price.setText(Double.parseDouble(basket.getSellPrice())*Double.parseDouble(basket.getAmount())+"");
                product_price_offer.setText(Double.parseDouble(basket.getOfferPrice())*Double.parseDouble(basket.getAmount())+"");
            }
            if (basket.getProduct_img().equals("0")){
                product_img.setImageResource(R.drawable.logo);
            }else {
                Picasso.get().load("https://b.f.e.one-click.solutions/uploads/images/thumbs/"+basket.getProduct_img()).into(product_img);
            }
        }
    }
}
