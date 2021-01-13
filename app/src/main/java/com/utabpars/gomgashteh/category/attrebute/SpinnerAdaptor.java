package com.utabpars.gomgashteh.category.attrebute;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.SpinnerAnimation;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemSpinnerBinding;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdaptor extends RecyclerView.Adapter<SpinnerAdaptor.SpinnerViewHolder> {
    SpinnerModel spinnerModel;
    SpiinerCallback spiinerCallback;

    public SpinnerAdaptor(SpinnerModel spinnerModel, SpiinerCallback spiinerCallback) {
        this.spinnerModel = spinnerModel;
        this.spiinerCallback = spiinerCallback;
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
        Log.d("gdsdgdsdsg", "onBindViewHolder: "+spinnerModel.getAttrebuteData().get(0).getName());
        Log.d("gdsdgdsdsg", "onBindViewHolder: "+spinnerModel.getAttrebuteData().get(1).getName());
        holder.binding.spinner.setSpinnerPopupAnimation(SpinnerAnimation.BOUNCE);
        List<String> list=new ArrayList<>();
        for (String s:spinnerModel.getAttrebuteData().get(position).getValues()) {
            String[] strings=s.split("@");
            list.add(strings[1]);
            Log.d("dvsdvdsv", "onBindViewHolder: "+strings[0].toString());
            Log.d("dvsdvdsv", "onBindViewHolder: "+strings[1].toString());

        }
        holder.binding.setText(spinnerModel.getAttrebuteData().get(position).getName());


        holder.binding.spinner.setItems(list);

        holder.binding.spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int i, @Nullable String s, int i1, String t1) {
                Log.d("Dsfdsfdsf", "onItemSelected: "+t1);
                Log.d("Dsfdsfdsf", "onItemSelected: il "+i1);
                Log.d("Dsfdsfdsf", "onItemSelected: id "+spinnerModel.getAttrebuteData().get(position).getId());
                Log.d("Dsfdsfdsf", "onItemSelected: i "+spinnerModel.getAttrebuteData().get(position).getValues().get(i1));
                spiinerCallback.SpinnerItemCallBack(spinnerModel.getAttrebuteData().get(position).getId(),spinnerModel.getAttrebuteData().get(position).getValues().get(i1));
            }
        });
    }

    @Override
    public int getItemCount() {
        return spinnerModel.getAttrebuteData().size();
    }

    class SpinnerViewHolder extends RecyclerView.ViewHolder {
        ItemSpinnerBinding binding;
        public SpinnerViewHolder(@NonNull  ItemSpinnerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
