package com.utabpars.gomgashteh.paging.provinceFilter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.AnoncmentModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProvinceFilterDataSource extends PageKeyedDataSource<Integer, AnoncmentModel.Detile> {
    static String city;
    static String type;
     static String key_search;

    public static final int PAGE=1;

    public ProvinceFilterDataSource() {
    }

    public void FilterDataSource(String city,String type,String key_search) {
        this.city=city;
        this.type=type;
        this.key_search=key_search;
        Log.d("dsfdsfdsf", "FilterDataSource: "+city);

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, AnoncmentModel.Detile> callback) {


        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.filterAnnouncement(
                city,
                type,
                key_search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        if (anoncmentModel.getResponse().equals("1")){
                            callback.onResult(anoncmentModel.getData(),null,PAGE);
                            Log.d("vxcvdxvs", "onSuccess: gooooood");

                        }


                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("vxcvdxvs", "onSuccess: error"+e.toString());

                    }
                }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, AnoncmentModel.Detile> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, AnoncmentModel.Detile> callback) {

    }





}
