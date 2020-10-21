package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class AppVersionModel {
    @SerializedName("last_app_version")
    private String last_app_version;
    @SerializedName("is_force")
    private int is_force;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public String getLast_app_version() {
        return last_app_version;
    }

    public void setLast_app_version(String last_app_version) {
        this.last_app_version = last_app_version;
    }

    public int getIs_force() {
        return is_force;
    }

    public void setIs_force(int is_force) {
        this.is_force = is_force;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
