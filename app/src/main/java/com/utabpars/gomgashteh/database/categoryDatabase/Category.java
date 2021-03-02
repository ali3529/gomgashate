package com.utabpars.gomgashteh.database.categoryDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity
public class Category{
    @PrimaryKey(autoGenerate = true)
    private int column_id;
    @ColumnInfo(name = "category_id")
    @SerializedName("id")
    private String id;
    @ColumnInfo(name = "category_name")
    @SerializedName("name")
    private String name;

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
}
