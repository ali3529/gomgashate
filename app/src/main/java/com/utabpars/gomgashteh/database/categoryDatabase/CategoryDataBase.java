package com.utabpars.gomgashteh.database.categoryDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kotlin.jvm.Volatile;

@Database(entities = {Category.class,
                    Collection.class,
                    Subset.class,
                    Subset2.class,
                    Attrebiute.class},version = 1)
public abstract class CategoryDataBase extends RoomDatabase {
    public abstract CategoryDao categoryDao();

    private static CategoryDataBase categoryDataBase=null;

    public static CategoryDataBase getInstance(Context context){
        if (categoryDataBase==null) {
            categoryDataBase = Room.databaseBuilder(context,
                    CategoryDataBase.class,
                    "categoryDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return categoryDataBase;

    }




}
