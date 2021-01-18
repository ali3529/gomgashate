package com.utabpars.gomgashteh.category.attrebute;

public class AtttrModel {
    private String id;
    private String value;
    private String ischeck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public AtttrModel(String id, String value, String ischeck) {
        this.id = id;
        this.value = value;
        this.ischeck = ischeck;
    }
    public AtttrModel(String id, String value ) {
        this.id = id;
        this.value = value;

    }
}
