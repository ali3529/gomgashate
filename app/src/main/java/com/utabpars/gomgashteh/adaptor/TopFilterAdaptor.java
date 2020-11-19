package com.utabpars.gomgashteh.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemFilterTopBinding;

import java.util.ArrayList;
import java.util.List;

public class TopFilterAdaptor extends RecyclerView.Adapter<TopFilterAdaptor.FilterViewHolder> {
    List<String> lists=new ArrayList<>();

    public TopFilterAdaptor(List<String> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemFilterTopBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_filter_top,parent,false);
        return new FilterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        holder.binding.setTitle(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        ItemFilterTopBinding binding;
        public FilterViewHolder(@NonNull ItemFilterTopBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
