package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class AddImageModel {
    @SerializedName("response")
    private  String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
