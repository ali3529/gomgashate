package com.utabpars.gomgashteh.maincity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemCitySeceltedBinding;
import com.utabpars.gomgashteh.databinding.ItemFilterTopBinding;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class SelectetdCityAdaptor extends RecyclerView.Adapter<SelectetdCityAdaptor.SelectedViewHolder> {
    List<CategoryModel.ListData> listData=new ArrayList<>();

    public SelectetdCityAdaptor(List<CategoryModel.ListData> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public SelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemCitySeceltedBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_city_secelted,parent,false);
        return new SelectedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedViewHolder holder, int position) {
        holder.binding.setTitle(listData.get(position).getCategoryName());
        holder.binding.delete.setOnClickListener(o ->{
            listData.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class SelectedViewHolder extends RecyclerView.ViewHolder {
        ItemCitySeceltedBinding binding;
        public SelectedViewHolder(@NonNull ItemCitySeceltedBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
