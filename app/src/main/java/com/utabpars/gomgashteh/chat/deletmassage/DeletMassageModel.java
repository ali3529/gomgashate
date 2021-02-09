package com.utabpars.gomgashteh.chat.deletmassage;

import com.google.gson.annotations.SerializedName;

public class DeletMassageModel {
    @SerializedName("status")
    private String status;
    @SerializedName("massage")
    private String massage;


    public String getResponse() {
        return status;
    }

    public void setResponse(String response) {
        this.status = response;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
