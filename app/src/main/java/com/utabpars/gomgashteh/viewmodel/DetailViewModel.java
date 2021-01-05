package com.utabpars.gomgashteh.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncmentDetailBinding;
import com.utabpars.gomgashteh.model.DetailModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailViewModel extends ViewModel {
    public MutableLiveData<DetailModel.Data> dataMutableLiveData=new MutableLiveData<>();

    FragmentAnnouncmentDetailBinding binding;
    public void getDetail(int id,String user_id){
        Log.d("dsfdsfds33f", "getDetail: "+user_id);
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getDetail(id,user_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<DetailModel>() {
            @Override
            public void onSuccess(@NonNull DetailModel detailModel) {
                if (detailModel.getResponse()!=null){
                    if (detailModel.getResponse().equals("1")){
                        dataMutableLiveData.setValue(detailModel.getData());

                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("dfsdre", "onError: "+e);
                binding.setLayoutvisibility(true);
                binding.setProgress(false);
            }
        }));
    }

    public MutableLiveData<DetailModel.Data> getMutableDetail(){
        return dataMutableLiveData;
    }

   public void getView(FragmentAnnouncmentDetailBinding binding){
        this.binding=binding;
   }

public void test(int id,String user_id){
      getDetail(id,user_id);
      binding.setLayoutvisibility(false);
      binding.setProgress(true);
}


}
