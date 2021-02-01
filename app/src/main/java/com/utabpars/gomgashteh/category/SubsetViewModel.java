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
    MutableLiveData<SubSetModel> subsetMutableLiveData=new MutableLiveData<>();
    MutableLiveData<Boolean> emptySubSet=new MutableLiveData<>();
    MutableLiveData<SubSetModel> attributeMutableLiveData=new MutableLiveData<>();
    static SubSetCallBack subSetCallBack;

    public void getSubset(String collection_id,String sub_name,String type){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getSubsets(collection_id,sub_name,type)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<SubSetModel>() {
            @Override
            public void onSuccess(@NonNull SubSetModel subSetModel) {
                if (subSetModel.getResponse().equals("1")){
                    subSetCallBack.onSubsetCallback(subSetModel);
                }else if (subSetModel.getResponse().equals("0")){
                    subSetCallBack.emptyCallback(false,subSetModel);
                }else if (subSetModel.getResponse().equals("2")){
                    subSetCallBack.onAttributeCallback(subSetModel);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("safcaccv", "onError: "+e.toString());
            }
        }));
    }

    public void getCallBack(SubSetCallBack subSetCallBack){
        this.subSetCallBack=subSetCallBack;
    }
}
