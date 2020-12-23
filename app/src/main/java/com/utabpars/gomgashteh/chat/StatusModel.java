package com.utabpars.gomgashteh.chat;

import com.google.gson.annotations.SerializedName;

public class StatusModel {
    @SerializedName("status")
    private String status;

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @SerializedName("message")
    private String massage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
