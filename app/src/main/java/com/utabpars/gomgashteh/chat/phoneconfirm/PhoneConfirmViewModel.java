package com.utabpars.gomgashteh.chat.phoneconfirm;

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

public class PhoneConfirmViewModel extends ViewModel {
   public MutableLiveData<PhoneConfirmModel> phoneConfirmModelMutableLiveData=new MutableLiveData<>();

    public void confirmPhone(String user_id,String ticked_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.phoneConfirm(user_id,ticked_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<PhoneConfirmModel>() {
            @Override
            public void onSuccess(@NonNull PhoneConfirmModel phoneConfirmModel) {
                if (phoneConfirmModel.getResponse().equals("1")){
                    phoneConfirmModelMutableLiveData.setValue(phoneConfirmModel);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("svsdvdvdsvsd", "onError: "+e.toString());
            }
        }));
    }
}
