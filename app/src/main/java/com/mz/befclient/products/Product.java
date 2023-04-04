package com.mz.befclient.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("flow_line")
    @Expose
    private String flowLine;
    @SerializedName("frame")
    @Expose
    private String frame;
    @SerializedName("fac")
    @Expose
    private String fac;
    @SerializedName("category_id_fk")
    @Expose
    private String categoryIdFk;
    @SerializedName("all_amount")
    @Expose
    private String allAmount;
    @SerializedName("unit_id_fk")
    @Expose
    private String unitIdFk;
    @SerializedName("one_sell_price")
    @Expose
    private String oneSellPrice;
    @SerializedName("packet_sell_price")
    @Expose
    private String packetSellPrice;
    @SerializedName("max_one_sell_price")
    @Expose
    private String maxOneSellPrice;
    @SerializedName("max_packet_sell_price")
    @Expose
    private String maxPacketSellPrice;
    @SerializedName("first_rasied")
    @Expose
    private Object firstRasied;
    @SerializedName("factory_price")
    @Expose
    private String factoryPrice;
    @SerializedName("main_branch_id_fk")
    @Expose
    private String mainBranchIdFk;
    @SerializedName("sub_branch_id_fk")
    @Expose
    private String subBranchIdFk;
    @SerializedName("storage_id_fk")
    @Expose
    private String storageIdFk;
    @SerializedName("allowed_discount")
    @Expose
    private String allowedDiscount;
    @SerializedName("offer_id_fk")
    @Expose
    private String offerIdFk;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("packet_offer_price")
    @Expose
    private String packetOfferPrice;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("publisher")
    @Expose
    private Object publisher;
    @SerializedName("deleted")
    @Expose
    private String deleted;
    @SerializedName("value")
    @Expose
    private Object value;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("from_date")
    @Expose
    private Object fromDate;
    @SerializedName("to_date")
    @Expose
    private Object toDate;
    @SerializedName("from_date_ar")
    @Expose
    private Object fromDateAr;
    @SerializedName("to_date_ar")
    @Expose
    private Object toDateAr;
    @SerializedName("client_one_price")
    @Expose
    private String clientOnePrice;
    @SerializedName("client_packet_price")
    @Expose
    private String clientPacketPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFlowLine() {
        return flowLine;
    }

    public void setFlowLine(String flowLine) {
        this.flowLine = flowLine;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getFac() {
        return fac;
    }

    public void setFac(String fac) {
        this.fac = fac;
    }

    public String getCategoryIdFk() {
        return categoryIdFk;
    }

    public void setCategoryIdFk(String categoryIdFk) {
        this.categoryIdFk = categoryIdFk;
    }

    public String getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(String allAmount) {
        this.allAmount = allAmount;
    }

    public String getUnitIdFk() {
        return unitIdFk;
    }

    public void setUnitIdFk(String unitIdFk) {
        this.unitIdFk = unitIdFk;
    }

    public String getOneSellPrice() {
        return oneSellPrice;
    }

    public void setOneSellPrice(String oneSellPrice) {
        this.oneSellPrice = oneSellPrice;
    }

    public String getPacketSellPrice() {
        return packetSellPrice;
    }

    public void setPacketSellPrice(String packetSellPrice) {
        this.packetSellPrice = packetSellPrice;
    }

    public String getMaxOneSellPrice() {
        return maxOneSellPrice;
    }

    public void setMaxOneSellPrice(String maxOneSellPrice) {
        this.maxOneSellPrice = maxOneSellPrice;
    }

    public String getMaxPacketSellPrice() {
        return maxPacketSellPrice;
    }

    public void setMaxPacketSellPrice(String maxPacketSellPrice) {
        this.maxPacketSellPrice = maxPacketSellPrice;
    }

    public Object getFirstRasied() {
        return firstRasied;
    }

    public void setFirstRasied(Object firstRasied) {
        this.firstRasied = firstRasied;
    }

    public String getFactoryPrice() {
        return factoryPrice;
    }

    public void setFactoryPrice(String factoryPrice) {
        this.factoryPrice = factoryPrice;
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

    public String getAllowedDiscount() {
        return allowedDiscount;
    }

    public void setAllowedDiscount(String allowedDiscount) {
        this.allowedDiscount = allowedDiscount;
    }

    public String getOfferIdFk() {
        return offerIdFk;
    }

    public void setOfferIdFk(String offerIdFk) {
        this.offerIdFk = offerIdFk;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getPacketOfferPrice() {
        return packetOfferPrice;
    }

    public void setPacketOfferPrice(String packetOfferPrice) {
        this.packetOfferPrice = packetOfferPrice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getPublisher() {
        return publisher;
    }

    public void setPublisher(Object publisher) {
        this.publisher = publisher;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public void setFromDate(Object fromDate) {
        this.fromDate = fromDate;
    }

    public Object getToDate() {
        return toDate;
    }

    public void setToDate(Object toDate) {
        this.toDate = toDate;
    }

    public Object getFromDateAr() {
        return fromDateAr;
    }

    public void setFromDateAr(Object fromDateAr) {
        this.fromDateAr = fromDateAr;
    }

    public Object getToDateAr() {
        return toDateAr;
    }

    public void setToDateAr(Object toDateAr) {
        this.toDateAr = toDateAr;
    }

    public String getClientOnePrice() {
        return clientOnePrice;
    }

    public void setClientOnePrice(String clientOnePrice) {
        this.clientOnePrice = clientOnePrice;
    }

    public String getClientPacketPrice() {
        return clientPacketPrice;
    }

    public void setClientPacketPrice(String clientPacketPrice) {
        this.clientPacketPrice = clientPacketPrice;
    }
}
