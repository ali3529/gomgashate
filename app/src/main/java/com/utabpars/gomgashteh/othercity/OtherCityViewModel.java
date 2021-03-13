package com.utabpars.gomgashteh.othercity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OtherCityViewModel extends AndroidViewModel {
    CityDatabase db;
    int c=0;
   public MutableLiveData<List<City>> listMutableLiveData=new MutableLiveData<>();
    public OtherCityViewModel(@NonNull Application application) {
        super(application);
        db=CityDatabase.getInstance(getApplication().getApplicationContext());
    }

    public void getCityFromDb(String province_id){
        getCity(province_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city->{
                    listMutableLiveData.setValue(city);
                });
    }

    public void getCitySelectedFromDb(){
        getOtherCitySelected().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city->{
                    listMutableLiveData.setValue(city);
                });
    }



    private Single<List<City>> getCity(String province_id){
        return db.cityDao().getCity(province_id);
    }

    private Flowable<List<City>> getOtherCitySelected(){
        return db.cityDao().getOtherCitySelected();
    }

    public void updateSelectetOtherCity(City city){
         db.cityDao().selectedCity(city);
    }

    public void setSelectedProvince(String province_id,boolean selec){
        db.cityDao().selectedProvinceOtherCity(province_id,selec);
    }


    public Flowable<List<City>> getSelectedCitySize(){

        return db.cityDao().getSelectedCitySize();
    }
}
