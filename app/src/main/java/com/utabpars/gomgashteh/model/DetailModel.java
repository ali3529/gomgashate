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
        @SerializedName("create")
        private String created_at;
        @SerializedName("pictures")
        private List<String> pictures=new ArrayList<>();
        @SerializedName("other_city")
        private List<String> otherCity;

        @SerializedName("is_mark")
        private boolean isMark;

        @SerializedName("share_link")
        private String shareLink;

        @SerializedName("report_list")
        List<String> report_list=new ArrayList<>();

        @SerializedName("is_report")
        private boolean report;

        @SerializedName("reward")
        private String reward;

        @SerializedName("attributes")
        private String attributes;

        @SerializedName("pishkhan")
        private String pishkhan;

        @SerializedName("show_address")
        private String showAddress;
        @SerializedName("showaddress_edit")
        private String showAddressEdit;

        @SerializedName("office_name")
        private String office_name;

        @SerializedName("tell")
        private String office_tell;

        public String getOffice_name() {
            return office_name;
        }

        public void setOffice_name(String office_name) {
            this.office_name = office_name;
        }

        public String getOffice_tell() {
            return office_tell;
        }

        public void setOffice_tell(String office_tell) {
            this.office_tell = office_tell;
        }

        public String getShowAddressEdit() {
            return showAddressEdit;
        }

        public void setShowAddressEdit(String showAddressEdit) {
            this.showAddressEdit = showAddressEdit;
        }

        public String getShowAddress() {
            return showAddress;
        }

        public void setShowAddress(String showAddress) {
            this.showAddress = showAddress;
        }

        public String getPishkhan() {
            return pishkhan;
        }

        public void setPishkhan(String pishkhan) {
            this.pishkhan = pishkhan;
        }

        public String getAttributes() {
            return attributes;
        }

        public void setAttributes(String attributes) {
            this.attributes = attributes;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public boolean isReport() {
            return report;
        }

        public void setReport(boolean report) {
            this.report = report;
        }

        public List<String> getReport_list() {
            return report_list;
        }

        public void setReport_list(List<String> report_list) {
            this.report_list = report_list;
        }

        public String getShareLink() {
            return shareLink;
        }

        public void setShareLink(String shareLink) {
            this.shareLink = shareLink;
        }

        public boolean isMark() {
            return isMark;
        }

        public void setMark(boolean mark) {
            isMark = mark;
        }

        public List<String> getOtherCity() {
            return otherCity;
        }

        public void setOtherCity(List<String> otherCity) {
            this.otherCity = otherCity;
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
