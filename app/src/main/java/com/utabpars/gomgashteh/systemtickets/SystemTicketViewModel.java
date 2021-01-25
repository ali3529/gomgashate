package com.utabpars.gomgashteh.systemtickets;

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

public class SystemTicketViewModel extends ViewModel {
    MutableLiveData<SystemTicketModel> systemTicketModelMutableLiveData=new MutableLiveData<>();
    public void getSystemTicket(String user_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getSystemMessage(user_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<SystemTicketModel>() {
            @Override
            public void onSuccess(@NonNull SystemTicketModel systemTicketModel) {
                systemTicketModelMutableLiveData.setValue(systemTicketModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("sdvsvsdb", "onError: "+e.toString());
            }
        }));
    }
}
