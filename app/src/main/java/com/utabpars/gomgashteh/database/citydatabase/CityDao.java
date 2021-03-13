package com.utabpars.gomgashteh.database.citydatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CityDao {
    @Insert
    void insertProvinceToDB(Province province);

    @Insert
    void insertCityToDB(City city);


    @Query("SELECT * FROM province")
    Flowable<List<Province>> getProvnce();

    @Query("SELECT * FROM city WHERE province_id = :province_id")
    Single<List<City>> getCity(String province_id);

//    @Query("SELECT * FROM city WHERE province_id = :province_id")
//    Single<List<City>> getCityFlow(String province_id);

    @Update()
    void selectedCity(City city );

    @Query("UPDATE province Set selected_other_City=:selected WHERE province_id= :province_id")
    void selectedProvinceOtherCity(String province_id, boolean selected );

    @Query("UPDATE province Set selected_province=:selected WHERE province_id= :province_id")
    void selectedProvinceCity(String province_id, boolean selected );

    @Query("SELECT * FROM city WHERE selected_city=1")
    Flowable<List<City>> getCitySelected();

    @Query("SELECT * FROM city WHERE selected_city=1")
    List<City> getCitySelectedForFilterAnnounce();

    @Query("UPDATE city SET other_city_selected=0  WHERE other_city_selected=1")
    void clearSelectedCity();
    @Query("UPDATE province Set selected_other_City=0 WHERE province_id=:province_id")
    void clearProvinceSelectedOtherCity(String province_id);

    @Query("UPDATE province Set selected_other_City=0")
    void clearProvinceAfterInsertAnnunce();

    @Query("UPDATE province Set selected_province=0 WHERE province_id=:province_id")
    void clearProvinceSelected(String province_id);

    @Query("UPDATE city Set selected_city=0 WHERE province_id= :province_id ")
    void clearAllCitySelected(String province_id);

    @Query("UPDATE city Set other_city_selected=0 WHERE province_id= :province_id ")
    void clearAllOtherCitySelected(String province_id);

    @Query("SELECT * FROM city WHERE other_city_selected=1")
    Flowable<List<City>> getOtherCitySelected();

    //selected size for not select plus 10
    @Query("SELECT * FROM city WHERE other_city_selected=1")
    Flowable<List<City>> getSelectedCitySize();



}
