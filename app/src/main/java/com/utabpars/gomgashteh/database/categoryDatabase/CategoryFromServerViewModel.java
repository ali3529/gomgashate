package com.utabpars.gomgashteh.database.categoryDatabase;

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

public class CategoryFromServerViewModel extends ViewModel {
    public MutableLiveData<DatabaseEntityModel> databaseEntityModelMutableLiveData=new MutableLiveData<>();
    public CategoryFromServerViewModel() {

    }

    public void getCategory(){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getCategoryForDB()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<DatabaseEntityModel>() {
            @Override
            public void onSuccess(@NonNull DatabaseEntityModel databaseEntityModel) {
                databaseEntityModelMutableLiveData.setValue(databaseEntityModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Sdvsdvdsv", "onError: "+e.toString());
            }
        }));
    }
}
