package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentTestBinding;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class FragmentTest extends Fragment {
    FragmentTestBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_test,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        long currentTime =Calendar.getInstance().getTimeInMillis();

//
//        binding.date.setText(""+currentTime);

//        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
//
//// textView is the TextView view that should display it
//        binding.date.setText(currentDateTimeString);

        binding.time.setOnClickListener(o->{
            long currentTime =Calendar.getInstance().getTimeInMillis();
            long min=currentTime/1000;
            long minplus=1612338558+60;

            binding.date2.setText("min ; "+min);
            binding.date3.setText("minplus : "+minplus);



            if (min>minplus){
                Log.d("sddsvfnhgk", "onViewCreated: "+"false");
            }else {
                Log.d("sddsvfnhgk", "onViewCreated: "+"true");
            }
        });


    }
}