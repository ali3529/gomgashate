package com.utabpars.gomgashteh.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdaptorUtil {

    @BindingAdapter({"imagebind","error"})
    public static void getImage(ImageView imageView, String s, Drawable error){
        Picasso.get().load(s).placeholder(error).into(imageView);
    }
}
