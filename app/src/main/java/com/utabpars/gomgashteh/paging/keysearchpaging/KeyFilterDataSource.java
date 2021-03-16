package com.utabpars.gomgashteh.paging.keysearchpaging;

import android.util.Log;

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

public class KeyFilterDataSource extends PageKeyedDataSource<Integer, AnoncmentModel.Detile> {
     String city;
     String type;
     String key_search;
    EmtySearchResult emtySearchResult;

    public static final int PAGE=1;

    public KeyFilterDataSource(String city, String type, String key_search,EmtySearchResult emtySearchResult) {
        this.city=city;
        this.type=type;
        this.key_search=key_search;
        this.emtySearchResult=emtySearchResult;
        Log.d("bfdbtfnhftj", "FilterDataSource: "+city+type+key_search);


    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, AnoncmentModel.Detile> callback) {

        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.keyfilterAnnouncement(
                city,
                type,
                key_search,
                PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        Log.d("bfdbtfnhftj", "onSuccess: out");
                        if (anoncmentModel.getResponse().equals("1")){
                            callback.onResult(anoncmentModel.getData(),null,PAGE);
                            Log.d("bfdbtfnhftj", "onSuccess: "+anoncmentModel.getData().size());

                        }else {
                            Log.d("bfdbtfnhftj", "onSuccess: "+"not found");
                          //  emptyAnnouncement.onEmptyAnnouncement();
                            emtySearchResult.onEmptySEarch();

                        }


                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("bfdbtfnhftj", "onSuccess: error"+e.toString());


                    }
                }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, AnoncmentModel.Detile> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, AnoncmentModel.Detile> callback) {
        Integer key = true? params.key + 1 : null;
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.keyfilterAnnouncement(
                city,
                type,
                key_search,
                key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        Log.d("bfdbtfnhftj", "onSuccess: out");
                        if (anoncmentModel.getResponse().equals("1")){
                            callback.onResult(anoncmentModel.getData(),key);
                            Log.d("bfdbtfnhftj", "onSuccess: "+anoncmentModel.getData().size());

                        }else {
                            Log.d("bfdbtfnhftj", "onSuccess: "+"not found");
                            //  emptyAnnouncement.onEmptyAnnouncement();
                        }


                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("bfdbtfnhftj", "onSuccess: error"+e.toString());


                    }
                }));
    }

    public interface EmtySearchResult {
        void onEmptySEarch();
    }
}
