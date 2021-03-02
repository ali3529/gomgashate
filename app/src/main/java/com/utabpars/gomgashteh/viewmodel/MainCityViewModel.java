package com.utabpars.gomgashteh.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainCityViewModel extends AndroidViewModel {
   public MutableLiveData<List<City>> provinceMutableLiveData=new MutableLiveData<>();
   CityDatabase db;
    public MainCityViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        db=CityDatabase.getInstance(getApplication().getApplicationContext());
    }


public void updateSelectedCity(City city){
        db.cityDao().selectedCity(city);
}

    public void getProvinceFromDB(String province_id){
       getProvince(province_id).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(province->{
                           Log.d("fdbfdbfbf", "getProvinceFromDB: ");
                   provinceMutableLiveData.setValue(province);
                       },
                       error->{
                           Log.d("dsvdsv", "getMainCityFromDB: "+error.toString());
                       });
    }

   private Single<List<City>> getProvince(String province_id){
        return db.cityDao().getCity(province_id);
    }


    public void setSelectedProvince(String province_id,boolean selec){
        db.cityDao().selectedProvinceCity(province_id,selec);
    }





}
