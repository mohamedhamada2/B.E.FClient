package com.mz.befclient.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("pages_count")
    @Expose
    private Integer pagesCount;
    @SerializedName("fatora")
    @Expose
    private List<Fatora> fatora = null;

    public Integer getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(Integer pagesCount) {
        this.pagesCount = pagesCount;
    }

    public List<Fatora> getFatora() {
        return fatora;
    }

    public void setFatora(List<Fatora> fatora) {
        this.fatora = fatora;
    }
}
