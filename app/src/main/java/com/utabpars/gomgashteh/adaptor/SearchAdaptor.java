package com.utabpars.gomgashteh.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemAnnouncementBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;

import java.util.List;

public class SearchAdaptor extends RecyclerView.Adapter<SearchAdaptor.SearchViewHolder> {
    List<AnoncmentModel.Detile> detiles;
    DetileCallBack detileCallBack;

    public SearchAdaptor(List<AnoncmentModel.Detile> detiles, DetileCallBack detileCallBack) {
        this.detiles = detiles;
        this.detileCallBack = detileCallBack;
    }

    public SearchAdaptor(List<AnoncmentModel.Detile> detiles) {
        this.detiles = detiles;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemAnnouncementBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_announcement,parent,false);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.binding.setAnnouncment(detiles.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detileCallBack.onItemClicked(view,detiles.get(position).getId());
            }
        });
        if (detiles.get(position).getReward().equals("0")){
            holder.binding.rewardLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return detiles.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        ItemAnnouncementBinding binding;
        public SearchViewHolder(@NonNull  ItemAnnouncementBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
