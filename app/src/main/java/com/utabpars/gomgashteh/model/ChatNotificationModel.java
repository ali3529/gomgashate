package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class ChatNotificationModel {
    @SerializedName("sumTickets")
    private int NotificationNumber;
    @SerializedName("user_status")
    private String userStatus;
    @SerializedName("is_category_update_available")
    private String version_update;


    public String getVersion_update() {
        return version_update;
    }

    public void setVersion_update(String version_update) {
        this.version_update = version_update;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public int getNotificationNumber() {
        return NotificationNumber;
    }

    public void setNotificationNumber(int notificationNumber) {
        NotificationNumber = notificationNumber;
    }
}
