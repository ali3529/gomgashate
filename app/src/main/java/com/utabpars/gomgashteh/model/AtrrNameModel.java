package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class AtrrNameModel {
    @SerializedName("response")
    private String response;
    @SerializedName("attr")
    private String attr;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
