package com.utabpars.gomgashteh.paging.topfilterpaging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.model.AnoncmentModel;
public class TopFilterDataSourceFactory extends DataSource.Factory<Integer, AnoncmentModel.Detile> {
    private MutableLiveData<PageKeyedDataSource<Integer,AnoncmentModel.Detile>> pageKeyedDataSourceMutableLiveData=new MutableLiveData<>();
    @NonNull
    @Override
    public DataSource<Integer, AnoncmentModel.Detile> create() {
        TopFilterDataSource topFilterDataSource=new TopFilterDataSource();
        pageKeyedDataSourceMutableLiveData.postValue(topFilterDataSource);
        return topFilterDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> getPageKeyedDataSourceMutableLiveData() {
        return pageKeyedDataSourceMutableLiveData;
    }
}
