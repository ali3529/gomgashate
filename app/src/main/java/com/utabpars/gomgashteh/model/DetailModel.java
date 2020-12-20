package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DetailModel {

    @SerializedName("response")
    private String response;
    @SerializedName("data")
    private Data data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("city")
        private String city;
        @SerializedName("detail")
        private String detail;

        @SerializedName("announcer_id")
        private String announcer_id;

        public String getAnnouncer_id() {
            return announcer_id;
        }

        public void setAnnouncer_id(String announcer_id) {
            this.announcer_id = announcer_id;
        }

        @SerializedName("category")
        private String collection;
        @SerializedName("type")
        private String type;
        @SerializedName("announcer")
        private String announcer;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("pictures")
        private List<String> pictures=new ArrayList<>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }


        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAnnouncer() {
            return announcer;
        }

        public void setAnnouncer(String announcer) {
            this.announcer = announcer;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public List<String> getPictures() {
            return pictures;
        }

        public void setPictures(List<String> pictures) {
            this.pictures = pictures;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
