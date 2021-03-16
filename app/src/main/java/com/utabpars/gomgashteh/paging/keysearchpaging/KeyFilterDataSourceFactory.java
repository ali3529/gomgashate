package com.utabpars.gomgashteh.paging.keysearchpaging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.model.AnoncmentModel;

public class KeyFilterDataSourceFactory extends DataSource.Factory<Integer, AnoncmentModel.Detile> {
    String city;
    String type;
    String key_search;
    KeyFilterDataSource.EmtySearchResult emtySearchResult;
    private MutableLiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> getKeyFilterAnoncmentMutableLiveData=new MutableLiveData<>();



    @NonNull
    @Override
    public DataSource<Integer, AnoncmentModel.Detile> create() {
        KeyFilterDataSource keyitemDataSource=new KeyFilterDataSource(city,type,key_search,emtySearchResult);
        getKeyFilterAnoncmentMutableLiveData.postValue(keyitemDataSource);
        return keyitemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> getKeyFilterAnoncmentMutableLiveData() {
        return getKeyFilterAnoncmentMutableLiveData;
    }
}
