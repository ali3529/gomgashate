package com.utabpars.gomgashteh.managerAnnouncement;

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

public class OptionViewModel extends ViewModel {
    MutableLiveData<ManageModel> manageModelMutableLiveData=new MutableLiveData<>();
    public void getTabs(String announce_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getTabs(announce_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<ManageModel>() {
            @Override
            public void onSuccess(@NonNull ManageModel manageModel) {
                manageModelMutableLiveData.setValue(manageModel);
                Log.d("SDfvdsvsdv", "onError: "+manageModel.getResponse());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("SDfvdsvsdv", "onError: "+e.toString());
            }
        }));
    }
}
