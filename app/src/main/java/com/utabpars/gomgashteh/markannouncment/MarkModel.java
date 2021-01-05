package com.utabpars.gomgashteh.markannouncment;

import com.google.gson.annotations.SerializedName;

public class MarkModel {
    @SerializedName("response")
    private String response;
    @SerializedName("message")
    private String message;
    @SerializedName("announce_id")
    private String announce_id;
    @SerializedName("is_mark")
    private boolean isMark;

    public boolean isMark() {
        return isMark;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAnnounce_id() {
        return announce_id;
    }

    public void setAnnounce_id(String announce_id) {
        this.announce_id = announce_id;
    }
}
