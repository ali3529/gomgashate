package com.utabpars.gomgashteh.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.ChatsModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatsViewModel extends ViewModel {
  public   MutableLiveData<ChatsModel> chatsModelMutableLiveData=new MutableLiveData<>();
    public void getTickets(String user_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getTickets(user_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<ChatsModel>() {
            @Override
            public void onSuccess(@NonNull ChatsModel chatsModel) {
                if (chatsModel.getResponse()==null){

                }else {
                    if (chatsModel.getResponse().equals("1")){
                        chatsModelMutableLiveData.setValue(chatsModel);
                    }
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("acasccasc", "onError: "+e.toString());
            }
        }));
    }
}
