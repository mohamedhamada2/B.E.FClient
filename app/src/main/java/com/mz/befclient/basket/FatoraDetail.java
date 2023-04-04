package com.mz.befclient.basket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(tableName = "basket",primaryKeys = {"product_id_fk"})
public class FatoraDetail {
    @NonNull
    @ColumnInfo(name = "product_id_fk")
    @SerializedName("product_id_fk")
    @Expose
    private String productIdFk;
    @NonNull
    @ColumnInfo(name = "product_name")
    @SerializedName("product_name")
    @Expose
    private String productName;
    @NonNull
    @ColumnInfo(name = "sell_price")
    @SerializedName("sell_price")
    @Expose
    private String sellPrice;
    @NonNull
    @ColumnInfo(name = "offer_price")
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @Nullable
    @ColumnInfo(name = "offer_id_fk")
    @SerializedName("offer_id_fk")
    @Expose
    private String offerIdFk;

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    @ColumnInfo(name = "amount")
    @SerializedName("amount")
    @Expose
    private String amount;
    @NonNull
    @ColumnInfo(name = "type")
    @SerializedName("type")
    @Expose
    private String type;
    @NonNull
    @ColumnInfo(name = "total")
    @SerializedName("total")
    @Expose
    private String total;

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    @ColumnInfo(name = "product_img")
    @SerializedName("product_img")
    @Expose
    private String product_img;

    public String getProductIdFk() {
        return productIdFk;
    }

    public void setProductIdFk(String productIdFk) {
        this.productIdFk = productIdFk;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferIdFk() {
        return offerIdFk;
    }

    public void setOfferIdFk(String offerIdFk) {
        this.offerIdFk = offerIdFk;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
