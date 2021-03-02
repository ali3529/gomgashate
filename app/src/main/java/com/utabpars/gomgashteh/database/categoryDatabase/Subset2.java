package com.utabpars.gomgashteh.database.categoryDatabase;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity
public class Subset2{
    @PrimaryKey(autoGenerate = true)
    private int column_id;
    @ColumnInfo(name = "subset2_id")
    @SerializedName("id")
    public String id;
    @ColumnInfo(name = "subset2_name")
    @SerializedName("name")
    public String name;

    @SerializedName("subset_id")
    private String subset_id;

    public String getSubset_id() {
        return subset_id;
    }

    public void setSubset_id(String subset_id) {
        this.subset_id = subset_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColumn_id() {
        return column_id;
    }

    public void setColumn_id(int column_id) {
        this.column_id = column_id;
    }
}