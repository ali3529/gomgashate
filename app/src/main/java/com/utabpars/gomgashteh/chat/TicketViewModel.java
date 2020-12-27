package com.utabpars.gomgashteh.chat;

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

public class TicketViewModel extends ViewModel {
    MutableLiveData<TicketResponseModel> ticketMutableLiveData=new MutableLiveData<>();

    public void getTicket(String ticket_id,String user_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getTicketInfo(ticket_id,user_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<TicketResponseModel>() {
            @Override
            public void onSuccess(@NonNull TicketResponseModel ticketResponseModel) {
                if (ticketResponseModel.getResponse().equals("1")){
                    ticketMutableLiveData.setValue(ticketResponseModel);
                    Log.d("sdsadasd", "onSuccess: ");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("sdsadasd", "onError: "+e.toString());
            }
        }));

    }
}
