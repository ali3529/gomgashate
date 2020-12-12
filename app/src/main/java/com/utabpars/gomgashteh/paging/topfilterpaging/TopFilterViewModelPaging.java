package com.utabpars.gomgashteh.paging.topfilterpaging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.utabpars.gomgashteh.model.AnoncmentModel;

public class TopFilterViewModelPaging extends ViewModel {
    public LiveData<PagedList<AnoncmentModel.Detile>> listLiveData;
    private LiveData<PageKeyedDataSource<Integer,AnoncmentModel.Detile>> dataSourceLiveData;
    TopFilterDataSourceFactory topFilterDataSourceFactory;

    public TopFilterViewModelPaging() {

    }

    public void topFilterd(){
        topFilterDataSourceFactory=new TopFilterDataSourceFactory();
        dataSourceLiveData=topFilterDataSourceFactory.getPageKeyedDataSourceMutableLiveData();
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(5)
                .build();
        listLiveData=new LivePagedListBuilder(topFilterDataSourceFactory,config).build();
    }
}
