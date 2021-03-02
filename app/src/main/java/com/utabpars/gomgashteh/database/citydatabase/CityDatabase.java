package com.utabpars.gomgashteh.database.citydatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Province.class,
                     City.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
    private static CityDatabase cityDatabase=null;

    public static CityDatabase getInstance(Context context){
        if (cityDatabase==null){
            cityDatabase= Room.databaseBuilder(context.getApplicationContext(),
                   CityDatabase.class,
                    "cityDB")
                    //.fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return cityDatabase;
    }

}
