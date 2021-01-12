package com.utabpars.gomgashteh.category;

import android.util.Log;

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

public class SubsetViewModel extends ViewModel {
    MutableLiveData<CategoryModel> subsetMutableLiveData=new MutableLiveData<>();

    public void getSubset(String collection_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getSubsets(collection_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
            @Override
            public void onSuccess(@NonNull CategoryModel categoryModel) {
                if (categoryModel.getResponse().equals("1")){
                    subsetMutableLiveData.setValue(categoryModel);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("safcaccv", "onError: "+e.toString());
            }
        }));
    }
}
