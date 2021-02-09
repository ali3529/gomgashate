package com.utabpars.gomgashteh.chat.deletmassage;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeletMassageViewModel extends AndroidViewModel {


    public DeletMassageViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
    }

    public void deleteMassage(int answer_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.deleteMassage(answer_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<DeletMassageModel>() {
            @Override
            public void onSuccess(@NonNull DeletMassageModel deletMassageModel) {
                if (deletMassageModel.getResponse().equals("1")){
                    Toast.makeText(getApplication(), deletMassageModel.getMassage(), Toast.LENGTH_SHORT).show();
                    Log.d("dsvdsvdv", "onSuccess: "+deletMassageModel.getMassage());
                }else {
                    Log.d("dsvdsvdv", "onError:  else ");
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("dsvdsvdv", "onError: "+e.toString());
            }
        }));
    }
}
