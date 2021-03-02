package com.utabpars.gomgashteh.category.attrebute;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.utabpars.gomgashteh.category.SubSetCallBack;
import com.utabpars.gomgashteh.category.SubSetModel;
import com.utabpars.gomgashteh.database.categoryDatabase.Attrebiute;
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryDataBase;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset2;

import java.util.List;

import hu.akarnokd.rxjava3.bridge.RxJavaBridge;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Subset2ViewModel extends AndroidViewModel {
    CategoryDataBase db;
    public MutableLiveData<List<Subset2> > subse2tMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<Boolean> emptySubSet=new MutableLiveData<>();
    public MutableLiveData<List<Attrebiute>> attributeMutableLiveData=new MutableLiveData<>();
  //  static SubSetCallBack subSetCallBack;

    public Subset2ViewModel(@NonNull Application application)
    {
        super(application);
        db = CategoryDataBase.getInstance(getApplication().getApplicationContext());
    }

    public void getSubset2FromDb(String subset2_id) {

            Log.d("sdbvsdbsdbb", "getSubset2FromDb: id---" + subset2_id);
            getSubset2(subset2_id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                                Log.d("dfbngfmghmbb", "lastAnnouncmentAboveBtNavigation: out " + result.get(0).getName());
                                if (!result.isEmpty()) {
                                    Log.d("dfbngfmghmbb", "getSubsetFromDb: subsset not found   if");
                                    //subSetCallBack.onSubset2Callback(result);
                                    subse2tMutableLiveData.setValue(result);
                                } else {
                                    Log.d("dfbngfmghmbb", "getSubsetFromDb:  else   ");
                                }
                            },
                            error -> {
                                getAttrebiuteSubset2FromDb(subset2_id);
                                Log.d("dfbngfmghmbb", "lastAnnouncmentAboveBtNavigation: getattr----" + error.toString());
                            });

    }


    private void getAttrebiuteSubset2FromDb(String collection_id){
        db =CategoryDataBase.getInstance(getApplication().getApplicationContext());
        getAttrebiuteSubset2(collection_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                            Log.d("dfbngfmghmbb", "lastAnnouncmentAboveBtNavigation: "+result.get(0).getName());
                            if (!result.isEmpty()){
                                Log.d("dfbngfmghmbb", "getSubsetFromDb: attrebiute not found   if");
                               // subSetCallBack.onAttributeCallback(result);
                                attributeMutableLiveData.setValue(result);
                            }else {
                                Log.d("dfbngfmghmbb", "getSubsetFromDb:  else   ");
                            }
                        },
                        error->{

                            Log.d("dfbngfmghmbb", "lastAnnouncmentAboveBtNavigation emty--: "+error.toString());
                           // subSetCallBack.emptyCallback(true);
                            emptySubSet.setValue(true);
                        });
    }

    //get subset2
    private Flowable<List<Subset2>> getSubset2(String subset_id){
        return db.categoryDao().getSubset2(subset_id).as(RxJavaBridge.toV3Flowable());
    }

    private Flowable<List<Attrebiute>> getAttrebiuteSubset2(String subset_id){
        return db.categoryDao().getAttrebiuteSubset2last(subset_id).as(RxJavaBridge.toV3Flowable());
    }

//    public void getCallBack(SubSetCallBack subSetCallBack){
//        this.subSetCallBack=subSetCallBack;
//    }

    public Flowable<List<Subset2>> getSubsetToRecyclerView(String subset_id){
        return getSubset2(subset_id);
    }

}
