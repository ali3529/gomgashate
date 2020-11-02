package com.utabpars.gomgashteh.paging.filterpaging;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncCollectionBinding;
import com.utabpars.gomgashteh.model.AnoncmentModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilterItemDataSource extends PageKeyedDataSource<Integer, AnoncmentModel.Detile> {
    static private String id;
    //@SuppressLint("StaticFieldLeak")
    static FragmentAnnouncCollectionBinding binding;
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, AnoncmentModel.Detile> callback) {
        binding.setProgress(true);
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getFilterAnnouncment(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        if (anoncmentModel.getResponse().equals("1")){
                            if (anoncmentModel.getData()!=null){
                                callback.onResult(anoncmentModel.getData(),null,1);
                                binding.setVisivility(false);
                                binding.setProgress(false);
                            }else {
                                Log.d("paginfjnasfd", "onSuccess: "+"not found anouncment");
                                binding.setVisivility(true);
                                binding.setProgress(false);
                            }

                        }

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("paginfjnasfd", "onSuccess: "+e.toString());

                    }
                }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, AnoncmentModel.Detile> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, AnoncmentModel.Detile> callback) {

    }
    public void getCallectionId(String id){
        this.id=id;
    }

    public void getBind(FragmentAnnouncCollectionBinding binding){
        this.binding=binding;
    }
}
