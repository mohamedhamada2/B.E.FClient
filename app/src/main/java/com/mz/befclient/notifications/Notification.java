package com.mz.befclient.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("type_notify")
    @Expose
    private String typeNotify;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("approved")
    @Expose
    private String approved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTypeNotify() {
        return typeNotify;
    }

    public void setTypeNotify(String typeNotify) {
        this.typeNotify = typeNotify;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

}
