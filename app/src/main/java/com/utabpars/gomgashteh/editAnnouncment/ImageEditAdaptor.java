package com.utabpars.gomgashteh.editAnnouncment;

import android.net.Uri;
import android.view.LayoutInflater;
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
    onDeleteImage onDeleteImage;
    public ImageEditAdaptor(List<String> list,onDeleteImage onDeleteImage) {
        this.images = list;
        this.onDeleteImage=onDeleteImage;
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

            Picasso.get().load(Uri.parse(images.get(position))).into(holder.binding.img);

        holder.binding.deletImg.setOnClickListener( o->{
            images.remove(position);
            onDeleteImage.deleteImage(images);
        });
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
 interface onDeleteImage{
    void deleteImage(List<String> list);
}
