package com.utabpars.gomgashteh.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.DetailModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<DetailModel.Data> dataMutableLiveData=new MutableLiveData<>();
    public void getDetail(int id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getDetail(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<DetailModel>() {
            @Override
            public void onSuccess(@NonNull DetailModel detailModel) {
                if (detailModel.getResponse()!=null){
                    if (detailModel.getResponse().equals("1")){
                        dataMutableLiveData.setValue(detailModel.getData());
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("dfsd", "onError: "+e);
            }
        }));
    }

    public MutableLiveData<DetailModel.Data> getMutableDetail(){
        return dataMutableLiveData;
    }
}
