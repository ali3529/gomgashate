package com.utabpars.gomgashteh.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.category.SubSetModel;
import com.utabpars.gomgashteh.database.categoryDatabase.Category;
import com.utabpars.gomgashteh.databinding.ItemCategoryBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.interfaces.ItemSelectedCallback;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.CategoryViewHolder > {
    private List<Category> categoryList;
    private CategoryCallBack categoryCallBack;
    public ItemSelectedCallback itemSelectedCallback;


//    public CategoryAdaptor(List<CategoryModel.ListData> categoryList, CategoryCallBack categoryCallBack, ItemSelectedCallback itemSelectedCallback) {
//        this.categoryList = categoryList;
//        this.categoryCallBack = categoryCallBack;
//        this.itemSelectedCallback = itemSelectedCallback;
//    }

    public CategoryAdaptor(List<Category> categoryList, CategoryCallBack categoryCallBack) {
        this.categoryList = categoryList;
        this.categoryCallBack=categoryCallBack;
    }

//    public CategoryAdaptor(List<CategoryModel.ListData> categoryListt) {
//        this.categoryList = categoryListt;
//    }




    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_category,parent,false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
     //  holder.getSelectetItem(categoryList.get(position));
        holder.binding.setCategory(categoryList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryCallBack.getCategoryId(view,categoryList.get(position).getId(),position,categoryList.get(position).getName());
                if (itemSelectedCallback!=null){
                    itemSelectedCallback.getSelectedItem(view,categoryList.get(position),position,false);
                }

            }
        });
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

}
