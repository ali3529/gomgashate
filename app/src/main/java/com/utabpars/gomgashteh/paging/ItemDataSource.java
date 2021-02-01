package com.utabpars.gomgashteh.paging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.ActivityMainBinding;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncementBinding;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.ProgressModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItemDataSource extends PageKeyedDataSource<Integer, AnoncmentModel.Detile> {

    public static final int PAGE=1;
    public static final int PAGESIZE=5;
    public int lastPage=0;
   static FragmentAnnouncementBinding binding;
    static Context context;
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, AnoncmentModel.Detile> callback) {
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        binding.setProgress(true);
        compositeDisposable.add(apiInterface.getAnnouncement(PAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                if (anoncmentModel.getResponse()!=null){
                    if (anoncmentModel.getResponse().equals("1")){
                        callback.onResult(anoncmentModel.getData(),null,PAGE);
                        binding.setProgress(false);

                        binding.setLayoutvisibility(false);

                    }
                }

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Toast.makeText(context, "عدم ارتباط با اینترنت", Toast.LENGTH_LONG).show();
                binding.setLayoutvisibility(true);
                binding.setProgress(false);
                binding.setRefresh(false);
            }
        }));

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, AnoncmentModel.Detile> callback) {
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
        compositeDisposable.add(apiInterface.getAnnouncement(adjacentKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        if (anoncmentModel.getResponse()!=null) {
                            if (anoncmentModel.getResponse().equals("1")) {

                                callback.onResult(anoncmentModel.getData(), adjacentKey);
                            }
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        binding.setLayoutvisibility(true);
                        binding.setProgress(false);
                    }
                }));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, AnoncmentModel.Detile> callback) {
        ApiInterface apiInterface= ApiClient.getApiClient();
        if (lastPage!=params.key){
            binding.setProgressbelow(true);
        }

        CompositeDisposable compositeDisposable=new CompositeDisposable();
        Integer key = true? params.key + 1 : null;
      //  Integer key = (params.key > 1) ? params.key + 1 : null;
        compositeDisposable.add(apiInterface.getAnnouncement(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        if (anoncmentModel.getResponse()!=null) {
                            if (anoncmentModel.getResponse().equals("1") && anoncmentModel.getNext_page_url()!=null) {

                                    lastPage=anoncmentModel.getLast_page();
                                    callback.onResult(anoncmentModel.getData(), key);
                                    binding.setProgressbelow(false);


                            }
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        binding.setLayoutvisibility(true);
                        binding.setProgress(false);
                    }
                }));
    }



    public  void getbind(FragmentAnnouncementBinding binding, Context context){
     this.binding=binding;
     this.context=context;

    }


}
