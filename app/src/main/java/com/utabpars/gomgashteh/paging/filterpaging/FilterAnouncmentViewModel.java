package com.utabpars.gomgashteh.paging.filterpaging;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.utabpars.gomgashteh.databinding.FragmentAnnouncCollectionBinding;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.paging.ItemDataSource;
import com.utabpars.gomgashteh.paging.ItemDataSourceFactory;

public class FilterAnouncmentViewModel extends ViewModel {
    public LiveData<PagedList<AnoncmentModel.Detile>> listLiveData;
    LiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> dataSourceLiveData;
    FilterItemDataSourcsFactory itemDataSourceFactory;

    public  FilterAnouncmentViewModel(){
        getAnnouncement();

    }


    public void getAnnouncement(){
        itemDataSourceFactory=new FilterItemDataSourcsFactory();
        dataSourceLiveData=itemDataSourceFactory.getAnoncmentMutableLiveData();
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGESIZE)
                .build();

        listLiveData=new LivePagedListBuilder(itemDataSourceFactory,config).build();
    }

    public void refresh(){
        itemDataSourceFactory.getAnoncmentMutableLiveData().getValue().invalidate();
        Log.d("kjlj", "refresh: "+"bsdhfsdf");

    }
}
