package com.utabpars.gomgashteh.paging.filterpaging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.utabpars.gomgashteh.model.AnoncmentModel;

public class FilterItemDataSourcsFactory extends DataSource.Factory<Integer, AnoncmentModel.Detile> {
    private MutableLiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> filterAnoncmentMutableLiveData=new MutableLiveData<>();
    @NonNull
    @Override
    public DataSource<Integer, AnoncmentModel.Detile> create() {
        FilterItemDataSource itemDataSource=new FilterItemDataSource();
        filterAnoncmentMutableLiveData.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> getAnoncmentMutableLiveData() {
        return filterAnoncmentMutableLiveData;
    }
    }
