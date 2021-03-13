package com.utabpars.gomgashteh.othercity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.databinding.MainCityItemBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OtherCityAdaptor extends RecyclerView.Adapter<OtherCityAdaptor.OtherCityViewHolder > {
    private List<City> provinces;
    public ItemCitySelected itemSelectedCallback;
    Context context;
    CityDatabase cityDatabase;
    int is_ten_city_selected=0;
    List<City> cityList;
    boolean isChecked;
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
        context=parent.getContext();
        return new OtherCityAdaptor.OtherCityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherCityAdaptor.OtherCityViewHolder holder, int position) {
        //holder.binding.setProvince(provinces.get(position));
        holder.getSelectetItem(provinces.get(position));

        holder.binding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_ten_city_selected<=9) {
                    boolean isChecked = holder.binding.checkbox.isChecked();
                    itemSelectedCallback.getSelectedItem(view, provinces.get(position), position, isChecked, is_ten_city_selected);
                }else {
                    holder.binding.checkbox.setChecked(false);
                    Toast.makeText(context, "شما مجاز به انتخاب ده شهر هستید", Toast.LENGTH_SHORT).show();
                    for (City city:cityList) {
                        if (city.getCity_id().equals(provinces.get(position).getCity_id())){
                            itemSelectedCallback.getSelectedItem(view, provinces.get(position), position, false, is_ten_city_selected);
                            holder.binding.checkbox.setChecked(false);
                        }

                    }

                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_ten_city_selected<=9) {
                    holder.binding.checkbox.toggle();
                     isChecked = holder.binding.checkbox.isChecked();
                    itemSelectedCallback.getSelectedItem(view,provinces.get(position),position,isChecked,is_ten_city_selected);
                }else {
                    Toast.makeText(context, "شما مجاز به انتخاب ده شهر هستید", Toast.LENGTH_SHORT).show();
                    for (City city:cityList) {
                        if (city.getCity_id().equals(provinces.get(position).getCity_id())){
                            itemSelectedCallback.getSelectedItem(view,provinces.get(position),position,false,is_ten_city_selected);
                            holder.binding.checkbox.setChecked(false);
                        }


                    }
                }




            }
        });

        FragmentHostOtherCity.cityMutableLiveData.observeForever(city->{
            if (provinces.get(position).getCity_id().equals(city.getCity_id()))
                holder.binding.checkbox.setChecked(false);
        });
        cityDatabase=CityDatabase.getInstance(context.getApplicationContext());
        cityDatabase.cityDao().
                getSelectedCitySize().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rea->{
                    cityList=new ArrayList<>();
                    Log.d("gfnhgmghmgh", "onViewCreated: goooood");
                    is_ten_city_selected=rea.size();
                    cityList=rea;


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
         void getSelectedItem(View view, City city, int position, boolean is_checked,int is_ten_city_selected);
    }

    public void setData(List<City> provinces, ItemCitySelected itemSelectedCallback) {
        this.provinces = provinces;

        this.itemSelectedCallback = itemSelectedCallback;
    }
}

