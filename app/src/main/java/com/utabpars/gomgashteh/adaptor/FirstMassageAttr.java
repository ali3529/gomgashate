package com.utabpars.gomgashteh.adaptor;


import android.annotation.SuppressLint;
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
import com.utabpars.gomgashteh.category.attrebute.BottonShettCallback;
import com.utabpars.gomgashteh.category.attrebute.SpinnerModel;
import com.utabpars.gomgashteh.chat.ChatStatusModel;
import com.utabpars.gomgashteh.databinding.ItemSpinnerBinding;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FirstMassageAttr extends RecyclerView.Adapter<FirstMassageAttr.SpinnerViewHolder> {
    List<ChatStatusModel.attributes> spinnerModel=new ArrayList<>();

    int positionT;
    BottonShettCallback bottonShettCallback;
    boolean isckeck;

    public FirstMassageAttr(List<ChatStatusModel.attributes> attributes, BottonShettCallback bottonShettCallback) {
        this.spinnerModel = attributes;
        this.bottonShettCallback = bottonShettCallback;
    }

    @NonNull
    @Override
    public SpinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemSpinnerBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_spinner,parent,false);
        return new SpinnerViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull FirstMassageAttr.SpinnerViewHolder holder, int position) {
        positionT=position;
//        holder.binding.spinner.setSpinnerPopupAnimation(SpinnerAnimation.BOUNCE);
        List<String> list=new ArrayList<>();
        for (String s:spinnerModel.get(position).getValue()) {
            String[] strings=s.split("@");
            list.add(strings[1]);

        }
        holder.binding.setText(spinnerModel.get(position).getName());


        holder.binding.spinner.setOnClickListener(o ->{
            bottonShettCallback.onClickSpinner(spinnerModel.get(position).getId(),
                    spinnerModel.get(position).getValue(),position);

        });


        if (spinnerModel.get(position).isNecessary()){
            holder.binding.nassasery.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {


        return spinnerModel.size();

    }

    class SpinnerViewHolder extends RecyclerView.ViewHolder {
        ItemSpinnerBinding binding;
        public SpinnerViewHolder(@NonNull  ItemSpinnerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public void setText(String text,int position){
        spinnerModel.get(position).setName(text);
        notifyDataSetChanged();
    }


}
