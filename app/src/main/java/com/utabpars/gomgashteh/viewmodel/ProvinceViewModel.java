package com.utabpars.gomgashteh.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.CategoryModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProvinceViewModel extends ViewModel {
   public MutableLiveData<CategoryModel> categoryModelMutableLiveData=new MutableLiveData<>();
    public ProvinceViewModel() {
        getProvinces();
    }

    private void getProvinces(){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.Provinces()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
            @Override
            public void onSuccess(@NonNull CategoryModel categoryModel) {
                categoryModelMutableLiveData.postValue(categoryModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        }));
    }
}
