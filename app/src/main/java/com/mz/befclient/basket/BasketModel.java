package com.mz.befclient.basket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasketModel {

    @SerializedName("mob")
    @Expose
    private String mob;
    @SerializedName("client_id_fk")
    @Expose
    private String clientIdFk;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("byan")
    @Expose
    private String byan;
    @SerializedName("fatora_details")
    @Expose
    private List<FatoraDetail> fatoraDetails = null;


    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getClientIdFk() {
        return clientIdFk;
    }

    public void setClientIdFk(String clientIdFk) {
        this.clientIdFk = clientIdFk;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getByan() {
        return byan;
    }

    public void setByan(String byan) {
        this.byan = byan;
    }

    public List<FatoraDetail> getFatoraDetails() {
        return fatoraDetails;
    }

    public void setFatoraDetails(List<FatoraDetail> fatoraDetails) {
        this.fatoraDetails = fatoraDetails;
    }
}
