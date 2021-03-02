package com.utabpars.gomgashteh.othercity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.ProvinceAdaptor;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.Province;
import com.utabpars.gomgashteh.databinding.ProvinceItemBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;

import java.util.List;

public class OtherProvinceAdaptor extends RecyclerView.Adapter<OtherProvinceAdaptor.ProvinceViewHolder> {

    private List<Province> provinces;
    private CategoryCallBack categoryCallBack;
    public ItemProvinceSelected itemSelectedCallback;



    public OtherProvinceAdaptor(List<Province> categoryList, CategoryCallBack categoryCallBack,ItemProvinceSelected itemProvinceSelected) {
        this.provinces = categoryList;
        this.categoryCallBack=categoryCallBack;
        this.itemSelectedCallback=itemProvinceSelected;
    }






    @NonNull
    @Override
    public OtherProvinceAdaptor.ProvinceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ProvinceItemBinding binding= DataBindingUtil.inflate(inflater, R.layout.province_item,parent,false);
        return new OtherProvinceAdaptor.ProvinceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherProvinceAdaptor.ProvinceViewHolder  holder, int position) {
        //  holder.getSelectetItem(categoryList.get(position));
        //  holder.binding.setProvince(provinces.get(position));
        holder.getSelectetItem(provinces.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryCallBack.getCategoryId(view, provinces.get(position).getProvince_id(),position, provinces.get(position).getProvince_name());

            }
        });

        holder.binding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            itemSelectedCallback.clearAllOtherCity(provinces.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return provinces.size();
    }

    class ProvinceViewHolder extends RecyclerView.ViewHolder {
        ProvinceItemBinding binding;
        public ProvinceViewHolder(@NonNull ProvinceItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        private void getSelectetItem(Province province){
            binding.setProvince(province);
            if (province.isSelected_other_City()){
                Log.d("sdvsdvdsv", "getSelectetItem: if");
                binding.checkbox.setVisibility(View.VISIBLE);
            }else {
                Log.d("sdvsdvdsv", "getSelectetItem: else");
                binding.checkbox.setVisibility(View.GONE);
            }

        }

    }

    public interface ItemProvinceSelected {
       // public void getSelectedItem(View view, Province categoryModel, int position, boolean is_checked);
        void clearAllOtherCity(Province city);
    }
}
