package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentCity2Binding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.model.CategoryModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentCity extends Fragment {
    FragmentCity2Binding binding;
    RecyclerView recyclerView;
    CategoryAdaptor categoryAdaptor;
    String province_id,province_name,navigation;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_city2,container,false);
        initviews();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigation=getArguments().getString("navigate");
         province_id =getArguments().getString("province");
         province_name =getArguments().getString("province_name");
        Log.d("afd", "onSuccess: "+ province_id);
        getProvinces(province_id);
    }


    private void getProvinces(String id){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.cities(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoryModel categoryModel) {
                        if (categoryModel.getResponse().equals("1")) {


                     categoryAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
                         @Override
                         public void getCategoryId(View view, String id, int position) {

                             //shared
                             SharedPreferences.Editor editor=sharedPreferences.edit();
                             editor.putString("province_id",province_id);
                             editor.putString("province_name",province_name);
                             editor.putString("city_id",String.valueOf(id));
                             editor.putString("city_name",categoryModel.getListData().get(position).getCategoryName());
                             editor.apply();
                             if (navigation.equals("city_add")){
                                 Navigation.findNavController(view).navigate(R.id.action_fragmentCity2_to_add);
                             }else if (navigation.equals("choose")){
                               Navigation.findNavController(view).navigate(R.id.action_fragmentCity2_to_announcement);
                               SharedPreferences preferences=getActivity().getSharedPreferences("cityName",Context.MODE_PRIVATE);
                               SharedPreferences.Editor editor1=preferences.edit();
                                 editor1.putString("city_name_announcment",province_name);
                                 editor1.putString("province_id",province_id);
                                 editor1.apply();
                             }


                         }
                     });
                     recyclerView.setAdapter(categoryAdaptor);
                            Log.d("afd", "onSuccess: ");
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("tfhgfb", "onSuccess: "+e.toString()+"6");
                    }
                }));
    }

    private void initviews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        sharedPreferences = getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
    }

}