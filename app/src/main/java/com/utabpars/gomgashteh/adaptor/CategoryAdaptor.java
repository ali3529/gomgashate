package com.utabpars.gomgashteh.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemCategoryBinding;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.CategoryViewHolder > {
    private List<CategoryModel.ListData> categoryList;

    public CategoryAdaptor(List<CategoryModel.ListData> categoryList) {
        this.categoryList = categoryList;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_category,parent,false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.binding.setCategory(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;
        public CategoryViewHolder(@NonNull   ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
