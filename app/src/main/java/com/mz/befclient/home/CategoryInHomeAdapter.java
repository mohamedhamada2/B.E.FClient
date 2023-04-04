package com.mz.befclient.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mz.befclient.R;
import com.mz.befclient.products.ProductActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryInHomeAdapter extends RecyclerView.Adapter<CategoryInHomeAdapter.CategoryInHomeHolder> {
    List<Datum> categoryList;
    Context context;

    public CategoryInHomeAdapter(List<Datum> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryInHomeHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
       return new CategoryInHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryInHomeHolder holder, int position) {
        holder.setData(categoryList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("flag",1);
                intent.putExtra("category_id",categoryList.get(position).getIdSetting());
                context.startActivity(new Intent(intent));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryInHomeHolder extends RecyclerView.ViewHolder{
        ImageView category_img;
        TextView category_name;

        public CategoryInHomeHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            category_img = itemView.findViewById(R.id.category_img);
            category_name = itemView.findViewById(R.id.category_name);
        }

        public void setData(Datum category) {
            category_name.setText(category.getTitleSetting());
            if (category.getImg() != null){
                Picasso.get().load("https://b.f.e.one-click.solutions/uploads/images/thumbs/"+category.getImg()).into(category_img);
            }else {
                category_img.setImageResource(R.drawable.logo);
            }

            //category_img.setImageResource(category.getImg());
        }
    }
}
