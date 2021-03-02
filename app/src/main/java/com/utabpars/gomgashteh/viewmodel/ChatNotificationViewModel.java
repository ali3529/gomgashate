package com.utabpars.gomgashteh.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
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
    public MutableLiveData<ChatNotificationModel> chatNotificationModelMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<String> category_update=new MutableLiveData<>();
    public MutableLiveData<Boolean> error=new MutableLiveData<>();

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
                chatNotificationModelMutableLiveData.setValue(chatNotificationModel);
                category_update.setValue(chatNotificationModel.getVersion_update());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                error.setValue(true);

            }
        }));
    }
}
