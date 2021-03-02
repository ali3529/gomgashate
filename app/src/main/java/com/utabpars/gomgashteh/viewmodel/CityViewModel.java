package com.utabpars.gomgashteh.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.database.citydatabase.Province;

import java.util.List;

import hu.akarnokd.rxjava3.bridge.RxJavaBridge;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CityViewModel extends AndroidViewModel {
    public MutableLiveData<List<City>> provinceMutableLiveData=new MutableLiveData<>();
    CityDatabase db;
    public CityViewModel(@NonNull Application application) {
        super(application);
        db=CityDatabase.getInstance(getApplication().getApplicationContext());
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

    private Single<List<City>> getProvince(String province_id)
    {
        return db.cityDao().getCity(province_id);
    }
}
