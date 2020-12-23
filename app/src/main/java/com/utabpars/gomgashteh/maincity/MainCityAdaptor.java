package com.utabpars.gomgashteh.maincity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.MainCityItemBinding;
import com.utabpars.gomgashteh.interfaces.ItemSelectedCallback;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.util.List;

public class MainCityAdaptor extends RecyclerView.Adapter<MainCityAdaptor.CategoryViewHolder > {
    private List<CategoryModel.ListData> categoryList;
    public ItemSelectedCallback itemSelectedCallback;

    public MainCityAdaptor(List<CategoryModel.ListData> categoryList, ItemSelectedCallback itemSelectedCallback) {
        this.categoryList = categoryList;

        this.itemSelectedCallback = itemSelectedCallback;
    }




    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        MainCityItemBinding binding= DataBindingUtil.inflate(inflater, R.layout.main_city_item,parent,false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.getSelectetItem(categoryList.get(position));

        holder.binding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked=holder.binding.checkbox.isChecked();
                itemSelectedCallback.getSelectedItem(view,categoryList.get(position),position,isChecked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.checkbox.toggle();
                boolean isChecked=holder.binding.checkbox.isChecked();

                    itemSelectedCallback.getSelectedItem(view,categoryList.get(position),position,isChecked);




            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        MainCityItemBinding binding;

        public CategoryViewHolder(@NonNull   MainCityItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        private void getSelectetItem(CategoryModel.ListData listData){
            binding.setCategory(listData);
            if (listData.isSelected()){
                binding.checkbox.toggle();
            }



        }
    }

}
