package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class ChatNotificationModel {
    @SerializedName("sumTickets")
    private int NotificationNumber;

    public int getNotificationNumber() {
        return NotificationNumber;
    }

    public void setNotificationNumber(int notificationNumber) {
        NotificationNumber = notificationNumber;
    }
}
