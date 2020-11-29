package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class RmModel {

    @SerializedName("response")
    private String response;
    @SerializedName("message")
    private String massage;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
