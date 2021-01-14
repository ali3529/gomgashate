package com.utabpars.gomgashteh.category.attrebute;

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

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentAttrebuteBinding;

import java.util.ArrayList;
import java.util.List;


public class FragmentAttrebute extends Fragment {
    FragmentAttrebuteBinding binding;
    RecyclerView recyclerView;
    SpinnerAdaptor spinnerAdaptor;
    AttrebuteViewModel viewModel;
    List<String> attrList=new ArrayList<>();
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_attrebute,container,false);
        viewModel=new ViewModelProvider(this).get(AttrebuteViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        initVews();
        return binding.getRoot();
    }

    private void initVews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id=getArguments().getString("id");
        String type=getArguments().getString("type");
        viewModel.getAttrebute(id,type);
        Log.d("hg,mhg,gh,", "onViewCreated: "+id);
        Log.d("hg,mhg,gh,", "onViewCreated: "+type);

        viewModel.spinnerModelMutableLiveData.observe(getViewLifecycleOwner(),t ->{
            spinnerAdaptor=new SpinnerAdaptor(t,spiinerCallback);
            recyclerView.setAdapter(spinnerAdaptor);
        });


//        List<SpinnerModel.AttrebuteData> spiModels=new ArrayList<>();
//        List<String> list=new ArrayList<>();
//        list.add("قرمز-12");
//        list.add("ابی-62");
//        list.add("سبز-553");
//
//        List<String> list2=new ArrayList<>();
//        list2.add("قرمز-500");
//        list2.add("ابی-501");
//        list2.add("سبز-502");
//
//        List<String> list3=new ArrayList<>();
//        list3.add("قرمز-600");
//        list3.add("ابی-601");
//        list3.add("سبز-602");
//
//        spiModels.add(new SpinnerModel.AttrebuteData("رنگ", "1", list));
//        spiModels.add(new SpinnerModel.AttrebuteData("شکسدگی", "2", list2));
//        spiModels.add(new SpinnerModel.AttrebuteData("سایز", "3", list3));
//        spinnerModel.setAttrebuteData(spiModels);
//        spinnerAdaptor=new SpinnerAdaptor(spinnerModel,spiinerCallback);
//        recyclerView.setAdapter(spinnerAdaptor);

        binding.save.setOnClickListener( o->{
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("collaction_id",attrList.toString());
            editor.putString("type","");
            editor.putString("title","ویژگی");
            editor.apply();
            Navigation.findNavController(o).navigate(R.id.action_fragmentAttrebute_to_add);
        });
    }

    SpiinerCallback spiinerCallback=new SpiinerCallback() {
        @Override
        public void SpinnerItemCallBack(String id, String value) {

                String[] strings=value.split("@");
                Log.d("dvsdvdsv", "onBindViewHolder: "+strings[0].toString());
                Log.d("dvsdvdsv", "onBindViewHolder: "+strings[1].toString());

            attrList.add(id);
            attrList.add(strings[0]);
            Log.d("uuiytuyt", "onValueClicked: id      "+id);
            Log.d("uuiytuyt", "onValueClicked: value     "+strings[0]);
            Log.d("uuiytuyt", "onValueClicked: arry     "+attrList.toString());


        }
    };
}