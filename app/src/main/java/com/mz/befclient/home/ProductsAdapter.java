package com.mz.befclient.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mz.befclient.R;
import com.mz.befclient.basket.FatoraDetail;
import com.mz.befclient.contactus.ContactUsActivity;
import com.mz.befclient.data.DatabaseClass;
import com.mz.befclient.products.Product;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsHolder> {
    Context context;
    List<Product> productList;
    DatabaseClass databaseClass;
    HomeFragment homeFragment;
    List<String> buying;
    Integer count = 1;
    Double total_price,total_price_before_offer;
    public ProductsAdapter(Context context, List<Product> productList,HomeFragment homeFragment) {
        this.context = context;
        this.productList = productList;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @NotNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new ProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductsHolder holder, int position) {
        holder.setData(productList.get(position));
        holder.btn_add_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog(productList.get(position));
                /*FatoraDetail fatoraDetail = new FatoraDetail();
                fatoraDetail.setProductIdFk(productList.get(position).getId());
                fatoraDetail.setProductName(productList.get(position).getProductName());
                fatoraDetail.setSellPrice(productList.get(position).getOneSellPrice());
                if (productList.get(position).getImg()!= null){
                    fatoraDetail.setProduct_img(productList.get(position).getImg());
                }else {
                    fatoraDetail.setProduct_img("0");
                }
                fatoraDetail.setAmount("1");
                if (productList.get(position).getOfferIdFk() != null) {
                    fatoraDetail.setOfferIdFk(productList.get(position).getOfferIdFk());
                    fatoraDetail.setOfferPrice(productList.get(position).getOfferPrice());
                    fatoraDetail.setTotal(productList.get(position).getOfferPrice());
                } else {
                    fatoraDetail.setOfferIdFk("0");
                    fatoraDetail.setOfferPrice("0");
                    fatoraDetail.setTotal(productList.get(position).getOneSellPrice());
                }
                try {
                    if (databaseClass.getDao().getallproducts().isEmpty()){
                        databaseClass.getDao().AddtoBasket(fatoraDetail);
                        Toast.makeText(context, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                        homeFragment.setbasketsize(databaseClass.getDao().getallproducts().size()+"");
                    }else {
                        for (FatoraDetail fatoraDetail1:databaseClass.getDao().getallproducts()){
                            if (!fatoraDetail1.getProductIdFk().equals(productList.get(position).getId())){
                                databaseClass.getDao().AddtoBasket(fatoraDetail);
                                Toast.makeText(context, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                                homeFragment.setbasketsize(databaseClass.getDao().getallproducts().size()+"");
                            }else {
                                databaseClass.getDao().editproduct(fatoraDetail);
                                Toast.makeText(context, "تمت التعديل بنجاح", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                } catch (Exception e) {

                }*/
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog(productList.get(position));
            }
        });
        holder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createImageAlertDialog(productList.get(position));
            }
        });
    }

    private void createImageAlertDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.img_dialog, null);
        TextView out_of_stock_tv = view1.findViewById(R.id.out_of_stock_tv);
        ImageView product_img = view1.findViewById(R.id.product_img2);
        ImageView cardView = view1.findViewById(R.id.empty_img);
        if (product.getImg() != null){
            Picasso.get().load("https://b.f.e.one-click.solutions/uploads/images/thumbs/"+product.getImg()).into(product_img);
        }else {
            product_img.setImageResource(R.drawable.logo);
        }
        if (product.getBalance() == 0){
            cardView.setVisibility(View.VISIBLE);
            out_of_stock_tv.setVisibility(View.VISIBLE);
        }else {
            cardView.setVisibility(View.GONE);
            out_of_stock_tv.setVisibility(View.GONE);
        }

        builder.setView(view1);
        Dialog dialog3 = builder.create();
        dialog3.show();
        Window window = dialog3.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void createAlertDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.product_details_dialog, null);
        ImageView product_img = view.findViewById(R.id.product_img);
        TextView product_name = view.findViewById(R.id.product_name);
        TextView txt_flow_line = view.findViewById(R.id.txt_flow_line);
        TextView txt_frame = view.findViewById(R.id.txt_frame);
        TextView txt_fac = view.findViewById(R.id.txt_fac);
        TextView txt_amount = view.findViewById(R.id.txt_amount);
        TextView txt_product_code = view.findViewById(R.id.txt_product_code);
        TextView txt_packet_amount = view.findViewById(R.id.txt_packet_amount);
        RelativeLayout relative_amount = view.findViewById(R.id.relative_amount);
        ImageView add_img = view.findViewById(R.id.add_img);
        ImageView minus_img = view.findViewById(R.id.minus_img);
        TextView product_before_offer_price = view.findViewById(R.id.product_before_offer_price);
        TextView product_after_offer_price = view.findViewById(R.id.product_after_offer_price);
        Spinner spinner_type = view.findViewById(R.id.spinner_type);
        ImageView cancel_img = view.findViewById(R.id.cancel_img);
        Button btn_add_to_basket = view.findViewById(R.id.btn_add_to_basket);
        ImageView cardView = view.findViewById(R.id.empty_img);
        TextView out_of_stock_tv = view.findViewById(R.id.out_of_stock_tv);
        FrameLayout frameLayout = view.findViewById(R.id.product_img_frame);
        product_name.setText(product.getProductName());
        txt_flow_line.setText(product.getFlowLine());
        txt_frame .setText(product.getFrame());
        txt_fac .setText(product.getFac());
        txt_product_code.setText(product.getProductCode());
        if (product.getBalance() == 0){
            cardView.setVisibility(View.VISIBLE);
            out_of_stock_tv.setVisibility(View.VISIBLE);
            btn_add_to_basket.setVisibility(View.GONE);
        }else {
            cardView.setVisibility(View.GONE);
            out_of_stock_tv.setVisibility(View.GONE);
            btn_add_to_basket.setVisibility(View.VISIBLE);
        }
        if (product.getAllAmount() != null){
            txt_packet_amount.setText(product.getAllAmount());
        }else {
            relative_amount.setVisibility(View.GONE);
        }
        if (product.getImg() != null){
            Picasso.get().load("https://b.f.e.one-click.solutions/uploads/images/thumbs/"+product.getImg()).into(product_img);
        }else {
            product_img.setImageResource(R.drawable.logo);
        }
        builder.setView(view);
        Dialog dialog3 = builder.create();
        dialog3.show();
        buying = new ArrayList<>();
        buying.add("شراء بالقطعة");
        buying.add("شراء بالكرتونة");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,R.layout.spinner_item2,buying);
        spinner_type.setAdapter(arrayAdapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (buying.get(i).equals("شراء بالقطعة")) {
                    if (product.getOfferPrice() != null){
                        total_price = Double.parseDouble(product.getOfferPrice()) * count;
                        product_after_offer_price.setText(total_price + "");
                        total_price_before_offer = Double.parseDouble(product.getOneSellPrice()) * count;
                        product_before_offer_price.setText(total_price_before_offer + "");
                        //product_before_offer_price.setText(Double.parseDouble(product.getOneSellPrice()*Double.parseDouble(txt_amount.getText().toString()))+"");
                        product_after_offer_price.setText(total_price+"");
                    }else {
                        total_price = Double.parseDouble(product.getOneSellPrice()) * count;
                        product_after_offer_price.setText(total_price + "");
                        product_before_offer_price.setVisibility(View.GONE);
                    }
                }else if (buying.get(i).equals("شراء بالكرتونة")) {
                    if (product.getPacketOfferPrice() != null){
                        total_price = Double.parseDouble(product.getPacketOfferPrice()) * count;
                        product_after_offer_price.setText(total_price + "");
                        total_price_before_offer = Double.parseDouble(product.getPacketSellPrice()) * count;
                        product_before_offer_price.setText(total_price_before_offer + "");
                    }else {
                        total_price = Double.parseDouble(product.getPacketSellPrice()) * count;
                        product_after_offer_price.setText(total_price + "");
                        product_before_offer_price.setVisibility(View.GONE);
                    }
                }
                TextView textView = (TextView) view;
                textView.setTextColor(context.getResources().getColor(R.color.purple_500));
                //citytitlelist.clear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createImageAlertDialog(product);
            }
        });
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(txt_amount.getText().toString())+1;
                txt_amount.setText(count+"");
                if (spinner_type.getSelectedItemPosition()==0){
                    if (product.getOfferIdFk() != null) {
                        total_price = Double.parseDouble(product.getOfferPrice()) * count;
                        product_after_offer_price.setText(total_price + "");
                        total_price_before_offer = Double.parseDouble(product.getOneSellPrice()) * count;
                        product_before_offer_price.setText(total_price_before_offer + "");
                    }else{
                        total_price = Double.parseDouble(product.getOneSellPrice()) * count;
                        product_after_offer_price.setText(total_price + "");
                    }
                }else if (spinner_type.getSelectedItemPosition()==1){
                    if (product.getOfferIdFk() != null) {
                        total_price = Double.parseDouble(product.getPacketOfferPrice()) * count;
                        product_after_offer_price.setText(total_price + "");
                        total_price_before_offer = Double.parseDouble(product.getPacketSellPrice()) * count;
                        product_before_offer_price.setText(total_price_before_offer + "");
                    }else{
                        total_price = Double.parseDouble(product.getPacketSellPrice()) * count;
                        product_after_offer_price.setText(total_price + "");
                    }
                }
            }
        });
        minus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(txt_amount.getText().toString())==1){
                    count = 1;
                }else {
                    count = Integer.parseInt(txt_amount.getText().toString())-1;
                }
                if (spinner_type.getSelectedItemPosition()==0){
                    if (product.getOfferIdFk() != null){
                        total_price = Double.parseDouble(product.getOfferPrice())* count;
                        product_after_offer_price.setText(total_price+"");
                        total_price_before_offer = Double.parseDouble(product.getOneSellPrice())* count;
                        product_before_offer_price.setText(total_price_before_offer+"");
                    }else {
                        total_price = Double.parseDouble(product.getOneSellPrice().toString())* count;
                        product_after_offer_price.setText(total_price+"");
                        txt_amount.setText(count+"");

                    }
                }else {
                    if (product.getOfferIdFk() != null){
                        total_price = Double.parseDouble(product.getPacketOfferPrice())* count;
                        product_after_offer_price.setText(total_price+"");
                        total_price_before_offer = Double.parseDouble(product.getPacketSellPrice())* count;
                        product_before_offer_price.setText(total_price_before_offer+"");
                    }else {
                        total_price = Double.parseDouble(product.getPacketSellPrice().toString())* count;
                        product_after_offer_price.setText(total_price+"");
                        txt_amount.setText(count+"");

                    }
                }
            }
        });
        btn_add_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FatoraDetail fatoraDetail = new FatoraDetail();
                fatoraDetail.setProductIdFk(product.getId());
                fatoraDetail.setProductName(product.getProductName());
                fatoraDetail.setSellPrice(total_price/count+"");
                if (product.getImg()!= null){
                    fatoraDetail.setProduct_img(product.getImg());
                }else {
                    fatoraDetail.setProduct_img("0");
                }
                if (spinner_type.getSelectedItemPosition()==0){
                    fatoraDetail.setType("2");
                }else {
                    fatoraDetail.setType("1");
                }
                fatoraDetail.setAmount(txt_amount.getText().toString());
                if (product.getOfferIdFk() != null) {
                    fatoraDetail.setOfferIdFk(product.getOfferIdFk());
                    fatoraDetail.setOfferPrice(total_price/count+"");
                    fatoraDetail.setTotal(total_price+"");
                } else {
                    fatoraDetail.setOfferIdFk("0");
                    fatoraDetail.setOfferPrice("0");
                    fatoraDetail.setTotal(total_price+"");
                }
                try {
                    if (databaseClass.getDao().getallproducts().isEmpty()){
                        if (product.getBalance() >= Integer.parseInt(fatoraDetail.getAmount())){
                            databaseClass.getDao().AddtoBasket(fatoraDetail);
                            Toast.makeText(context, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                            homeFragment.setbasketsize(databaseClass.getDao().getallproducts().size()+"");
                            dialog3.dismiss();
                        }else {
                            Toast.makeText(context, "الكمية المطلوبة غير متوفرة", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        for (FatoraDetail fatoraDetail1:databaseClass.getDao().getallproducts()){
                            if (!fatoraDetail1.getProductIdFk().equals(product.getId())){
                                if (product.getBalance() >= Integer.parseInt(fatoraDetail.getAmount())){
                                    databaseClass.getDao().AddtoBasket(fatoraDetail);
                                    Toast.makeText(context, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                                    homeFragment.setbasketsize(databaseClass.getDao().getallproducts().size()+"");
                                    dialog3.dismiss();
                                }else {
                                    Toast.makeText(context, "الكمية المطلوبة غير متوفرة", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                if (product.getBalance() >= Integer.parseInt(fatoraDetail.getAmount())){
                                    databaseClass.getDao().editproduct(fatoraDetail);
                                    Toast.makeText(context, "تمت التعديل بنجاح", Toast.LENGTH_SHORT).show();
                                    dialog3.dismiss();
                                }else {
                                    Toast.makeText(context, "الكمية المطلوبة غير متوفرة", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {

                }
            }
        });
        //iv_gif_container.setVisibility(View.GONE);
        //fl_shadow_container.setVisibility(View.GONE);
        /*product_name.setText(product.getTitle());
        if (product.getPrice() != null){
            product_price_offer.setText(product.getPrice()+"");
        }else {
            product_price_offer.setText("أدخل السعر");
        }
        product_details.setText(product.getDetails());
        txt_store_name.setText(store_name);
        Picasso.get().load(Constants.BASE_URL +"public/uploads/images/images/"+store_img).into(image_store_img);*/
        Window window = dialog3.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        cancel_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 1;
                dialog3.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
            return productList.size();

    }

    class ProductsHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.product_name)
        TextView txt_product_name;
        @BindView(R.id.product_after_offer_price)
        TextView txt_product_after_offer_price;
        @BindView(R.id.product_before_offer_price)
        TextView txt_product_before_offer_price;
        @BindView(R.id.product_img)
        ImageView product_img;
        @BindView(R.id.btn_add_to_basket)
        Button btn_add_to_basket;
        @BindView(R.id.empty_img)
        ImageView empty_img;

        @BindView(R.id.out_of_stock_tv)
        TextView out_of_stock_tv;
        public ProductsHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Product product) {
            databaseClass =  Room.databaseBuilder(context.getApplicationContext(), DatabaseClass.class,"basket").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            txt_product_name.setText(product.getProductCode());
            if (product.getOfferIdFk()!= null){
                txt_product_after_offer_price.setText(product.getOfferPrice());
                txt_product_before_offer_price.setText(product.getOneSellPrice());
            }else {
                txt_product_after_offer_price.setText(product.getOneSellPrice());
                txt_product_before_offer_price.setVisibility(View.GONE);

            }
            if (product.getBalance() == 0){
                empty_img.setVisibility(View.VISIBLE);
                Log.e("img","success");
            }else {
                empty_img.setVisibility(View.GONE);
                Log.e("img","error");
            }

            if (product.getImg() != null){
                Picasso.get().load("https://b.f.e.one-click.solutions/uploads/images/thumbs/"+product.getImg()).into(product_img);
            }else {
                product_img.setImageResource(R.drawable.logo);
            }
        }
    }
    public void add_product(List<Product> productList1) {
        for (Product product : productList1) {
            productList.add(product);
        }
        notifyDataSetChanged();
    }
}
