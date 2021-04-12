package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.databinding.FragmentTestBinding;
import com.utabpars.gomgashteh.utils.PlateNumber;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import hu.akarnokd.rxjava3.bridge.RxJavaBridge;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentTest extends Fragment {
    FragmentTestBinding binding;
    EditText t1,t2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_test,container,false);
        // Inflate the layout for this fragment
//        t2=binding.pp.lNumber;
//        t1=binding.pp.fNumber;

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //PlateNumber.setType("7");

        binding.fff.setOnClickListener(t->{
//            for (String s:binding.rrrr.getMotoNumber()) {
//                Log.d("dfvdfvfv", "onViewCreated: "+s);
//            }
           // Log.d("dfvdfvfv", "onViewCreated: "+binding.rrrr.getCardNumber());

        });
//        String[] word=new String[]{"الف","ب","پ","ج","د","ژ","س","ص","ط","ق","ک","گ","ل","م","ن","و","ه","ی"};
//
//
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.item_default_power_spinner_library,word);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.pp.word.setAdapter(adapter);
//
//        t1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.pp.fNumber.setBackground(getResources().getDrawable(R.color.white));
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//
//        t2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.pp.lNumber.setBackground(getResources().getDrawable(R.color.white));
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.pp.provinceNum.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.pp.provinceNum.setBackground(getResources().getDrawable(R.color.white));
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.pp.word.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }
}