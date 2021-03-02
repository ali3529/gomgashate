package com.utabpars.gomgashteh.database.categoryDatabase;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity
public class Subset{
    @PrimaryKey(autoGenerate = true)
    private int column_id;
    @ColumnInfo(name = "subset_id")
    @SerializedName("id")
    public String id;
    @ColumnInfo(name = "subset_name")
    @SerializedName("name")
    public String name;

    @SerializedName("collection_id")
    private String collection_id;

    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
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