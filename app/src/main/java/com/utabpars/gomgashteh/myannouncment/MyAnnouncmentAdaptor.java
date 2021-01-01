package com.utabpars.gomgashteh.myannouncment;

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

import java.util.ArrayList;
import java.util.List;

public class MyAnnouncmentAdaptor extends RecyclerView.Adapter<MyAnnouncmentAdaptor.MyAnnounceViewHolder> {
    DetileCallBack detileCallBack;
    List<AnoncmentModel.Detile> detiles=new ArrayList<>();

    public MyAnnouncmentAdaptor(List<AnoncmentModel.Detile> detiles , DetileCallBack detileCallBack) {
        this.detiles = detiles;
        this.detileCallBack=detileCallBack;
    }


    @NonNull
    @Override
    public MyAnnounceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemAnnouncementBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_announcement,parent,false);
        return new MyAnnounceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnnounceViewHolder holder, int position) {
        holder.binding.setAnnouncment(detiles.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detileCallBack.onItemClicked(view,detiles.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return detiles.size();
    }

    class MyAnnounceViewHolder extends RecyclerView.ViewHolder {
        ItemAnnouncementBinding binding;
        public MyAnnounceViewHolder(@NonNull ItemAnnouncementBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
