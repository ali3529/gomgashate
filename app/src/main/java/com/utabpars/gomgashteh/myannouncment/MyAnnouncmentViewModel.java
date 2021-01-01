package com.utabpars.gomgashteh.myannouncment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.AnoncmentModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyAnnouncmentViewModel extends ViewModel {
    MutableLiveData<AnoncmentModel> myAnnouncmentLiveData=new MutableLiveData<>();
    public void MyAnnouncment(String user_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getMyAnnouncment(user_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
            @Override
            public void onSuccess(@NonNull AnoncmentModel anoncmentModel) {
                myAnnouncmentLiveData.setValue(anoncmentModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Sdfsdvdsv", "onError: "+e.toString());
            }
        }));
    }
}
