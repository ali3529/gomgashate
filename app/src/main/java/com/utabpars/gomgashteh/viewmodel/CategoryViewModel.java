package com.utabpars.gomgashteh.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.database.categoryDatabase.Category;
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryDataBase;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.util.List;

import hu.akarnokd.rxjava3.bridge.RxJavaBridge;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryViewModel extends AndroidViewModel {
     MutableLiveData<CategoryModel> categoryModelMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<List<Category>> listMutableLiveDataFromDb=new MutableLiveData<>();
    CompositeDisposable compositeDisposable;
    CategoryDataBase db;

    public CategoryViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
       // getCategory();
        getcategoryFromDb();
    }

    public void getCategory(){
        ApiInterface apiInterface= ApiClient.getApiClient();
         compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getcategories()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
            @Override
            public void onSuccess(@NonNull CategoryModel categoryModel) {
                if (categoryModel.getResponse().equals("1")){
                    categoryModelMutableLiveData.postValue(categoryModel);

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("dsfsd", "onError: "+e.toString());
            }
        }));
    }

    public MutableLiveData<CategoryModel> categoriesMutableLiveData(){
        return categoryModelMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
 // db.close();
    }

    public void getcategoryFromDb(){
        db=CategoryDataBase.getInstance(getApplication().getApplicationContext());
        getlist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                            Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation: "+result.get(0).getName());
                            listMutableLiveDataFromDb.setValue(result);
                        },
                        error->{ Log.d("dfbngfmghm", "lastAnnouncmentAboveBtNavigation: "+error.toString());});


    }

    public Flowable<List<Category>> getlist(){
        return db.categoryDao().getCategoryRx().as(RxJavaBridge.toV3Flowable());
    }
}
