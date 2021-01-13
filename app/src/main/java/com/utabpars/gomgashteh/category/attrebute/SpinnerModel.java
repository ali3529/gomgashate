package com.utabpars.gomgashteh.category.attrebute;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpinnerModel {
    @SerializedName("response")
    private String response;
    @SerializedName("data")
    private List<AttrebuteData> attrebuteData=new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<AttrebuteData> getAttrebuteData() {
        return attrebuteData;
    }

    public void setAttrebuteData(List<AttrebuteData> attrebuteData) {
        this.attrebuteData = attrebuteData;
    }

    static class AttrebuteData{

        public AttrebuteData(String name, String id, List<String> values) {
            this.name = name;
            this.id = id;
            this.values = values;
        }
        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private String id;
        @SerializedName("values")
        List<String> values=new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }
    }
}
