package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentRecomendBinding;
import com.utabpars.gomgashteh.model.REcomendModel;
import com.utabpars.gomgashteh.model.RmModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentRecomend extends Fragment {

    FragmentRecomendBinding binding;
    SharedPreferences user_status;
    String user_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_recomend,container,false);
        user_status=getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_id=user_status.getString("user_id","0");
        binding.send.setOnClickListener(o->{
            if (binding.recomenrText.getText().toString().length()!=0){
                sendRecommendMassage(user_id,binding.recomenrText.getText().toString());
            }else
                Toast.makeText(getContext(), "لطفا پیشنهاد خود را بنویسید", Toast.LENGTH_SHORT).show();
        });

        binding.backArrow.setOnClickListener(o->{
            Navigation.findNavController(o).navigateUp();
        });
    }



    public void sendRecommendMassage(String user_id,String massage){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.sendRecommend(user_id,massage)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<REcomendModel>() {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull REcomendModel rmModel) {
                Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
                try {
                    Navigation.findNavController(getView()).navigateUp();
                }catch (Exception e){

                }

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d("sacascascasc", "onError: "+e.toString());
            }
        }));
    }
}