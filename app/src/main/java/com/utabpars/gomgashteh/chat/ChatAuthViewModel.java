package com.utabpars.gomgashteh.chat;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.interfaces.ChatCallBack;


import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ChatAuthViewModel extends ViewModel {
    ChatCallBack chatCallBack;
   public MutableLiveData<StatusModel> firstChatStatus=new MutableLiveData<>();
    public ChatAuthViewModel() {

    }

    public void ChatInterface(ChatCallBack chatCallBack){
        this.chatCallBack=chatCallBack;
    }

    public void chatValidate( String user_id,String announcement_id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getChatStatus(user_id,announcement_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<ChatStatusModel>() {
            @Override
            public void onSuccess(@NonNull ChatStatusModel chatStatusModel) {

                chatCallBack.ChatListener(chatStatusModel);
                Log.d("dsfsdf", "chatValidate: chat"+chatStatusModel.getMassage());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("dsfsdf", "chatValidate: chat"+e.toString());
            }
        }));

    }


    public void sendFirstMassage(List<MultipartBody.Part> parts, Map<String, RequestBody> requestBody){
        ApiInterface apiInterface = ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.sendFirstAnnouncementMassage(parts,requestBody)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<StatusModel>() {
            @Override
            public void onSuccess(@NonNull StatusModel rmModel) {
                firstChatStatus.setValue(rmModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("dsfsdfsdf", "onError: "+e.toString());
            }
        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
