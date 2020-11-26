package com.utabpars.gomgashteh.adaptor;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemImageAddAnnounsmentBinding;


import java.util.ArrayList;
import java.util.List;

public class AddImageAnnouncmentAdaptor extends RecyclerView.Adapter<AddImageAnnouncmentAdaptor.TViewHolder> {
    List<Uri> uris=new ArrayList<>();

    public AddImageAnnouncmentAdaptor(List<Uri> uris) {
        this.uris = uris;
    }

    @NonNull
    @Override
    public TViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemImageAddAnnounsmentBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_image_add_announsment,parent,false);
        return new TViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TViewHolder holder, int position) {
        holder.binding.img.setImageURI(uris.get(position));
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    class TViewHolder extends RecyclerView.ViewHolder {
        ItemImageAddAnnounsmentBinding binding;
        public TViewHolder(@NonNull  ItemImageAddAnnounsmentBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
