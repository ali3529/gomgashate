package com.utabpars.gomgashteh.database.citydatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class City {
    @PrimaryKey(autoGenerate = true)
    private  int column_id;

    @SerializedName("id")
    @ColumnInfo(name = "city_id")
    private String city_id;

    @SerializedName("name")
    @ColumnInfo(name = "city_name")
    private String city_name;

    @SerializedName("province")
    @ColumnInfo(name = "province_id")
    private String province_id;
    @ColumnInfo(name = "selected_city")
    private boolean selected_city;

    @ColumnInfo(name = "other_city_selected")
    private boolean selected_otherCity;

    public boolean isSelected_otherCity() {
        return selected_otherCity;
    }

    public void setSelected_otherCity(boolean selected_otherCity) {
        this.selected_otherCity = selected_otherCity;
    }

    public boolean isSelected_city() {
        return selected_city;
    }

    public void setSelected_city(boolean selected_city) {
        this.selected_city = selected_city;
    }

    public int getColumn_id() {
        return column_id;
    }

    public void setColumn_id(int column_id) {
        this.column_id = column_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }


}
