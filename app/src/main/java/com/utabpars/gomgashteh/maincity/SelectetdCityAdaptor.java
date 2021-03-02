package com.utabpars.gomgashteh.maincity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.databinding.ItemCitySeceltedBinding;
import com.utabpars.gomgashteh.databinding.ItemFilterTopBinding;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class SelectetdCityAdaptor extends RecyclerView.Adapter<SelectetdCityAdaptor.SelectedViewHolder> {
    List<City> listData=new ArrayList<>();
    deleteSelectedCityCallback deleteSelectedCityCallback;



    public SelectetdCityAdaptor(List<City> listData,deleteSelectedCityCallback deleteSelectedCityCallback) {
        this.listData = listData;
        this.deleteSelectedCityCallback=deleteSelectedCityCallback;
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
//        if (listData==null){
//            holder.binding.setTitle(names.get(position));
//            holder.binding.delete.setOnClickListener(o ->{
//                names.remove(position);
//                notifyDataSetChanged();
//            });
//        }else {
//            holder.binding.setTitle(listData.get(position).getCategoryName());
//            holder.binding.delete.setOnClickListener(o ->{
//                listData.remove(position);
//                notifyDataSetChanged();
//            });
//        }
        holder.binding.setTitle(listData.get(position).getCity_name());

        holder.binding.delete.setOnClickListener(o ->{
        deleteSelectedCityCallback.DeleteSelectetdCity(listData.get(position));
        if (listData.size()==1){
            deleteSelectedCityCallback.last(listData.get(position));
        }
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


    public interface deleteSelectedCityCallback{
        void DeleteSelectetdCity(City city);
        void last(City city);
    }
}
