package com.utabpars.gomgashteh.managerAnnouncement;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ManageModel {
    @SerializedName("response")
    private String response;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    List<Option> options=new ArrayList<>();

    @SerializedName("type")
    private String status_type;

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public class Option{
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("title")
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
