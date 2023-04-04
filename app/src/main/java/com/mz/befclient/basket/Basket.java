package com.mz.befclient.basket;

public class Basket {
    String product_name,product_price,product_price_offer;

    public Basket(String product_name, String product_price, String product_price_offer) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_price_offer = product_price_offer;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_price_offer() {
        return product_price_offer;
    }

    public void setProduct_price_offer(String product_price_offer) {
        this.product_price_offer = product_price_offer;
    }
}
