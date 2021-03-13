package com.utabpars.gomgashteh.othercity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.databinding.FragmentHostOtherCityBinding;
import com.utabpars.gomgashteh.maincity.SelectetdCityAdaptor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentHostOtherCity extends Fragment {
    FragmentHostOtherCityBinding binding;
    SelectetdCityAdaptor adaptor;
    RecyclerView recyclerView;
    CityDatabase db;
    public static MutableLiveData<City> cityMutableLiveData=new MutableLiveData<>();
    List<City> cityList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_host_other_city,container,false);
        binding.setFrag(this);
        db=CityDatabase.getInstance(getContext());
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        recyclerView=binding.showSelectedRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String navigate = getArguments().getString("navigate");
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("other_city_navigate", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("navigate",navigate);
        editor.putString("navigate",navigate);
        editor.apply();

        db.cityDao().getOtherCitySelected().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(selectetOtherCity->{
                    adaptor=new SelectetdCityAdaptor(selectetOtherCity,deleteSelectedCityCallback);
                    recyclerView.setAdapter(adaptor);
                    cityList=selectetOtherCity;
                });
    }

    public void ttt()  {
        popback();
    }



    public void popback(){
        Navigation.findNavController(getView()).popBackStack();
    }
    SelectetdCityAdaptor.deleteSelectedCityCallback deleteSelectedCityCallback=new SelectetdCityAdaptor.deleteSelectedCityCallback() {
        @Override
        public void DeleteSelectetdCity(City city) {
            city.setSelected_otherCity(false);
            db.cityDao().selectedCity(city);

            cityMutableLiveData.setValue(city);
            for (City c:cityList) {
                Log.d("jfgjtti", "getSelectedItem: for");
                if (c.getProvince_id().equals(city.getProvince_id()) && c.isSelected_otherCity()){
                    db.cityDao().selectedProvinceOtherCity(city.getProvince_id(),true);
                    Log.d("jfgjtti", "getSelectedItem: for if");
                    break;
                }else {
                    db.cityDao().selectedProvinceOtherCity(city.getProvince_id(),false);

                    Log.d("jfgjtti", "getSelectedItem: for else");
                }

            }

        }

    };
}
