package com.utabpars.gomgashteh.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.model.AnoncmentModel;

public class ItemDataSourceFactory extends DataSource.Factory<Integer, AnoncmentModel.Detile> {

private MutableLiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> anoncmentMutableLiveData=new MutableLiveData<>();
    @NonNull
    @Override
    public DataSource<Integer, AnoncmentModel.Detile> create() {
        ItemDataSource itemDataSource=new ItemDataSource();
anoncmentMutableLiveData.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> getAnoncmentMutableLiveData(){
        return anoncmentMutableLiveData;
    }

}
