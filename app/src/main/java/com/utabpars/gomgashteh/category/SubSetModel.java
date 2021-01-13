package com.utabpars.gomgashteh.category;

import com.google.gson.annotations.SerializedName;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.util.List;

public class SubSetModel {
    @SerializedName("response")
    private String response;
    @SerializedName("data")
    private List<CategoryModel.ListData> listData;
    @SerializedName("message")
    private String masasge;

    public String getMasasge() {
        return masasge;
    }

    public void setMasasge(String masasge) {
        this.masasge = masasge;
    }

    public List<CategoryModel.ListData> getListData() {
        return listData;
    }

    public void setListData(List<CategoryModel.ListData> listData) {
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
        private String id;
        @SerializedName("name")
        private String categoryName;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private boolean selected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
