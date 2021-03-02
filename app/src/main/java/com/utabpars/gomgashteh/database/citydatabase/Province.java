package com.utabpars.gomgashteh.database.citydatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.google.gson.annotations.SerializedName;

@Entity
public class Province {
    @PrimaryKey(autoGenerate = true)
    private int column_id;
    @SerializedName("id")
    private String province_id;
    @SerializedName("name")
    private String province_name;

    private boolean selected_other_City;

    public boolean isSelected_other_City() {
        return selected_other_City;
    }

    public void setSelected_other_City(boolean selected_other_City) {
        this.selected_other_City = selected_other_City;
    }

    private boolean selected_province;

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

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public boolean isSelected_province() {
        return selected_province;
    }

    public void setSelected_province(boolean selected_province) {
        this.selected_province = selected_province;
    }
}
