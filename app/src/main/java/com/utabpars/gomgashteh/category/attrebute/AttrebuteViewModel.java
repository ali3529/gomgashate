package com.utabpars.gomgashteh.category.attrebute;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AttrebuteViewModel extends ViewModel {
    public MutableLiveData<SpinnerModel> spinnerModelMutableLiveData=new MutableLiveData<>();
    public void getAttrebute(String id,String type){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getAttrebute(id,type)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<SpinnerModel>() {
            @Override
            public void onSuccess(@NonNull SpinnerModel spinnerModel) {
                spinnerModelMutableLiveData.setValue(spinnerModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("sdgfdsgdsg", "onError: "+e.toString());
            }
        }));
    }
}
