package com.utabpars.gomgashteh.managerAnnouncement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentDeleteBottonSheetBinding;
import com.utabpars.gomgashteh.model.SaveAnnouncementModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FragmentDeleteBottonSheet extends BottomSheetDialogFragment {
    FragmentDeleteBottonSheetBinding binding;
    MutableLiveData<Boolean> isDelete=new MutableLiveData<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_delete_botton_sheet,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.success.setOnClickListener(o ->{
            deleteAnnouncment(getTag(),"1");
        });
        binding.unsuccess.setOnClickListener(o ->{
            deleteAnnouncment(getTag(),"2");
        });
    }
    public void deleteAnnouncment(String id,String result){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.deleteAnnouncement(id,result)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<SaveAnnouncementModel>() {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull SaveAnnouncementModel saveAnnouncementModel) {
                if (saveAnnouncementModel.getResponse().equals("1")){
                    isDelete.setValue(true);
                }

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d("ascsacsac", "onError: "+e.toString());

            }
        }));
    }
}