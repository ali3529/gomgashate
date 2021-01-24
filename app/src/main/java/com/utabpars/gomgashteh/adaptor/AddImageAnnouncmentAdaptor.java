package com.utabpars.gomgashteh.adaptor;

import android.net.Uri;
import android.util.Log;
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
    onDeleteImages onDeleteImage;

    public AddImageAnnouncmentAdaptor(List<Uri> uris) {
        this.uris = uris;
    }

    public AddImageAnnouncmentAdaptor(List<Uri> uris, onDeleteImages onDeleteImage) {
        this.uris = uris;
        this.onDeleteImage=onDeleteImage;
    }

    public void AddImageAnnouncmentAdaptor(List<Uri> uris, onDeleteImages onDeleteImage) {
        this.uris = uris;
        this.onDeleteImage=onDeleteImage;
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
        Log.d("sfcsdfsdf", "onBindViewHolder: "+uris.size());
        holder.binding.img.setImageURI(uris.get(position));
        holder.binding.deletImg.setOnClickListener(o ->{
            onDeleteImage.deleteImage(uris,position);
        });
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
    public interface onDeleteImages{
        void deleteImage(List<Uri> list,int position);
    }
}
