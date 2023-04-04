package com.mz.befclient.about;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("terms_condition")
    @Expose
    private String termsCondition;
    @SerializedName("policy")
    @Expose
    private String policy;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("whatsapp")
    @Expose
    private String whatsapp;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("hoteline")
    @Expose
    private String hoteline;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("phone2")
    @Expose
    private String phone2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(String termsCondition) {
        this.termsCondition = termsCondition;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHoteline() {
        return hoteline;
    }

    public void setHoteline(String hoteline) {
        this.hoteline = hoteline;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

}
