package com.utabpars.gomgashteh.database.categoryDatabase;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity
public class Collection{
    @PrimaryKey(autoGenerate = true)
    private int column_id;
    @ColumnInfo(name = "collection_id")
    @SerializedName("id")
    private String id;
    @ColumnInfo(name = "collection_name")
    @SerializedName("name")
    private String name;
    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    private String category_id;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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