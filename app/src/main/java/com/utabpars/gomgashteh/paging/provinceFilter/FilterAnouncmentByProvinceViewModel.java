package com.utabpars.gomgashteh.paging.provinceFilter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.paging.ItemDataSource;

public class FilterAnouncmentByProvinceViewModel extends ViewModel {
    public LiveData<PagedList<AnoncmentModel.Detile>> listLiveData;
    LiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> dataSourceLiveData;
    ProvinceFilterDataSourceFactory provinceFilterDataSourceFactory;
    public FilterAnouncmentByProvinceViewModel() {
        getProvinceFilter();
    }

    public void getProvinceFilter(){
        provinceFilterDataSourceFactory=new ProvinceFilterDataSourceFactory();
        dataSourceLiveData=provinceFilterDataSourceFactory.getMutableLiveData();
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGESIZE)
                .build();

        listLiveData=new LivePagedListBuilder(provinceFilterDataSourceFactory,config).build();
    }


}
