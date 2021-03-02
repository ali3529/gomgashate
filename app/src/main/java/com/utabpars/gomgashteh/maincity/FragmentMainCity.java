package com.utabpars.gomgashteh.maincity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.database.citydatabase.Province;
import com.utabpars.gomgashteh.databinding.FragmentMainCityBinding;
import com.utabpars.gomgashteh.interfaces.ItemSelectedCallback;
import com.utabpars.gomgashteh.model.CategoryModel;
import com.utabpars.gomgashteh.viewmodel.MainCityViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FragmentMainCity extends Fragment
        implements MainCityAdaptor.ItemCitySelected
{
    FragmentMainCityBinding binding;
    MainCityViewModel mainCityViewModel;
    MainCityAdaptor categoryAdaptor;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    List<City> cityList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_main_city,container,false);
        mainCityViewModel=new ViewModelProvider(this).get(MainCityViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("main_city", Context.MODE_PRIVATE);
        Log.d("fnfgngfnfgn", "onCreateView: ");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=binding.mainRe4cyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        String province_name=getArguments().getString("province_name");
        String province_id=getArguments().getString("province");
        categoryAdaptor=new MainCityAdaptor();

        mainCityViewModel.getProvinceFromDB(province_id);
        mainCityViewModel.provinceMutableLiveData.observe(getViewLifecycleOwner(), city ->{

            categoryAdaptor.setData(city,this::getSelectedItem);
            recyclerView.setAdapter(categoryAdaptor);
            cityList=city;

        });

//        FragmentChoosecity.booleanMutableLiveData.observe(getViewLifecycleOwner(),t->{
//            Log.d("fdbdfbdfb", "onViewCreated: ");
//            //categoryAdaptor.notifyDataSetChanged();
//            mainCityViewModel.getProvinceFromDB(province_id);
//        });


    }

    @Override
    public void getSelectedItem(View view, City city, int position, boolean is_checked) {

        Log.d("fbdfbdfbfb", "getSelectedItem: bbefooor ");

            city.setSelected_city(is_checked);
            mainCityViewModel.updateSelectedCity(city);


//        CityDatabase cityDatabase=CityDatabase.getInstance(getContext());
//        Flowable<List<Province>> provinces= cityDatabase.cityDao().getProvnce();

        for (City c:cityList) {
            if (c.isSelected_city()){
                mainCityViewModel.setSelectedProvince(city.getProvince_id(),true);
                Log.d("fbdfbdfbfb", "getSelectedItem: for if");
                break;
            }else {
                mainCityViewModel.setSelectedProvince(city.getProvince_id(),false);
                Log.d("fbdfbdfbfb", "getSelectedItem: for else");
            }

        }


    }

}