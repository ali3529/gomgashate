package com.utabpars.gomgashteh.category.attrebute;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skydoves.powerspinner.OnSpinnerDismissListener;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.SpinnerAnimation;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.chat.ChatStatusModel;
import com.utabpars.gomgashteh.databinding.ItemSpinnerBinding;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdaptor extends RecyclerView.Adapter<SpinnerAdaptor.SpinnerViewHolder> {
    SpinnerModel spinnerModel;

    int positionT;
    BottonShettCallback bottonShettCallback;
    ItemSpinnerBinding binding;
    boolean isckeck;
    public SpinnerAdaptor(SpinnerModel spinnerModel, BottonShettCallback bottonShettCallback) {
        this.spinnerModel = spinnerModel;
        this.bottonShettCallback=bottonShettCallback;
    }


    @NonNull
    @Override
    public SpinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemSpinnerBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_spinner,parent,false);
        return new SpinnerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinnerViewHolder holder, int position) {
        positionT=position;
        this.binding=holder.binding;
//        holder.binding.spinner.setSpinnerPopupAnimation(SpinnerAnimation.BOUNCE);
        List<String> list=new ArrayList<>();
        for (String s:spinnerModel.getAttrebuteData().get(position).getValues()) {
            String[] strings=s.split("@");
            list.add(strings[1]);
        }
        holder.binding.setText(spinnerModel.getAttrebuteData().get(position).getName());


        holder.binding.spinner.setOnClickListener(o ->{
            bottonShettCallback.onClickSpinner(spinnerModel.getAttrebuteData().get(position).getId(),
                    spinnerModel.getAttrebuteData().get(position).getValues(),position);



        });


    }

    @Override
    public int getItemCount() {

            return spinnerModel.getAttrebuteData().size();


    }

    public class SpinnerViewHolder extends RecyclerView.ViewHolder {
        ItemSpinnerBinding binding;
        public SpinnerViewHolder(@NonNull  ItemSpinnerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public void setText(String text,int position){
        spinnerModel.getAttrebuteData().get(position).setName(text);
        notifyDataSetChanged();
        Log.d("dsfdsf", "setText: "+position);
    }



}
