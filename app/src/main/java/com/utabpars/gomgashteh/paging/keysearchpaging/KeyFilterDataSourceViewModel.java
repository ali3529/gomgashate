package com.utabpars.gomgashteh.paging.keysearchpaging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.paging.ItemDataSource;

public class KeyFilterDataSourceViewModel extends ViewModel {
    public LiveData<PagedList<AnoncmentModel.Detile>> listLiveData;
    LiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> dataSourceLiveData;
    KeyFilterDataSourceFactory itemDataSourceFactory;

    public  KeyFilterDataSourceViewModel(){
        getKeyFilterAnnouncement();

    }


    public void getKeyFilterAnnouncement(){
        itemDataSourceFactory=new KeyFilterDataSourceFactory();
        dataSourceLiveData=itemDataSourceFactory.getKeyFilterAnoncmentMutableLiveData();
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGESIZE)
                .build();

        listLiveData=new LivePagedListBuilder(itemDataSourceFactory,config).build();
    }

    public void refresh(){
        itemDataSourceFactory.getKeyFilterAnoncmentMutableLiveData().getValue().invalidate();

    }

    public void setKeyFilterData(String city, String type, String key_search, KeyFilterDataSource.EmtySearchResult emtySearchResult) {
        itemDataSourceFactory.city=city;
        itemDataSourceFactory.type=type;
        itemDataSourceFactory.key_search=key_search;
        itemDataSourceFactory.emtySearchResult=emtySearchResult;

    }

}
