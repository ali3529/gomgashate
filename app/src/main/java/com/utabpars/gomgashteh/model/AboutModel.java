package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

public class AboutModel {
    @SerializedName("data")
    private String about_me;

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }
}
