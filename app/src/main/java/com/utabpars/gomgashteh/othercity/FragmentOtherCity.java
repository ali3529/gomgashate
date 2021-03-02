package com.utabpars.gomgashteh.othercity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.adaptor.CityAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.database.categoryDatabase.Category;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.databinding.FragmentOtherCityBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.interfaces.ItemSelectedCallback;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

;

public class

FragmentOtherCity extends Fragment {
    FragmentOtherCityBinding binding;
    RecyclerView recyclerView;
    OtherCityAdaptor otherCityAdaptor;
    String navigate;
    OtherCityViewModel viewModel;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_other_city,container,false);
        viewModel=new ViewModelProvider(this).get(OtherCityViewModel.class);
        initViews();
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String province_id=getArguments().getString("province");
        String province_name=getArguments().getString("province_name");
         navigate=getArguments().getString("navigate");

        viewModel.getCityFromDb(province_id);
        viewModel.listMutableLiveData.observe(getViewLifecycleOwner(),citys->{
            otherCityAdaptor.setData(citys,itemCitySelected);
            recyclerView.setAdapter(otherCityAdaptor);
            Log.d("sdvdsvdsv", "onViewCreated: ");
        });

    }

    private void initViews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        otherCityAdaptor=new OtherCityAdaptor();

    }

    OtherCityAdaptor.ItemCitySelected itemCitySelected=new OtherCityAdaptor.ItemCitySelected() {
        @Override
        public void getSelectedItem(View view, City city, int position, boolean is_checked) {
            city.setSelected_otherCity(is_checked);
            viewModel.updateSelectetOtherCity(city);

            viewModel.setSelectedProvince(city.getProvince_id(),is_checked);
            Log.d("sdvdsvdsv", "getSelectedItem: ");
        }
    };

}