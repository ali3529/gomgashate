package com.utabpars.gomgashteh.markannouncment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.AnoncmentModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarkViewModel extends ViewModel {
   public MutableLiveData<MarkModel> markModelMutableLiveData=new MutableLiveData<>();
   public MutableLiveData<AnoncmentModel> myMarkMutableLiveData=new MutableLiveData<>();
    public void markAnnouncement(String user_id,String announce_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.markAnnouncement(user_id,announce_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<MarkModel>() {
            @Override
            public void onSuccess(@NonNull MarkModel markModel) {
                markModelMutableLiveData.setValue(markModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("vfvddddvbfdb", "onError: "+e.toString());
            }
        }));
    }


    public void myMark(String user_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.mymarkAnnouncment(user_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
            @Override
            public void onSuccess(@NonNull AnoncmentModel anoncmentModel) {
                myMarkMutableLiveData.setValue(anoncmentModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("vfvddddvbfdb", "onError: "+e.toString());
            }
        }));
    }
}
