package com.mz.befclient.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mz.befclient.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BillDetailsAdapter extends RecyclerView.Adapter<BillDetailsAdapter.BillDetailsHolder> {
    List<Detail> detailList;
    Context context;

    public BillDetailsAdapter(List<Detail> detailList, Context context) {
        this.detailList = detailList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public BillDetailsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bill_details_item,parent,false);
        return new BillDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BillDetailsHolder holder, int position) {
        holder.setData(detailList.get(position));
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    class BillDetailsHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_product_name)
        TextView product_name;
        @BindView(R.id.txt_product_price)
        TextView product_price;
        @BindView(R.id.txt_product_amount)
        TextView product_amount;
        @BindView(R.id.txt_offer_price)
        TextView txt_offer_price;
        @BindView(R.id.txt_total)
        TextView txt_total;
        public BillDetailsHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Detail detail) {
            product_name.setText(detail.getProductName());
            if (detail.getOfferIdFk().equals("0")){
                product_price.setText(detail.getSellPrice()+" جنيه");
            }else {
                product_price.setText(detail.getOfferPrice()+" جنيه");
            }
            if (detail.getType().equals("2")){
                product_amount.setText(detail.getAmount()+" قطعة");
            }else {
                product_amount.setText(detail.getAmount()+" كارتونة");
            }
            txt_total.setText(detail.getTotal()+" جنيه");
        }
    }
}
