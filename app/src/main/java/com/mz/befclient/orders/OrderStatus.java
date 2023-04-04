package com.mz.befclient.orders;

public class OrderStatus {
    String En_name;
    String ar_name;

    public OrderStatus(String en_name, String ar_name) {
        En_name = en_name;
        this.ar_name = ar_name;
    }

    public String getEn_name() {
        return En_name;
    }

    public void setEn_name(String en_name) {
        En_name = en_name;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }
}
