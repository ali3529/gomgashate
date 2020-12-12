package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RmModel {

    @SerializedName("response")
    private String response;
    @SerializedName("message")
    private String massage;
    @SerializedName("data")
    private List<TopFilterData> topFilterData;

    public List<TopFilterData> getTopFilterData() {
        return topFilterData;
    }

    public void setTopFilterData(List<TopFilterData> topFilterData) {
        this.topFilterData = topFilterData;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public class TopFilterData{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        private boolean is_selected;

        public boolean isIs_selected() {
            return is_selected;
        }

        public void setIs_selected(boolean is_selected) {
            this.is_selected = is_selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
