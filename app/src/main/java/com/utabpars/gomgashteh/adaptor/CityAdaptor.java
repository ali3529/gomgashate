package com.utabpars.gomgashteh.adaptor;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.Province;

import com.utabpars.gomgashteh.databinding.ItemCityBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;

import java.util.List;

public class CityAdaptor extends RecyclerView.Adapter<CityAdaptor.CityViewHolder> {
    private List<City> city;
    private CategoryCallBack categoryCallBack;
    public ItemProvinceSelected itemSelectedCallback;



    public CityAdaptor(List<City> categoryList, CategoryCallBack categoryCallBack) {
        this.city = categoryList;
        this.categoryCallBack=categoryCallBack;
    }






    @NonNull
    @Override
    public CityAdaptor.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemCityBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_city,parent,false);
        return new CityAdaptor.CityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdaptor.CityViewHolder  holder, int position) {
        //  holder.getSelectetItem(categoryList.get(position));
        holder.binding.setCity(city.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryCallBack.getCategoryId(view, city.get(position).getProvince_id(),position, city.get(position).getCity_name());
                if (itemSelectedCallback!=null){
                    itemSelectedCallback.getSelectedItem(view, city.get(position),position,false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return city.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        ItemCityBinding binding;
        public CityViewHolder(@NonNull ItemCityBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
//        private void getSelectetItem(CategoryModel.ListData listData){
//            if (listData.isSelected()){
//                binding.setCategory(listData);
//                binding.selected.setVisibility(View.VISIBLE);
//            }else {
//                binding.setCategory(listData);
//                binding.selected.setVisibility(View.GONE);
//            }
//
//        }
    }

    public interface ItemProvinceSelected {
        public void getSelectedItem(View view, City categoryModel, int position, boolean is_checked);
    }
}
