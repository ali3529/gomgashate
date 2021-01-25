package com.utabpars.gomgashteh.systemtickets;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SystemTicketModel {
    @SerializedName("response")
    private String response;
    @SerializedName("massage")
    private String massage;
    @SerializedName("data")
    private List<Data> massageList=new ArrayList<>();

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

    public List<Data> getMassageList() {
        return massageList;
    }

    public void setMassageList(List<Data> massageList) {
        this.massageList = massageList;
    }

    public class Data{
        @SerializedName("text")
        private String text;
        @SerializedName("date")
        private String date;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
