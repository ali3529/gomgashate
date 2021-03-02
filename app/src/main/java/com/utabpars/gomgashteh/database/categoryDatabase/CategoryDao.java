package com.utabpars.gomgashteh.database.categoryDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CategoryDao {
    @Insert
    void setCategoryToDB(Category categoryToDB);
    @Insert
    void setCollectionToDB(Collection collectionToDB);
    @Insert
    void setSubsetToDB(Subset subsetToDB);
    @Insert
    void setSubset2ToDB(Subset2 subset2ToDB);
    @Insert
    void setAttrebiuteToDB(Attrebiute attrebiuteToDB);

//    @Query("SELECT * FROM  Category  ")
//    LiveData<List<Category>> getCategory();

    @Query("SELECT * FROM  Category  ")
    Flowable<List<Category>> getCategoryRx();


    @Query("SELECT * FROM  collection WHERE category_id = :id ")
    LiveData<List<Collection>> getCollection(String id);


    //get subset1
    @Query("SELECT * FROM  subset WHERE collection_id = :collection_id")
    Flowable<List<Subset>> getSubset(String collection_id);

    @Query("SELECT * FROM  attrebiute WHERE collection_id = :collection_id")
    Flowable<List<Attrebiute>> getAttrebiute(String collection_id);


    //get subset2
    @Query("SELECT * FROM  subset2 WHERE subset_id = :subset_id")
    Flowable<List<Subset2>> getSubset2(String subset_id);

    @Query("SELECT * FROM  attrebiute WHERE subset_id = :subset_id")
    Flowable<List<Attrebiute>> getAttrebiuteSubset2(String subset_id);

    @Query("SELECT * FROM  attrebiute WHERE subset2_id = :subset2_id")
    Flowable<List<Attrebiute>> getAttrebiuteSubset2last(String subset2_id);




}
