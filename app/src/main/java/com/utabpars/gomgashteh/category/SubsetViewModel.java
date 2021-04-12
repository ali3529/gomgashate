package com.utabpars.gomgashteh.category;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.database.categoryDatabase.Attrebiute;
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryDataBase;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset2;

import java.util.List;

import hu.akarnokd.rxjava3.bridge.RxJavaBridge;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SubsetViewModel extends AndroidViewModel {
    MutableLiveData<SubSetModel> subsetMutableLiveData=new MutableLiveData<>();
    MutableLiveData<Boolean> emptySubSet=new MutableLiveData<>();
    MutableLiveData<SubSetModel> attributeMutableLiveData=new MutableLiveData<>();
    CategoryDataBase db;
    static SubSetCallBack subSetCallBack;
    SharedPreferences sharedPreferences;

    public SubsetViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        db =CategoryDataBase.getInstance(getApplication().getApplicationContext());
    }



    public void getSubsetFromDb(String collection_id){
        if (collection_id!=null) {
            Log.d("sdbvsdbsdbb", "getSubset2FromDb: id---" + collection_id);
            db = CategoryDataBase.getInstance(getApplication().getApplicationContext());
            getSubset(collection_id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                                Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation: " + result.get(0).getName());
                                if (!result.isEmpty()) {
                                    Log.d("dsvdfhnfgn", "getSubsetFromDb: subsset not found   if");
                                    subSetCallBack.onSubsetCallback(result);
                                } else {
                                    Log.d("dsvdfhnfgn", "getSubsetFromDb:  else   ");
                                }
                            },
                            error -> {
                                getAttrebiuteFromDb(collection_id);
                                Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation: " + error.toString());
                            });
        }
    }

    private void getAttrebiuteFromDb(String collection_id){
        getAttrebiute(collection_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                            Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation: "+result.get(0).getName());
                            if (!result.isEmpty()){
                                Log.d("dsvdfhnfgngg", "getSubsetFromDb: attrebiute not found   if");
                                if (detectType()){
                                    subSetCallBack.onAttributeCallback(result);
                                    Log.d("gnyjuyjygj", "getAttrebiuteFromDb:   if--" );
                                }else {
                                    subSetCallBack.emptyCallback(true);
                                    Log.d("gnyjuyjygj", "getAttrebiuteFromDb:   else--" );
                                }


                            }else {
                                Log.d("dsvdfhnfgngg", "getSubsetFromDb:  else   ");
                            }
                        },
                        error->{

                            Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation emty: "+error.toString());
                            subSetCallBack.emptyCallback(true);
                        });
    }

    public void getSubset2FromDb(String subset_id){
        db =CategoryDataBase.getInstance(getApplication().getApplicationContext());
        getSubset2(subset_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                            Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation: "+result.get(0).getName());
                            if (!result.isEmpty()){
                                Log.d("dsvdfhnfgn", "getSubsetFromDb: subsset not found   if");
                                subSetCallBack.onSubset2Callback(result);
                            }else {
                                Log.d("dsvdfhnfgn", "getSubsetFromDb:  else   ");
                            }
                        },
                        error->{
                            getAttrebiuteSubset2FromDb(subset_id);
                            Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation: "+error.toString());
                        });
    }

    private void getAttrebiuteSubset2FromDb(String subset_id){
        db =CategoryDataBase.getInstance(getApplication().getApplicationContext());
        getAttrebiuteSubset2(subset_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                            Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation: "+result.get(0).getName());
                            if (!result.isEmpty()){
                                Log.d("dsvdfhnfgnn", "getSubsetFromDb: attrebiute not found   if");
                                subSetCallBack.onAttributeCallback(result);
                            }else {
                                Log.d("dsvdfhnfgnn", "getSubsetFromDb:  else   ");
                            }
                        },
                        error->{

                            Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation emty: "+error.toString());
                            subSetCallBack.emptyCallback(true);
                        });
    }


    //get subset1
   private Flowable<List<Subset>> getSubset(String collection_id){
       return db.categoryDao().getSubset(collection_id).as(RxJavaBridge.toV3Flowable());
    }

    private Flowable<List<Attrebiute>> getAttrebiute(String collection_id){
        return db.categoryDao().getAttrebiute(collection_id).as(RxJavaBridge.toV3Flowable());
    }
    //get subset2
    private Flowable<List<Subset2>> getSubset2(String subset_id){
        return db.categoryDao().getSubset2(subset_id).as(RxJavaBridge.toV3Flowable());
    }

    private Flowable<List<Attrebiute>> getAttrebiuteSubset2(String subset_id){
        return db.categoryDao().getAttrebiuteSubset2(subset_id).as(RxJavaBridge.toV3Flowable());
    }

    public void getCallBack(SubSetCallBack subSetCallBack){
        this.subSetCallBack=subSetCallBack;
    }

    public Flowable<List<Subset>> getSubsetToRecyclerView(String subset_id){
        return getSubset(subset_id);
    }

    public boolean detectType(){
       sharedPreferences=getApplication().getSharedPreferences("from_add", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("from_add",false);
    }
}
