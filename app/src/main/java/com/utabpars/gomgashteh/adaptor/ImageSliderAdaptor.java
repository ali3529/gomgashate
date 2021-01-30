package com.utabpars.gomgashteh.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.ItemImageSliderBinding;
import com.utabpars.gomgashteh.model.ProgressModel;

import java.util.ArrayList;
import java.util.List;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoomConfig;

public class ImageSliderAdaptor extends SliderViewAdapter<ImageSliderAdaptor.DetailImageSliderVieHolder> {
    private List<String> picture=new ArrayList<>();
    imageCallback imageCallback;

    public ImageSliderAdaptor(List<String> picture, ImageSliderAdaptor.imageCallback imageCallback) {
        this.picture = picture;
        this.imageCallback = imageCallback;
    }

    public ImageSliderAdaptor() {
    }



    public ImageSliderAdaptor(List<String> picture) {
        this.picture = picture;
    }

    @Override
    public DetailImageSliderVieHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemImageSliderBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_image_slider,parent,false);
        return new DetailImageSliderVieHolder(binding);
    }

    @Override
    public void onBindViewHolder(DetailImageSliderVieHolder viewHolder, int position) {
        viewHolder.binding.setPicture(picture.get(position));
        viewHolder.itemView.setOnClickListener(o->{
            imageCallback.ImageOnClick(picture.get(position));
        });

    }

    @Override
    public int getCount() {
        return picture.size();
    }

    class DetailImageSliderVieHolder extends SliderViewAdapter.ViewHolder {
        ItemImageSliderBinding binding;
        public DetailImageSliderVieHolder(@NonNull ItemImageSliderBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }

   public interface imageCallback{
        void ImageOnClick(String url);
    }

}
