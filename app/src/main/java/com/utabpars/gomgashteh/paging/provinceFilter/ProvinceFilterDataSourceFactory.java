package com.utabpars.gomgashteh.paging.provinceFilter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.model.AnoncmentModel;

public class ProvinceFilterDataSourceFactory extends DataSource.Factory<Integer, AnoncmentModel.Detile> {
    MutableLiveData<PageKeyedDataSource<Integer,AnoncmentModel.Detile>> mutableLiveData=new MutableLiveData<>();
    @NonNull
    @Override
    public DataSource<Integer, AnoncmentModel.Detile> create() {
        ProvinceFilterDataSource provinceFilterDataSource=new ProvinceFilterDataSource();
        mutableLiveData.postValue(provinceFilterDataSource);
        return provinceFilterDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> getMutableLiveData() {
        return mutableLiveData;
    }
}
