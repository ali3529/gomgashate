package com.utabpars.gomgashteh.database.categoryDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity
public class Attrebiute {
    @PrimaryKey(autoGenerate = true)
    private int column_id;
    @ColumnInfo(name = "attr_id")
    @SerializedName("id")
    private String id;
    @ColumnInfo(name = "attr_name")
    @SerializedName("name")
    private String name;

    @SerializedName("collection_id")
    private String collection_id;

    @SerializedName("subset_id")
    private String subset_id;

    @SerializedName("subset2_id")
    private String subset2_id;

    @SerializedName("subset3_id")
    private String subset3_id;

    public int getColumn_id() {
        return column_id;
    }

    public void setColumn_id(int column_id) {
        this.column_id = column_id;
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

    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    public String getSubset_id() {
        return subset_id;
    }

    public void setSubset_id(String subset_id) {
        this.subset_id = subset_id;
    }

    public String getSubset2_id() {
        return subset2_id;
    }

    public void setSubset2_id(String subset2_id) {
        this.subset2_id = subset2_id;
    }

    public String getSubset3_id() {
        return subset3_id;
    }

    public void setSubset3_id(String subset3_id) {
        this.subset3_id = subset3_id;
    }
}
