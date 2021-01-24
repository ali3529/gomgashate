package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AnoncmentModel {
    @SerializedName("response")
    private String response;
    @SerializedName("data")
    private List<Detile> data=new ArrayList<>();
    @SerializedName("massage")
    private String massage;
    @SerializedName("last_page")
    private int last_page;

    @SerializedName("next_page_url")
    private String next_page_url;

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<Detile> getData() {
        return data;
    }

    public void setData(List<Detile> data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public AnoncmentModel(String response) {
        this.response = response;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public class Detile {
        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;


        @SerializedName("city")
        private String city;

        @SerializedName("category")
        private String category;


        @SerializedName("detail")
        private String detail;

        @SerializedName("pictures")
        private String pictures;

        @SerializedName("type")
        private String type;

        @SerializedName("user")
        private String user;

        @SerializedName("create")
        private String create;

        @SerializedName("reward")
        private String reward;

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }


        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getCreate() {
            return create;
        }

        public void setCreate(String create) {
            this.create = create;
        }
    }
}
