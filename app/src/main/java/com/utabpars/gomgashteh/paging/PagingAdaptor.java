package com.utabpars.gomgashteh.paging;

import android.database.DatabaseUtils;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemAnnouncementBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;

public class PagingAdaptor extends PagedListAdapter<AnoncmentModel.Detile, PagingAdaptor.AnnoncementViewHolder> {
    DetileCallBack detileCallBack;
    public PagingAdaptor(){
        super(DIFF_CALLBACK);
    }


    public static DiffUtil.ItemCallback<AnoncmentModel.Detile> DIFF_CALLBACK=new DiffUtil.ItemCallback<AnoncmentModel.Detile>() {
        @Override
        public boolean areItemsTheSame(@NonNull AnoncmentModel.Detile oldItem, @NonNull AnoncmentModel.Detile newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AnoncmentModel.Detile oldItem, @NonNull AnoncmentModel.Detile newItem) {
            return oldItem.equals(newItem);
        }
    };


    @NonNull
    @Override
    public AnnoncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemAnnouncementBinding binding= DataBindingUtil.inflate(inflater,R.layout.item_announcement,parent,false);
        return new AnnoncementViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnoncementViewHolder holder, int position) {
        holder.binding.setAnnouncment(getItem(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detileCallBack.onItemClicked(view,getItem(position).getId());
            }
        });
        if (getItem(position).getType().equals("پیدا شده")){
            holder.binding.rewardLayout.setVisibility(View.INVISIBLE);
        }
       if (getItem(position).getType().equals("پیدا شده")){
           holder.binding.rewardLayout.setVisibility(View.INVISIBLE);
       }
        if (getItem(position).getReward().equals("0 تومان")){
            holder.binding.rewardLayout.setVisibility(View.INVISIBLE);
        }else {
            holder.binding.rewardLayout.setVisibility(View.VISIBLE);
        }

        if (getItem(position).getPishkhan().equals("دفتر پیشخوان")){
            holder.binding.pishkan.setVisibility(View.VISIBLE);
        }else {
            holder.binding.pishkan.setVisibility(View.INVISIBLE);
        }
        holder.binding.status.setVisibility(View.GONE);

    }

    class AnnoncementViewHolder extends RecyclerView.ViewHolder {

        ItemAnnouncementBinding binding;
        public AnnoncementViewHolder(@NonNull ItemAnnouncementBinding binding) {
            super(binding.getRoot());
            this.binding=binding;



        }
    }

    public void getDEtail(DetileCallBack detileCallBack){
        this.detileCallBack=detileCallBack;
    }


}
