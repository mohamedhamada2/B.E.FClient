
package com.mz.befclient.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Detail {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fatora_id_fk")
    @Expose
    private String fatoraIdFk;
    @SerializedName("carorstore")
    @Expose
    private String carorstore;
    @SerializedName("representative_id_fk")
    @Expose
    private String representativeIdFk;
    @SerializedName("car_id_fk")
    @Expose
    private String carIdFk;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("product_id_fk")
    @Expose
    private String productIdFk;
    @SerializedName("main_branch_id_fk")
    @Expose
    private String mainBranchIdFk;
    @SerializedName("sub_branch_id_fk")
    @Expose
    private String subBranchIdFk;
    @SerializedName("storage_id_fk")
    @Expose
    private String storageIdFk;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("factory_price")
    @Expose
    private String factoryPrice;
    @SerializedName("sell_price")
    @Expose
    private String sellPrice;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("offer_id_fk")
    @Expose
    private String offerIdFk;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("num_pieces_in_package")
    @Expose
    private Object numPiecesInPackage;
    @SerializedName("all_pieces")
    @Expose
    private String allPieces;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("product_code")
    @Expose
    private String productCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFatoraIdFk() {
        return fatoraIdFk;
    }

    public void setFatoraIdFk(String fatoraIdFk) {
        this.fatoraIdFk = fatoraIdFk;
    }

    public String getCarorstore() {
        return carorstore;
    }

    public void setCarorstore(String carorstore) {
        this.carorstore = carorstore;
    }

    public String getRepresentativeIdFk() {
        return representativeIdFk;
    }

    public void setRepresentativeIdFk(String representativeIdFk) {
        this.representativeIdFk = representativeIdFk;
    }

    public String getCarIdFk() {
        return carIdFk;
    }

    public void setCarIdFk(String carIdFk) {
        this.carIdFk = carIdFk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductIdFk() {
        return productIdFk;
    }

    public void setProductIdFk(String productIdFk) {
        this.productIdFk = productIdFk;
    }

    public String getMainBranchIdFk() {
        return mainBranchIdFk;
    }

    public void setMainBranchIdFk(String mainBranchIdFk) {
        this.mainBranchIdFk = mainBranchIdFk;
    }

    public String getSubBranchIdFk() {
        return subBranchIdFk;
    }

    public void setSubBranchIdFk(String subBranchIdFk) {
        this.subBranchIdFk = subBranchIdFk;
    }

    public String getStorageIdFk() {
        return storageIdFk;
    }

    public void setStorageIdFk(String storageIdFk) {
        this.storageIdFk = storageIdFk;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFactoryPrice() {
        return factoryPrice;
    }

    public void setFactoryPrice(String factoryPrice) {
        this.factoryPrice = factoryPrice;
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

    public Object getNumPiecesInPackage() {
        return numPiecesInPackage;
    }

    public void setNumPiecesInPackage(Object numPiecesInPackage) {
        this.numPiecesInPackage = numPiecesInPackage;
    }

    public String getAllPieces() {
        return allPieces;
    }

    public void setAllPieces(String allPieces) {
        this.allPieces = allPieces;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

}
