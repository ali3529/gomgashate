package com.utabpars.gomgashteh.utils;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;
import com.utabpars.gomgashteh.model.AnoncmentModel;

public class BindingAdaptorUtil {

    @BindingAdapter({"imagebind","error"})
    public static void getImage(ImageView imageView, String s, Drawable error){
        Picasso.get().load(s).placeholder(error).into(imageView);
    }

    @BindingAdapter("setvisib")
    public static void setvisi(LottieAnimationView view,boolean visibility){
        if (visibility){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }

    }


}
