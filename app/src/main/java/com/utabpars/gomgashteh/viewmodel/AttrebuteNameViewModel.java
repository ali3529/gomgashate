package com.utabpars.gomgashteh.viewmodel;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.AtrrNameModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AttrebuteNameViewModel extends ViewModel {
public MutableLiveData<String> nameModelMutableLiveData=new MutableLiveData<>();
public boolean eventHandle=false;
    public void getAttrName(String colection_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add( apiInterface.getAttributesName(colection_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<AtrrNameModel>() {
            @Override
            public void onSuccess(@NonNull AtrrNameModel s) {
                eventHandle=true;
                if (eventHandle) {
                    nameModelMutableLiveData.setValue(s.getAttr());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("sdfdsfsdfdsf", "onSuccess: "+e.toString());
            }
        }));
    }

    public void setEvant(boolean event){
        eventHandle=event;
    }
}
