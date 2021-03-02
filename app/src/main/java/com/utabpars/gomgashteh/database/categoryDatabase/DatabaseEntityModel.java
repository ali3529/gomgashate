package com.utabpars.gomgashteh.database.categoryDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseEntityModel{
    @SerializedName("is_category_update_available")
    private String version_update;

    public String getVersion_update() {
        return version_update;
    }

    public void setVersion_update(String version_update) {
        this.version_update = version_update;
    }

    @SerializedName("category")
    public List<Category> category=new ArrayList<>();

    @SerializedName("collection")
    public List<Collection> Collection=new ArrayList<>();

    @SerializedName("subset")
    public List<Subset> Subset=new ArrayList<>();

    @SerializedName("subset2")
    public List<Subset2> Subset2=new ArrayList<>();

    @SerializedName("attribute")
    public List<Attrebiute> attribute=new ArrayList<>();

    public List<Attrebiute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attrebiute> attribute) {
        this.attribute = attribute;
    }

    public List<com.utabpars.gomgashteh.database.categoryDatabase.Collection> getCollection() {
        return Collection;
    }

    public void setCollection(List<com.utabpars.gomgashteh.database.categoryDatabase.Collection> collection) {
        Collection = collection;
    }

    public List<com.utabpars.gomgashteh.database.categoryDatabase.Subset> getSubset() {
        return Subset;
    }

    public void setSubset(List<com.utabpars.gomgashteh.database.categoryDatabase.Subset> subset) {
        Subset = subset;
    }

    public List<com.utabpars.gomgashteh.database.categoryDatabase.Subset2> getSubset2() {
        return Subset2;
    }

    public void setSubset2(List<com.utabpars.gomgashteh.database.categoryDatabase.Subset2> subset2) {
        Subset2 = subset2;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }






}



