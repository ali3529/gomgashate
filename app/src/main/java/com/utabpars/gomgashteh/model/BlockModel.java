package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class BlockModel {
    @SerializedName("status")
    private String status;
    @SerializedName("massage")
    private String massage;
    @SerializedName("is_block")
    boolean isBlock;

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
