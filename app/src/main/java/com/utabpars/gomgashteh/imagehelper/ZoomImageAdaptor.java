package com.utabpars.gomgashteh.imagehelper;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.ImageSliderAdaptor;
import com.utabpars.gomgashteh.databinding.ItemImageSliderBinding;
import com.utabpars.gomgashteh.databinding.ItemZoomImageBinding;

import java.util.ArrayList;
import java.util.List;

public class ZoomImageAdaptor extends SliderViewAdapter<ZoomImageAdaptor.ZoomImageSliderVieHolder> {

    private List<String> picture=new ArrayList<>();


    public ZoomImageAdaptor(List<String> picture) {
        this.picture = picture;
    }


    @Override
    public ZoomImageSliderVieHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        ItemZoomImageBinding binding= DataBindingUtil.inflate(inflater, R.layout.item_zoom_image,parent,false);
        return new ZoomImageAdaptor.ZoomImageSliderVieHolder(binding);
    }

    @Override
    public void onBindViewHolder(ZoomImageSliderVieHolder viewHolder, int position) {
        viewHolder.binding.setPicture(picture.get(position));

    }

    @Override
    public int getCount() {
        return picture.size();
    }

    class ZoomImageSliderVieHolder extends SliderViewAdapter.ViewHolder {
        ItemZoomImageBinding binding;
        public ZoomImageSliderVieHolder(@NonNull ItemZoomImageBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }

}
