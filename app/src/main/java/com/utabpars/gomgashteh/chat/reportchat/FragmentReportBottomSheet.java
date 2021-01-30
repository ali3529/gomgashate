package com.utabpars.gomgashteh.chat.reportchat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.chat.StatusModel;
import com.utabpars.gomgashteh.databinding.FragmentReportBottomSheetBinding;
import com.utabpars.gomgashteh.model.RmModel;


import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentReportBottomSheet extends BottomSheetDialogFragment {
    FragmentReportBottomSheetBinding binding;
    MutableLiveData<List<String>> list=new MutableLiveData<>();
    RecyclerView recyclerView;
    ReportTextAdaptor adaptor;
    String blocker,blocked;
    String announce_id,user_id;
    public MutableLiveData<Boolean> reportResponsLiveData=new MutableLiveData<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_report_bottom_sheet,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=binding.recyclerviewReport;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.observe(getViewLifecycleOwner(), t ->{
            adaptor=new ReportTextAdaptor(t,reportClick);
            recyclerView.setAdapter(adaptor);
            Log.d("sadsadsad", "onchange: "+t.get(0));

        });

    }


    public void getList(List<String> lists){
     list.setValue(lists);
        Log.d("sadsadsad", "getList: "+lists.get(0));


    }

    public void getUsers(String blocker,String blocked){
        this.blocked=blocked;
        this.blocker=blocker;

    }
    public void getAnnounceid(String announceid,String user_id){
        this.announce_id=announceid;
        this.user_id=user_id;
        Log.d("sdfdsfdsf", "getAnnounceid: "+announceid);
        Log.d("sdfdsfdsf", "getAnnounceid: "+user_id);

    }
    ReportClick reportClick=new ReportClick() {
        @Override
        public void reportTextClick(String rText) {
          //  Toast.makeText(getContext(), rText, Toast.LENGTH_SHORT).show();
            if (getTag().equals("report_announcment")){
                reportAnnounce(announce_id,user_id,rText);

            }else {
                reportUser(blocker,blocked,rText);
            }

        }
    };

public void reportUser(String blocker,String blocked,String report_text){
    ApiInterface apiInterface= ApiClient.getApiClient();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    compositeDisposable.add(apiInterface.reportUser(blocker,blocked,report_text)
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeWith(new DisposableSingleObserver<ReportModel>() {
        @Override
        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ReportModel statusModel) {
            if (statusModel.getStatus().equals("1")){

                    Toast.makeText(getContext(), statusModel.getMassage(), Toast.LENGTH_SHORT).show();


                reportResponsLiveData.setValue(statusModel.isReport());
                Log.d("sdvdsvdsv", "onSuccess: ");
            }
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            Log.d("sdvdsvdsv", "onError: "+e.toString());
        }
    }));
}

public void reportAnnounce(String announce_id,String user_id,String text){
    ApiInterface apiInterface= ApiClient.getApiClient();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    compositeDisposable.add(apiInterface.reportAnnouncement(announce_id,user_id,text)
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeWith(new DisposableSingleObserver<RmModel>() {
        @Override
        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RmModel rmModel) {
            Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
            reportResponsLiveData.setValue(true);
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            Log.d("dvdsvsdvdsv", "onError: "+e.toString());
        }
    }));
}
}