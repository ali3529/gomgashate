package com.utabpars.gomgashteh.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.utabpars.gomgashteh.R;

import java.util.ArrayList;
import java.util.List;

public class PlateNumber extends LinearLayout {
    EditText numOne,numTwo,ir_num;
    TextView title;
    Spinner word_spinner;
    String word;
     String type="0";
      String state;
     static MutableLiveData<String> liveData=new MutableLiveData<>();
    int view;
    String[] plate_word=new String[]{"الف","f","ب","پ","ج","د","ژ","س","ص","ط","ق","ک","گ","ل","م","ن","و","ه","ی"};
    public PlateNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        @SuppressLint("Recycle") TypedArray hh=context.obtainStyledAttributes(attrs,R.styleable.PlateNumber);
//         type=hh.getString(R.styleable.PlateNumber_type);
          //  inflate(getContext(),setView(type), this);
//



        Log.d("sdvdsvvv", "PlateNumber: "+ type);
            liveData.observeForever(t->{
                if (!type.equals(t)){
                type=t;
                detachAllViewsFromParent();
                inflate(getContext(),setView(type), this);
                if (type.equals("39") || type.equals("127") || type.equals("165")) {
                    initViews();
                    setPlateWord();
                    Log.d("sdvdsvvv", "PlateNumber: vvvv");
                }else if (type.equals("116") || type.equals("166")){
                    initViewsMoto();
                } else if (type.equals("38") || type.equals("117") || type.equals("162") ||
                        type.equals("163") || type.equals("121") || type.equals("156")
                        || type.equals("157") || type.equals("158")) {
                    initNationViews();
                }else if (type.equals("118")){
                    initCardNumberViews();
                }else if (type.equals("154") || type.equals("155")){
                    initCheckNumberViews();
                }
                }

            });
    }

    private void initViews() {
        numOne=findViewById(R.id.f_number);
        numTwo=findViewById(R.id.l_number);
        ir_num=findViewById(R.id.province_num);
        word_spinner=findViewById(R.id.word);
    }
    private void initViewsMoto() {
        numOne=findViewById(R.id.t_number);
        numTwo=findViewById(R.id.b_number);
    }

    private void initNationViews() {
        numOne=findViewById(R.id.nation_code_card);
        title=findViewById(R.id.title_nation_card);
        title.setText("کد ملی");

    }

    private void initCheckNumberViews() {
        numOne=findViewById(R.id.nation_code_card);
        title=findViewById(R.id.title_nation_card);
        title.setText("شماره چک یا سفته");

    }
    private void initCardNumberViews() {
        numOne=findViewById(R.id.nation_code_card);
        title=findViewById(R.id.title_nation_card);
        title.setText("شماره کارت");

    }

private void setPlateWord(){
    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.item_default_power_spinner_library,plate_word);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        word_spinner.setAdapter(adapter);
}

private int setView(String type){
        if (type.equals("39") || type.equals("127") || type.equals("165")){
            view=R.layout.plate_number;
        }else if (type.equals("116") || type.equals("166")){
            view=R.layout.moto_plate;
        }else if (type.equals("38") || type.equals("117") || type.equals("162") ||
                type.equals("163") || type.equals("121") || type.equals("156")
                || type.equals("157") || type.equals("158")){
            view=R.layout.national_code;
        }else if (type.equals("118")){
            view=R.layout.national_code;
        }else if (type.equals("154") || type.equals("155")){
            view=R.layout.national_code;
        }
        else view=R.layout.plate_number;
        return view;
}


    public String getCarPlateNumber(){
        String number;
        if (numOne.getText().toString().length()==0 || numTwo.getText().toString().length()==0
                || ir_num.getText().toString().length()==0){
            number="";
        }else {
            number=numOne.getText().toString()+"-"+
                    word_spinner.getSelectedItem().toString()+"-"+
                    numTwo.getText().toString()+"-"+
                    ir_num.getText().toString();
        }


        Log.d("dbdhdh", "getCarPlateNumber: "+number);
        return number;
    }

    public String getMotoNumber(){
        String number;
        if (numOne.getText().toString().length()==0 || numTwo.getText().toString().length()==0 ){
            number="";
        }else {
            number=numOne.getText().toString()+"-"+
                    numTwo.getText().toString();
        }

        return number;
    }
    public String getCardNumber(){
        return numOne.getText().toString();
    }

public static void setType(String types){
        liveData.setValue(types);
}


}
