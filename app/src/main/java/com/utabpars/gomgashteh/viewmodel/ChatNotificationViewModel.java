package com.utabpars.gomgashteh.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.ChatNotificationModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatNotificationViewModel extends ViewModel {
    public MutableLiveData<Integer> chatCounterMutableLiveData=new MutableLiveData<>();

    public void getChatNotification(String user_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getNotification(user_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<ChatNotificationModel>() {
            @Override
            public void onSuccess(@NonNull ChatNotificationModel chatNotificationModel) {
                chatCounterMutableLiveData.setValue(chatNotificationModel.getNotificationNumber());
                Log.d("sdvdsvsdv", "onSuccess: "+chatNotificationModel.getNotificationNumber());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("sdasdsad", "onError: "+e.toString());
            }
        }));
    }
}
