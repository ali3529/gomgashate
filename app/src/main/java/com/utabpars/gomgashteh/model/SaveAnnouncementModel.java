package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveAnnouncementModel {
    @SerializedName("response")
    private String response;
    @SerializedName("message")
    private List<String> masg;
    @SerializedName("announce_id")
    private String announce_id;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<String> getMasg() {
        return masg;
    }

    public void setMasg(List<String> masg) {
        this.masg = masg;
    }

    public String getAnnounce_id() {
        return announce_id;
    }

    public void setAnnounce_id(String announce_id) {
        this.announce_id = announce_id;
    }
}
