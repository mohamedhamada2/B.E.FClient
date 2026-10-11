package com.mz.befclient.productDetails;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.gson.Gson;
import com.mz.befclient.R;
import com.mz.befclient.basket.BasketActivity;
import com.mz.befclient.basket.FatoraDetail;
import com.mz.befclient.data.DatabaseClass;
import com.mz.befclient.products.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_PRODUCT = "product";
    private static final String EXTRA_PRODUCT_ID = "product_id";
    private static final String IMG_URL = "https://b.f.e.one-click.solutions/uploads/images/thumbs/";

    Product product;
    ProductDetailsViewModel productDetailsViewModel;
    DatabaseClass databaseClass;
    List<String> buying;
    Integer count = 1;
    Double total_price, total_price_before_offer;

    TextView txt_title, txt_basket, txt_amount, product_before_offer_price, product_after_offer_price;
    TextView product_name, txt_product_code, txt_flow_line, txt_frame, txt_fac, txt_packet_amount, out_of_stock_tv;
    ImageView product_img, empty_img;
    RelativeLayout relative_amount;
    Button btn_add_to_basket;
    Spinner spinner_type;

    public static void start(Context context, Product product) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(EXTRA_PRODUCT, new Gson().toJson(product));
        context.startActivity(intent);
    }

    // Used when only the product id is known (e.g. from a push notification), the details come from get_product_details
    public static Intent newIntent(Context context, String productId) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        product = new Gson().fromJson(getIntent().getStringExtra(EXTRA_PRODUCT), Product.class);
        String productId = getIntent().getStringExtra(EXTRA_PRODUCT_ID);
        if (product == null && productId != null && !productId.isEmpty()) {
            product = new Product();
            product.setId(productId);
        }
        if (product == null) {
            finish();
            return;
        }
        databaseClass = Room.databaseBuilder(getApplicationContext(), DatabaseClass.class, "basket").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        productDetailsViewModel = new ProductDetailsViewModel(this);
        initHeader();
        initDetails();
        bindProduct();
        productDetailsViewModel.get_product_details(product.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (databaseClass != null) {
            txt_basket.setText(databaseClass.getDao().getallproducts().size() + "");
        }
    }

    public void setProductDetails(Product details) {
        // get_product_details may not return the client prices or the stock balance, keep them from the list item
        if (details.getBalance() == null) details.setBalance(product.getBalance());
        if (details.getClientOnePrice() == null) details.setClientOnePrice(product.getClientOnePrice());
        if (details.getClientPacketPrice() == null) details.setClientPacketPrice(product.getClientPacketPrice());
        product = details;
        bindProduct();
    }

    private void initHeader() {
        txt_title = findViewById(R.id.txt_title);
        txt_basket = findViewById(R.id.txt_basket);
        findViewById(R.id.back_img).setOnClickListener(view -> onBackPressed());
        findViewById(R.id.basket_card).setOnClickListener(view ->
                startActivity(new Intent(ProductDetailsActivity.this, BasketActivity.class)));
    }

    private void initDetails() {
        ImageView cancel_img = findViewById(R.id.cancel_img);
        FrameLayout product_img_frame = findViewById(R.id.product_img_frame);
        ImageView add_img = findViewById(R.id.add_img);
        ImageView minus_img = findViewById(R.id.minus_img);
        product_img = findViewById(R.id.product_img);
        empty_img = findViewById(R.id.empty_img);
        out_of_stock_tv = findViewById(R.id.out_of_stock_tv);
        product_name = findViewById(R.id.product_name);
        txt_product_code = findViewById(R.id.txt_product_code);
        txt_flow_line = findViewById(R.id.txt_flow_line);
        txt_frame = findViewById(R.id.txt_frame);
        txt_fac = findViewById(R.id.txt_fac);
        txt_packet_amount = findViewById(R.id.txt_packet_amount);
        relative_amount = findViewById(R.id.relative_amount);
        btn_add_to_basket = findViewById(R.id.btn_add_to_basket);
        txt_amount = findViewById(R.id.txt_amount);
        product_before_offer_price = findViewById(R.id.product_before_offer_price);
        product_after_offer_price = findViewById(R.id.product_after_offer_price);
        spinner_type = findViewById(R.id.spinner_type);

        // The screen has its own back button in the header
        cancel_img.setVisibility(View.GONE);

        buying = new ArrayList<>();
        buying.add("شراء بالقطعة");
        buying.add("شراء بالكرتونة");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item2, buying);
        spinner_type.setAdapter(arrayAdapter);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updatePrice();
                if (view != null) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.purple_500));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        product_img_frame.setOnClickListener(view -> createImageAlertDialog());
        add_img.setOnClickListener(view -> {
            count++;
            updatePrice();
        });
        minus_img.setOnClickListener(view -> {
            if (count > 1) {
                count--;
            }
            updatePrice();
        });
        btn_add_to_basket.setOnClickListener(view -> addToBasket());
    }

    private void bindProduct() {
        txt_title.setText(product.getProductName());
        product_name.setText(product.getProductName());
        txt_product_code.setText(product.getProductCode());
        txt_flow_line.setText(product.getFlowLine());
        txt_frame.setText(product.getFrame());
        txt_fac.setText(product.getFac());
        if (isOutOfStock()) {
            empty_img.setVisibility(View.VISIBLE);
            out_of_stock_tv.setVisibility(View.VISIBLE);
            btn_add_to_basket.setVisibility(View.GONE);
        } else {
            empty_img.setVisibility(View.GONE);
            out_of_stock_tv.setVisibility(View.GONE);
            btn_add_to_basket.setVisibility(View.VISIBLE);
        }
        if (product.getAllAmount() != null) {
            relative_amount.setVisibility(View.VISIBLE);
            txt_packet_amount.setText(product.getAllAmount());
        } else {
            relative_amount.setVisibility(View.GONE);
        }
        if (product.getImg() != null) {
            Picasso.get().load(IMG_URL + product.getImg()).into(product_img);
        } else {
            product_img.setImageResource(R.drawable.logo);
        }
        updatePrice();
    }

    private boolean isOutOfStock() {
        return product.getBalance() != null && product.getBalance() == 0;
    }

    private boolean isPieceSelected() {
        return spinner_type.getSelectedItemPosition() == 0;
    }

    private void updatePrice() {
        txt_amount.setText(count + "");
        String offerPrice = isPieceSelected() ? product.getOfferPrice() : product.getPacketOfferPrice();
        String clientPrice = isPieceSelected() ? product.getClientOnePrice() : product.getClientPacketPrice();
        if (clientPrice == null) {
            return;
        }
        if (offerPrice != null) {
            total_price = Double.parseDouble(offerPrice) * count;
            total_price_before_offer = Double.parseDouble(clientPrice) * count;
            product_after_offer_price.setText(total_price + "");
            product_before_offer_price.setText(total_price_before_offer + "");
            product_before_offer_price.setVisibility(View.VISIBLE);
        } else {
            total_price = Double.parseDouble(clientPrice) * count;
            product_after_offer_price.setText(total_price + "");
            product_before_offer_price.setVisibility(View.GONE);
        }
    }

    private void addToBasket() {
        if (total_price == null) {
            return;
        }
        if (product.getBalance() == null || product.getBalance() < count) {
            Toast.makeText(this, "الكمية المطلوبة غير متوفرة", Toast.LENGTH_SHORT).show();
            return;
        }
        FatoraDetail fatoraDetail = new FatoraDetail();
        fatoraDetail.setProductIdFk(product.getId());
        fatoraDetail.setProductName(product.getProductName());
        fatoraDetail.setSellPrice(total_price / count + "");
        fatoraDetail.setProduct_img(product.getImg() != null ? product.getImg() : "0");
        fatoraDetail.setType(isPieceSelected() ? "2" : "1");
        fatoraDetail.setAmount(count + "");
        fatoraDetail.setTotal(total_price + "");
        if (product.getOfferIdFk() != null) {
            fatoraDetail.setOfferIdFk(product.getOfferIdFk());
            fatoraDetail.setOfferPrice(total_price / count + "");
        } else {
            fatoraDetail.setOfferIdFk("0");
            fatoraDetail.setOfferPrice("0");
        }
        try {
            boolean alreadyInBasket = false;
            for (FatoraDetail item : databaseClass.getDao().getallproducts()) {
                if (item.getProductIdFk().equals(product.getId())) {
                    alreadyInBasket = true;
                    break;
                }
            }
            if (alreadyInBasket) {
                databaseClass.getDao().editproduct(fatoraDetail);
                Toast.makeText(this, "تمت التعديل بنجاح", Toast.LENGTH_SHORT).show();
            } else {
                databaseClass.getDao().AddtoBasket(fatoraDetail);
                Toast.makeText(this, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
            }
            txt_basket.setText(databaseClass.getDao().getallproducts().size() + "");
        } catch (Exception e) {

        }
    }

    private void createImageAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 = LayoutInflater.from(this).inflate(R.layout.img_dialog, null);
        TextView out_of_stock_tv = view1.findViewById(R.id.out_of_stock_tv);
        CircleImageView product_img = view1.findViewById(R.id.product_img2);
        ImageView empty_img = view1.findViewById(R.id.empty_img);
        if (product.getImg() != null) {
            Picasso.get().load(IMG_URL + product.getImg()).into(product_img);
        } else {
            product_img.setImageResource(R.drawable.logo);
        }
        empty_img.setVisibility(isOutOfStock() ? View.VISIBLE : View.GONE);
        out_of_stock_tv.setVisibility(isOutOfStock() ? View.VISIBLE : View.GONE);

        builder.setView(view1);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}
