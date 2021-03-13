package com.utabpars.gomgashteh.maincity;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.database.citydatabase.Province;
import com.utabpars.gomgashteh.databinding.FragmentChoosecityBinding;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentChoosecity extends Fragment {

    FragmentChoosecityBinding binding;
   static SharedPreferences sharedPreferences;
   static SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    SelectetdCityAdaptor adaptor;
    CityDatabase db;
    public static MutableLiveData<City> booleanMutableLiveData=new MutableLiveData<>();
    List<City> cityList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_choosecity,container,false);
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        sharedPreferences=getActivity().getSharedPreferences("main_city", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        initViews();
        Log.d("fnfgngfnfgn", "onCreateView: ");
        return binding.getRoot();

    }

    private void initViews() {
        recyclerView=binding.showSelectedRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setFrag(this);
         db=CityDatabase.getInstance(getContext());
        db.cityDao().getCitySelected().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(selectetd_city->{
                    adaptor = new SelectetdCityAdaptor(selectetd_city,deleteSelectedCityCallback);
                    recyclerView.setAdapter(adaptor);
                    cityList=selectetd_city;
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
            city.setSelected_city(false);
            db.cityDao().selectedCity(city);
          //  db.cityDao().selectedProvinceCity(city.getProvince_id(),false);
            booleanMutableLiveData.setValue(city);
            Log.d("fbdfbdfbfnbnb", "getSelectedItem:");
            for (City c:cityList) {
                Log.d("fbdfbdfbfnbnb", "getSelectedItem: for");
                if (c.getProvince_id().equals(city.getProvince_id()) && c.isSelected_city()){
                    db.cityDao().selectedProvinceCity(city.getProvince_id(),true);
                    Log.d("fbdfbdfbfnbnb", "getSelectedItem: for if");
                    break;
                }else {
                    db.cityDao().selectedProvinceCity(city.getProvince_id(),false);
                    //provinceMutableLiveData.setValue(c);
                    Log.d("fbdfbdfbfnbnb", "getSelectedItem: for else");
                }

            }

        }

    };
}