package com.utabpars.gomgashteh.paging.topfilterpaging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.model.AnoncmentModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TopFilterDataSource extends PageKeyedDataSource<Integer, AnoncmentModel.Detile> {
   private static String id;

    public TopFilterDataSource() {
    }

    public TopFilterDataSource(String id) {
        this.id = id;
    }

    public static final int PAGE=1;
    public static final int PAGESIZE=5;
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, AnoncmentModel.Detile> callback) {
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getTopFilterAnnouncement(PAGE,id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                if (anoncmentModel.getResponse().equals("1")){
                    callback.onResult(anoncmentModel.getData(),null,PAGE);
                    Log.d("sdfsdfdf", "onSuccess: "+anoncmentModel.getData().get(0).getType());
                    Log.d("sdfsdfdf", "onSuccess: "+anoncmentModel.getData().get(0).getId());
                    Log.d("sdfsdfdf", "onSuccess: "+anoncmentModel.getData().get(0).getTitle());
                    Log.d("sdfsdfdf", "onSuccess: "+anoncmentModel.getData().get(0).getCategory());
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d("sdfsdfdf", "onSuccess: "+e.toString());
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
