package com.utabpars.gomgashteh.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.AppVersionModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CheckUpdateViewModel extends ViewModel {
    private MutableLiveData<AppVersionModel> appVersionModelMutableLiveData=new MutableLiveData<>();
    public CheckUpdateViewModel(){
        checkVersionUpdate();
    }


    private void checkVersionUpdate(){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.update()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<AppVersionModel>() {
            @Override
            public void onSuccess(@NonNull AppVersionModel appVersionModel) {
                if (appVersionModel.getStatus()!=null && appVersionModel.getStatus().equals("1")){
                    appVersionModelMutableLiveData.postValue(appVersionModel);

                }


            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        }));
    }

    public MutableLiveData<AppVersionModel> getAppVersionModelLiveData(){
        return appVersionModelMutableLiveData;
    }
}
