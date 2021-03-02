package com.utabpars.gomgashteh.database.citydatabase;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityDatabaseModel {
    @SerializedName("province")
    private List<Province> provinces=new ArrayList<>();
    @SerializedName("city")
    private List<City> cities=new ArrayList<>();

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
