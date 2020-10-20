package com.utabpars.gomgashteh.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.utabpars.gomgashteh.model.AnoncmentModel;

public class AnnouncementViewModel extends ViewModel {
    public LiveData<PagedList<AnoncmentModel.Detile>> listLiveData;
    LiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> dataSourceLiveData;


    public  AnnouncementViewModel(){
        ItemDataSourceFactory itemDataSourceFactory=new ItemDataSourceFactory();
        dataSourceLiveData=itemDataSourceFactory.getAnoncmentMutableLiveData();
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGESIZE)
                .build();

        listLiveData=new LivePagedListBuilder(itemDataSourceFactory,config).build();
    }

}
