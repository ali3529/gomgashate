package com.utabpars.gomgashteh.viewmodel;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.interfaces.LoginRespondeCallBack;
import com.utabpars.gomgashteh.model.RegisterModel;
import com.utabpars.gomgashteh.model.RmModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserAuthenticationViewModel extends ViewModel {
    LoginRespondeCallBack loginRespondeCallBack;

    public MutableLiveData<RmModel> phoneNumberResponseLiveData=new MutableLiveData<>();
    public MutableLiveData<RegisterModel> otpResponseLiveData=new MutableLiveData<>();
    public MutableLiveData<RegisterModel> registerUserLiveData=new MutableLiveData<>();
    public MutableLiveData<Long> timerOtp=new MutableLiveData<>();

    public MutableLiveData<String> error=new MutableLiveData<>();

    public MutableLiveData<String> timerOtpFinish=new MutableLiveData<>();

    public void phoneNumberInterface(LoginRespondeCallBack loginRespondeCallBack) {
        this.loginRespondeCallBack = loginRespondeCallBack;
    }

    public void userAuthentication(String phoneNum){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.userAuthentication(phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RmModel>() {
                    @Override
                    public void onSuccess(@NonNull RmModel rmModel) {
                        if (rmModel.getResponse()!=null){
                            loginRespondeCallBack.otpCallback(rmModel);

                        }



                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.setValue(e.toString());
                        Log.d("sdfsdf", "onError: "+e.toString());
                    }
                }));
    }

    public void validateOtp(String phoneNum,String otpCode){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.validateOtp(phoneNum,otpCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RegisterModel>() {
                    @Override
                    public void onSuccess(@NonNull RegisterModel rmModel) {

                        otpResponseLiveData.setValue(rmModel);
                        Log.d("gnfgnfgnfgn", "onSuccess: ");


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(e.toString());
                    }
                }));
    }


    public void timerOtp(){
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                timerOtp.postValue(l/1000);
            }

            @Override
            public void onFinish() {
                timerOtpFinish.postValue("ارسال مجدد");
            }
        }.start();
    }

    public void registerUser(String phone,String code,String name,String lastName){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.registerUser(phone,code,name,lastName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RegisterModel>() {
                    @Override
                    public void onSuccess(@NonNull RegisterModel rmModel) {
                        registerUserLiveData.postValue(rmModel);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(e.toString());

                    }
                }));
    }
}

