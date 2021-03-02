package com.utabpars.gomgashteh.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.database.citydatabase.Province;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProvinceViewModel extends AndroidViewModel {
    public MutableLiveData<List<Province>> provinceMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<List<City>> listMutableLiveData=new MutableLiveData<>();
    CityDatabase db;
    public ProvinceViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        db=CityDatabase.getInstance(getApplication().getApplicationContext());
        getProvinceFromDB();
    }


    public void getProvinceFromDB(){
        getProvince().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(province->{
                            Log.d("fdbfdbfbf", "getProvinceFromDB: ");
                            provinceMutableLiveData.setValue(province);
                        },
                        error->{
                            Log.d("dsvdsv", "getMainCityFromDB: "+error.toString());
                        });
    }

    public void clearAllCity(String province_id){
   db.cityDao().clearAllCitySelected(province_id);
    }

    public void onSelectProvince(String province_id){
        db.cityDao().clearProvinceSelected(province_id);
    }

    private Flowable<List<Province>> getProvince(){
        return db.cityDao().getProvnce();
    }

    public void clearProvince(String province_id){
        db.cityDao().clearProvinceSelectedOtherCity(province_id);
    }

    public void clearAllOtherCity(String province_id){
        db.cityDao().clearAllOtherCitySelected(province_id);
    }
}
