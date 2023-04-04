package com.mz.befclient.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mz.befclient.R;
import com.mz.befclient.products.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    List<Fatora> orderList;
    Context context;
    OrdersActivity ordersActivity;

    public OrderAdapter(List<Fatora> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
        ordersActivity = (OrdersActivity) context;
    }

    @NonNull
    @NotNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderHolder holder, int position) {
        holder.setData(orderList.get(position));
        holder.txt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, fatoraList.get(position).getDafterRkmFatora(), Toast.LENGTH_SHORT).show();
                ordersActivity.setBills_product(orderList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        TextView txt_order_num,txt_order_status,txt_details,txt_total;
        public OrderHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_order_num = itemView.findViewById(R.id.txt_order_num);
            txt_order_status = itemView.findViewById(R.id.txt_order_status);
            txt_details = itemView.findViewById(R.id.txt_details);
            txt_total = itemView.findViewById(R.id.txt_total);
        }

        public void setData(Fatora order) {
            txt_total.setText(order.getFatoraCostAfterDiscount()+" جنيه");
            txt_order_num.setText("#"+order.getRkmFatora());
            if (order.getStatus().equals("neworder")){
                txt_order_status.setText("طلب جديد");
                txt_order_status.setTextColor(context.getResources().getColor(R.color.purple_500));
            }else if (order.getStatus().equals("inprogress")){
                txt_order_status.setText("قيد التوصيل");
                txt_order_status.setTextColor(context.getResources().getColor(R.color.blue));
            }else if (order.getStatus().equals("Cancelled")){
                txt_order_status.setText("مرفوض");
                txt_order_status.setTextColor(context.getResources().getColor(R.color.red));
            }else if (order.getStatus().equals("completed")){
                txt_order_status.setText("تم التوصيل");
                txt_order_status.setTextColor(context.getResources().getColor(R.color.green));
            }
        }
    }
    public void add_order(List<Fatora> orderList1) {
        for (Fatora product : orderList1) {
            orderList.add(product);
        }
        notifyDataSetChanged();
    }
}
