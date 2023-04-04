package com.mz.befclient.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("client_code")
    @Expose
    private String clientCode;
    @SerializedName("subscription_id_fk")
    @Expose
    private String subscriptionIdFk;
    @SerializedName("city_id_fk")
    @Expose
    private String cityIdFk;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("govern_id_fk")
    @Expose
    private String governIdFk;
    @SerializedName("national_num")
    @Expose
    private String nationalNum;
    @SerializedName("mob")
    @Expose
    private String mob;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mob2")
    @Expose
    private Object mob2;
    @SerializedName("debt")
    @Expose
    private String debt;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("shop")
    @Expose
    private String shop;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("day_of_week")
    @Expose
    private Object dayOfWeek;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("publisher")
    @Expose
    private Object publisher;
    @SerializedName("deleted")
    @Expose
    private String deleted;
    @SerializedName("approved")
    @Expose
    private String approved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getSubscriptionIdFk() {
        return subscriptionIdFk;
    }

    public void setSubscriptionIdFk(String subscriptionIdFk) {
        this.subscriptionIdFk = subscriptionIdFk;
    }

    public String getCityIdFk() {
        return cityIdFk;
    }

    public void setCityIdFk(String cityIdFk) {
        this.cityIdFk = cityIdFk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGovernIdFk() {
        return governIdFk;
    }

    public void setGovernIdFk(String governIdFk) {
        this.governIdFk = governIdFk;
    }

    public String getNationalNum() {
        return nationalNum;
    }

    public void setNationalNum(String nationalNum) {
        this.nationalNum = nationalNum;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getMob2() {
        return mob2;
    }

    public void setMob2(Object mob2) {
        this.mob2 = mob2;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Object getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Object dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }
}
