package com.utabpars.gomgashteh.managerAnnouncement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemCategoryBinding;
import com.utabpars.gomgashteh.databinding.ItemOptionBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;

import java.util.ArrayList;
import java.util.List;

public class ManagerAdaptor extends RecyclerView.Adapter<ManagerAdaptor.OptionViewHolder> {
    List<ManageModel.Option> options=new ArrayList<>();
    DetileCallBack detileCallBack;
    public ManagerAdaptor(List<ManageModel.Option> options, DetileCallBack detileCallBack) {
        this.options = options;
        this.detileCallBack=detileCallBack;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemOptionBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_option,parent,false);
        return new OptionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        holder.binding.setCat(options.get(position));
        holder.itemView.setOnClickListener( o ->{
            detileCallBack.onItemClicked(o,options.get(position).getId());
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class OptionViewHolder extends RecyclerView.ViewHolder {
        ItemOptionBinding binding;
        public OptionViewHolder(@NonNull ItemOptionBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
