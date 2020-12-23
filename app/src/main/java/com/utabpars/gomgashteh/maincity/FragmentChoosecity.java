package com.utabpars.gomgashteh.maincity;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentChoosecityBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentChoosecity extends Fragment {

    FragmentChoosecityBinding binding;
    FragmentMainCity fragmentMainCity=new FragmentMainCity();
   static SharedPreferences sharedPreferences;
   static SharedPreferences.Editor editor;
    Gson gson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_choosecity,container,false);
        sharedPreferences=getActivity().getSharedPreferences("main_city", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setFrag(this);

    }

    public void ttt()  {
        try {
            gson=new Gson();
            String s=gson.toJson(list());
            editor.putString("main_city",s);
            editor.commit();
            popback();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "انتخاب حداقل یک شهر الزامی است", Toast.LENGTH_SHORT).show();
            Log.d("bhvhvhvg", "ttt: "+e.toString());
        }
    }

   public List<String> list(){
        List<String> id=new ArrayList<>();
       for (int i = 0; i < fragmentMainCity.ggggg().size(); i++) {
           id.add(fragmentMainCity.ggggg().get(i).getId());
       }
       return id;
   }

   public void popback(){
       Navigation.findNavController(getView()).popBackStack();
   }



}