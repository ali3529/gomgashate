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
import com.utabpars.gomgashteh.databinding.FragmentChoosecityBinding;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FragmentChoosecity extends Fragment {

    FragmentChoosecityBinding binding;
    FragmentMainCity fragmentMainCity=new FragmentMainCity();
   static SharedPreferences sharedPreferences;
   static SharedPreferences.Editor editor;
    Gson gson;
    RecyclerView recyclerView;
    SelectetdCityAdaptor adaptor;
    MutableLiveData< List<CategoryModel.ListData>> nameMutableLiveData=new MutableLiveData<>();
    List<String> name=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_choosecity,container,false);
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        sharedPreferences=getActivity().getSharedPreferences("main_city", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        initViews();
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

        FragmentMainCity.listDataMutableLiveData.observe(getViewLifecycleOwner(), t->{
            if (t.size()>0){
                Log.d("ffdvf", "onViewCreated: okkk"+t.get(0).getId());
                Log.d("ffdvf", "onViewCreated: okkk"+t.get(0).getCategoryName());
                Log.d("ffdvf", "onViewCreated: okkk"+t.size());
                nameMutableLiveData.setValue(t);

            }else {
                try {
                    adaptor.notifyDataSetChanged();
                }catch (Exception e){

                }

            }

        });
        try {
            //set city when open app
            if (getCityName()!=null){
                adaptor=new SelectetdCityAdaptor(null,getCityName());
                recyclerView.setAdapter(adaptor);
            }else {
                Log.d("gvnygjy", "onViewCreated: rrrrr");
            }
        }catch (Exception e){

        }


        nameMutableLiveData.observe(getViewLifecycleOwner(),t->{
            //edit save city name forshow when app close
            //not complete
            adaptor=new SelectetdCityAdaptor(t,null);
            recyclerView.setAdapter(adaptor);

        });
    }

    public void ttt()  {
        try {
            //citys_id_save
            gson=new Gson();
            String s=gson.toJson(list());
            editor.putString("main_city",s);
            editor.commit();

            //citys_name_save
            Gson gson=new Gson();
            String n=gson.toJson(name);
            editor.putString("name",n);
            editor.apply();

            popback();

        } catch (Exception e) {

            editor.clear();
            editor.commit();
            popback();
        }
    }

   public List<String> list(){
        List<String> id=new ArrayList<>();
       for (int i = 0; i < fragmentMainCity.ggggg().size(); i++) {
           id.add(fragmentMainCity.ggggg().get(i).getId());
           name.add(fragmentMainCity.ggggg().get(i).getCategoryName());
       }
       return id;
   }


   public void popback(){
       Navigation.findNavController(getView()).popBackStack();
   }

    public List<String> getCityName(){
        Gson gson=new Gson();
        SharedPreferences sharedPreferences;
            sharedPreferences=getActivity().getSharedPreferences("main_city", Context.MODE_PRIVATE);
            Log.d("fhfdgdfg", "onViewCreated: edit ee");


        String s=sharedPreferences.getString("name","w");
        Type type=new TypeToken<List<String>>(){

        }.getType();
        List<String>  j=gson.fromJson(s,type);

        return j;
    }

}