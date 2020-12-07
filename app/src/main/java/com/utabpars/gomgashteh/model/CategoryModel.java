package com.utabpars.gomgashteh.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryModel {
    @SerializedName("response")
    private String response;
    @SerializedName("data")
    private List<ListData> listData;

    public List<ListData> getListData() {
        return listData;
    }

    public void setListData(List<ListData> listData) {
        this.listData = listData;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public class ListData{
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String categoryName;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private boolean selected;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
