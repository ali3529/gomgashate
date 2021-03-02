package com.utabpars.gomgashteh.database.citydatabase;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CityFromServerViewModel extends ViewModel {
   public MutableLiveData<CityDatabaseModel> cityDatabaseModelMutableLiveData=new MutableLiveData<>();
    public CityFromServerViewModel() {
        getCity();
    }

    public void getCity(){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getCityForDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CityDatabaseModel>() {
                    @Override
                    public void onSuccess(@NonNull CityDatabaseModel cityDatabaseModel) {
                        cityDatabaseModelMutableLiveData.setValue(cityDatabaseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                }));
    }

}
