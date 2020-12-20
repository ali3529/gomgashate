package com.utabpars.gomgashteh.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemFilterTopBinding;
import com.utabpars.gomgashteh.interfaces.TopFilterCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.RmModel;

import java.util.ArrayList;
import java.util.List;

public class TopFilterAdaptor extends RecyclerView.Adapter<TopFilterAdaptor.FilterViewHolder> {
    List<RmModel.TopFilterData> lists=new ArrayList<>();
    TopFilterCallBack topFilterCallBack;
    Context context;

    public TopFilterAdaptor(List<RmModel.TopFilterData> lists) {
        this.lists = lists;
    }
    public TopFilterAdaptor(Context context,List<RmModel.TopFilterData> lists,TopFilterCallBack topFilterCallBack) {
        this.context=context;
        this.lists = lists;
        this.topFilterCallBack=topFilterCallBack;
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
        holder.binding.setTitle(lists.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getSlected(lists.get(position));
                topFilterCallBack.OnClickCallback(lists.get(position).getId(),lists.get(position).isIs_selected(),holder.binding);



            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
    MutableLiveData<Boolean> liveData=new MutableLiveData<>();
    class FilterViewHolder extends RecyclerView.ViewHolder {
        ItemFilterTopBinding binding;
        public FilterViewHolder(@NonNull ItemFilterTopBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }


        public void getSlected(RmModel.TopFilterData anoncmentModel){


            if (anoncmentModel.isIs_selected()){
                anoncmentModel.setIs_selected(false);


                binding.itemfilterlayout.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_filter_item));
                binding.txt.setTextColor(context.getResources().getColor(R.color.text_color_black));
            }else {
                anoncmentModel.setIs_selected(true);
                binding.itemfilterlayout.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_filter_item_selected));
                binding.txt.setTextColor(context.getResources().getColor(R.color.white));



            }

        }
    }
}
