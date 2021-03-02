package com.utabpars.gomgashteh.othercity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.databinding.MainCityItemBinding;

import java.util.List;

public class OtherCityAdaptor extends RecyclerView.Adapter<OtherCityAdaptor.OtherCityViewHolder > {
    private List<City> provinces;
    public ItemCitySelected itemSelectedCallback;

    public OtherCityAdaptor(List<City> provinces, ItemCitySelected itemSelectedCallback) {
        this.provinces = provinces;

        this.itemSelectedCallback = itemSelectedCallback;
    }

    public OtherCityAdaptor() {
    }

    @NonNull
    @Override
    public OtherCityAdaptor.OtherCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        MainCityItemBinding binding= DataBindingUtil.inflate(inflater, R.layout.main_city_item,parent,false);
        return new OtherCityAdaptor.OtherCityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherCityAdaptor.OtherCityViewHolder holder, int position) {
        //holder.binding.setProvince(provinces.get(position));
        holder.getSelectetItem(provinces.get(position));

        holder.binding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked=holder.binding.checkbox.isChecked();
                itemSelectedCallback.getSelectedItem(view,provinces.get(position),position,isChecked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.checkbox.toggle();
                boolean isChecked=holder.binding.checkbox.isChecked();

                itemSelectedCallback.getSelectedItem(view,provinces.get(position),position,isChecked);




            }
        });

        FragmentHostOtherCity.cityMutableLiveData.observeForever(city->{
            if (provinces.get(position).getCity_id().equals(city.getCity_id()))
                holder.binding.checkbox.setChecked(false);
        });
    }

    @Override
    public int getItemCount() {
        return provinces.size();
    }

    class OtherCityViewHolder extends RecyclerView.ViewHolder {
        MainCityItemBinding binding;

        public OtherCityViewHolder(@NonNull   MainCityItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        private void getSelectetItem(City city){
            binding.setProvince(city);

            if (city.isSelected_otherCity()){
                binding.checkbox.setChecked(true);
            }else {
                binding.checkbox.setChecked(false);
            }



        }
    }
    public interface ItemCitySelected {
         void getSelectedItem(View view, City city, int position, boolean is_checked);
    }

    public void setData(List<City> provinces, ItemCitySelected itemSelectedCallback) {
        this.provinces = provinces;

        this.itemSelectedCallback = itemSelectedCallback;
    }
}

