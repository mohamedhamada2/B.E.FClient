package com.mz.befclient.notifications;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mz.befclient.R;
import com.mz.befclient.products.Product;
import com.mz.befclient.products.ProductActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    List<Notification> notificationList;
    Context context;
    NotificationsFragment notificationsFragment;

    public NotificationAdapter(List<Notification> notificationList, Context context,NotificationsFragment notificationsFragment) {
        this.notificationList = notificationList;
        this.context = context;
        this.notificationsFragment = notificationsFragment;
    }

    @NonNull
    @NotNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationHolder holder, int position) {
        holder.setData(notificationList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationList.get(position).getTypeNotify().equals("offer")){
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("offer_id",notificationList.get(position).getOrderId());
                    intent.putExtra("flag",2);
                    context.startActivity(intent);
                }else if (notificationList.get(position).getTypeNotify().equals("order")){
                    notificationsFragment.setBills_product(notificationList.get(position).getOrderId());
                }else {
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("offer_id",notificationList.get(position).getOrderId());
                    intent.putExtra("flag",2);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationHolder extends RecyclerView.ViewHolder{
        TextView txt_title,txt_description;
        public NotificationHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_description = itemView.findViewById(R.id.txt_description);
        }

        public void setData(Notification notification) {
            if(notification.getTitle()!= null){
                txt_title.setText(notification.getTitle());
            }else {
                txt_title.setText("عرض جديد");
            }
            txt_description.setText(notification.getNotification());
        }
    }
    public void add_product(List<Notification> notificationList1) {
        for (Notification notification : notificationList1) {
            notificationList.add(notification);
        }
        notifyDataSetChanged();
    }
}
