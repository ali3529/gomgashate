package com.utabpars.gomgashteh.paging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.airbnb.lottie.LottieAnimationView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncementBinding;
import com.utabpars.gomgashteh.fragment.FragmentAnnouncement;
import com.utabpars.gomgashteh.model.AnoncmentModel;

public class AnnouncementViewModel extends ViewModel {
    public LiveData<PagedList<AnoncmentModel.Detile>> listLiveData;
    LiveData<PageKeyedDataSource<Integer, AnoncmentModel.Detile>> dataSourceLiveData;
    ItemDataSourceFactory itemDataSourceFactory;

    FragmentAnnouncementBinding binding;

    public  AnnouncementViewModel(){
      getAnnouncement();

    }


    public void getAnnouncement(){
      itemDataSourceFactory=new ItemDataSourceFactory();
        dataSourceLiveData=itemDataSourceFactory.getAnoncmentMutableLiveData();
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGESIZE)
                .build();

        listLiveData=new LivePagedListBuilder(itemDataSourceFactory,config).build();
    }

    public void refresh(){
        itemDataSourceFactory.getAnoncmentMutableLiveData().getValue().invalidate();
        binding.setRefresh(true);

    }

    public void getProg(FragmentAnnouncementBinding binding){
        this.binding=binding;
    }

    public void swipeRefresh(){
        try {
            itemDataSourceFactory.getAnoncmentMutableLiveData().getValue().invalidate();
        }catch (Exception e){
            Log.d("crashswipe", "swipeRefresh: "+e.toString());
        }



    }
}
