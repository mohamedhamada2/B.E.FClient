package com.mz.befclient.productDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mz.befclient.products.Product;

public class ProductDetailsModel {
    @SerializedName("data")
    @Expose
    private Product data;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
