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
    static private String type;
    public static final int PAGE=1;
    //@SuppressLint("StaticFieldLeak")
    static FragmentAnnouncCollectionBinding binding;
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, AnoncmentModel.Detile> callback) {
        binding.setProgress(true);
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getFilterAnnouncment(id,type,PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        if (anoncmentModel.getResponse().equals("1")){
                            if (anoncmentModel.getData()!=null){
                                callback.onResult(anoncmentModel.getData(),null,PAGE);
                                binding.setVisivility(false);
                                binding.setProgress(false);
                            }

                        }else  {
                            binding.setVisivility(true);
                            binding.setProgress(false);
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
        binding.setProgress(true);
        Integer key = true? params.key + 1 : null;
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getFilterAnnouncment(id,type,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        if (anoncmentModel.getResponse().equals("1")){
                            if (anoncmentModel.getData()!=null){
                                callback.onResult(anoncmentModel.getData(),key);
                                binding.setVisivility(false);
                                binding.setProgress(false);
                            }

                        }else  {
                            binding.setVisivility(true);
                            binding.setProgress(false);
                        }

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("paginfjnasfd", "onSuccess: "+e.toString());

                    }
                }));
    }
    public void getCallectionId(String id,String type){
        this.id=id;
        this.type=type;
    }

    public void getBind(FragmentAnnouncCollectionBinding binding){
        this.binding=binding;
    }
}
