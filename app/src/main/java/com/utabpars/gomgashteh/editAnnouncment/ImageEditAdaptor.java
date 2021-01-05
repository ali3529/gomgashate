package com.utabpars.gomgashteh.editAnnouncment;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemImageAddAnnounsmentBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageEditAdaptor extends RecyclerView.Adapter<ImageEditAdaptor.ImageViewHolder> {
    List<String> images=new ArrayList<>();

    public ImageEditAdaptor(List<String> list) {
        this.images = list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemImageAddAnnounsmentBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_image_add_announsment,parent,false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        for (int i = 0; i < images.size(); i++) {
            Picasso.get().load(Uri.parse(images.get(i))).into(holder.binding.img);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ItemImageAddAnnounsmentBinding binding;
        public ImageViewHolder(@NonNull ItemImageAddAnnounsmentBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
