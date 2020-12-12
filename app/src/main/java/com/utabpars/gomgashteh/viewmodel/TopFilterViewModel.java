package com.utabpars.gomgashteh.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.RmModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TopFilterViewModel extends ViewModel {
    public MutableLiveData<RmModel> rmModelMutableLiveData=new MutableLiveData<>();
    CompositeDisposable compositeDisposable;
    public TopFilterViewModel() {
        topfilterData();
    }


    public void topfilterData(){

        ApiInterface apiInterface= ApiClient.getApiClient();
         compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getTopFilter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RmModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RmModel rmModel) {
                        if (rmModel.getResponse().equals("1")){
                            rmModelMutableLiveData.postValue(rmModel);
                            Log.d("sfsdfsdf", "onError: "+rmModel.getTopFilterData().get(0).getName());
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("sfsdfsdf", "onError: "+e.toString());
                    }
                }));


    }


}
