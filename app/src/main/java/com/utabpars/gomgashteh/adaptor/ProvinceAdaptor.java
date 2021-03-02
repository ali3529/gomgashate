package com.utabpars.gomgashteh.adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.Province;
import com.utabpars.gomgashteh.databinding.ProvinceItemBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.maincity.FragmentChoosecity;

import java.util.List;

public class ProvinceAdaptor extends RecyclerView.Adapter<ProvinceAdaptor.ProvinceViewHolder> {
    private List<Province> provinces;
    private CategoryCallBack categoryCallBack;
    public ItemProvinceSelected itemSelectedCallback;



    public ProvinceAdaptor(List<Province> categoryList, CategoryCallBack categoryCallBack,ItemProvinceSelected itemProvinceSelected) {
        this.provinces = categoryList;
        this.categoryCallBack=categoryCallBack;
        this.itemSelectedCallback=itemProvinceSelected;
    }





    @NonNull
    @Override
    public ProvinceAdaptor.ProvinceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ProvinceItemBinding binding= DataBindingUtil.inflate(inflater, R.layout.province_item,parent,false);
        return new ProvinceAdaptor.ProvinceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvinceAdaptor.ProvinceViewHolder  holder, int position) {
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
                itemSelectedCallback.clearAllCity(provinces.get(position));
            }
        });

//        FragmentChoosecity.provinceMutableLiveData.observeForever(t->{
//            if (provinces.get(position).getProvince_id().equals(t.getProvince_id())){
//                holder.binding.checkbox.setChecked(false);
//                Log.d("fbdfbdfbfnbnb", "getSelectedItem: kkkkkkkkkkkkkkk");
//            }
//        });
//
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

            if (province.isSelected_province()){
                Log.d("sdvsdvdsv", "getSelectetItem: if");
                binding.checkbox.setVisibility(View.VISIBLE);
            }else {
                Log.d("sdvsdvdsv", "getSelectetItem: else");
            }

        }

    }

    public interface ItemProvinceSelected {
      //  public void getSelectedItem(View view, Province categoryModel, int position, boolean is_checked);
        void clearAllCity(Province city);
    }

}
