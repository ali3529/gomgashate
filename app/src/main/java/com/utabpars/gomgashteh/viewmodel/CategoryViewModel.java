package com.utabpars.gomgashteh.viewmodel;

import android.util.Log;
import android.widget.Toast;

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

public class CategoryViewModel extends ViewModel {
     MutableLiveData<CategoryModel> categoryModelMutableLiveData=new MutableLiveData<>();
    CompositeDisposable compositeDisposable;
    public CategoryViewModel() {
        getCategory();
    }

    public void getCategory(){
        ApiInterface apiInterface= ApiClient.getApiClient();
         compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getcategories()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
            @Override
            public void onSuccess(@NonNull CategoryModel categoryModel) {
                if (categoryModel.getResponse().equals("1")){
                    categoryModelMutableLiveData.postValue(categoryModel);

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("dsfsd", "onError: "+e.toString());
            }
        }));
    }

    public MutableLiveData<CategoryModel> categoriesMutableLiveData(){
        return categoryModelMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
