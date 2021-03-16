package com.utabpars.gomgashteh.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.utabpars.gomgashteh.R;

import java.util.ArrayList;
import java.util.List;

public class PlateNumber extends LinearLayout {
    EditText numOne,numTwo,ir_num;
    Spinner word_spinner;
    String word;
    int view;
    String[] plate_word=new String[]{"الف","ب","پ","ج","د","ژ","س","ص","ط","ق","ک","گ","ل","م","ن","و","ه","ی"};
    public PlateNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        @SuppressLint("Recycle") TypedArray hh=context.obtainStyledAttributes(attrs,R.styleable.PlateNumber);
        String s=hh.getString(R.styleable.PlateNumber_type);
            inflate(getContext(),setView(s), this);
            
            if (s.equals("5")) {
                initViews();
                setPlateWord();
            }


        Log.d("sdvdsvvv", "PlateNumber: "+s);
    }

    private void initViews() {
        numOne=findViewById(R.id.f_number);
        numTwo=findViewById(R.id.l_number);
        ir_num=findViewById(R.id.province_num);
        word_spinner=findViewById(R.id.word);
    }

    public List<String> getNumber(){
        List<String> number=new ArrayList<>();
        number.add(numOne.getText().toString());
        number.add( word_spinner.getSelectedItem().toString());
        number.add(numTwo.getText().toString());
        number.add(ir_num.getText().toString());


        return number ;
    }

private void setPlateWord(){
    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.item_default_power_spinner_library,plate_word);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        word_spinner.setAdapter(adapter);
}

private int setView(String type){
        if (type.equals("5")){
            view=R.layout.plate_number;
        }else if (type.equals("6")){
            view=R.layout.item_tttt;
        }
        return view;
}
}
