package com.utabpars.gomgashteh.maincity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
import com.utabpars.gomgashteh.databinding.FragmentMainCityBinding;
import com.utabpars.gomgashteh.interfaces.ItemSelectedCallback;
import com.utabpars.gomgashteh.model.CategoryModel;
import com.utabpars.gomgashteh.viewmodel.MainCityViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FragmentMainCity extends Fragment implements ItemSelectedCallback {
    FragmentMainCityBinding binding;
    MainCityViewModel mainCityViewModel;
    MainCityAdaptor categoryAdaptor;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
   static List<CategoryModel.ListData> listData=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_main_city,container,false);
        mainCityViewModel=new ViewModelProvider(this).get(MainCityViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("main_city", Context.MODE_PRIVATE);
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
        mainCityViewModel.getMainCity(province_id);


        mainCityViewModel.categoryModelMutableLiveData.observe(getViewLifecycleOwner(), citeis ->{

                if (getMainCity()==null){
                    for (int i = 0; i < citeis.getListData().size(); i++) {
                        for (int j = 0; j < listData.size(); j++) {
                            if (citeis.getListData().get(i).getId().equals(listData.get(j).getId())){
                                citeis.getListData().get(i).setSelected(true);
                            }
                        }
                    }
                }else {
                    for (int i = 0; i < citeis.getListData().size(); i++) {
                        for (int j = 0; j < getMainCity().size(); j++) {
                            if (citeis.getListData().get(i).getId().equals(getMainCity().get(j))) {
                                citeis.getListData().get(i).setSelected(true);
                            }
                        }
                    }
                }


            categoryAdaptor=new MainCityAdaptor(citeis.getListData(),this::getSelectedItem);
            recyclerView.setAdapter(categoryAdaptor);
        });


    }

    @Override
    public void getSelectedItem(View view, CategoryModel.ListData categoryModel, int position, boolean is_checked) {

        if (is_checked){
            listData.add(categoryModel);
            for (int i = 0; i < listData.size(); i++) {
                Log.d("htgdfgvdfg", "getSelectedItem: "+listData.get(i).getId());
                Log.d("htgdfgvdfg", "getSelectedItem: "+listData.get(i).getCategoryName());
            }

        }
        else {
            if (listData.size()!=0){
                for (int i = 0; i < listData.size(); i++) {
                    if (listData.get(i).getId().equals(categoryModel.getId())) {
                        Log.d("htgdfgvdfg", "getSelectedItem: " + listData.get(i).getId());
                        Log.d("htgdfgvdfg", "getSelectedItem: " + listData.get(i).getCategoryName());
                        listData.remove(i);
                    }

                }
            }
        }

    }

    public List<CategoryModel.ListData>  ggggg() {
        Log.d("thrfhbdr", "ggggg: "+listData.get(0).getId());

        return listData;
    }

    public List<String> getMainCity(){
        Gson gson=new Gson();

        String s=sharedPreferences.getString("main_city",null);

        Type type=new TypeToken<List<String>>(){

        }.getType();
        List<String>  j=gson.fromJson(s,type);


        return j;
    }
}