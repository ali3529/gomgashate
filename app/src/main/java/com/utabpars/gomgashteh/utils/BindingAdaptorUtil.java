package com.utabpars.gomgashteh.utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.smarteist.autoimageslider.SliderView;
import com.utabpars.gomgashteh.adaptor.ImageSliderAdaptor;
import com.utabpars.gomgashteh.paging.AnnouncementViewModel;

public class BindingAdaptorUtil {

    @BindingAdapter({"imagebind","error"})
    public static void getImage(ImageView imageView, String s, Drawable error){
        Utils.showimage(imageView,s,error);
    }


    @BindingAdapter({"imageSlider","errorSlider"})
    public static void imageSlider(ImageView imageView, String s, Drawable error){
        Utils.showimage(imageView,s,error);
    }


    @BindingAdapter("setvisib")
    public static void setvisible(LottieAnimationView view,boolean visibility){
        if (visibility){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }

    }

    @BindingAdapter("setImageSliderProgress")
    public static void setSliderProgress(RelativeLayout view, boolean visibility){
        if (visibility){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }

    }

    @BindingAdapter("setLayoutVisi")
    public static void setLayoutVisibility(RelativeLayout view,boolean visibility){
        if (visibility){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }

    }

    @BindingAdapter("setSwipeREfresh")
    public static void SetSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, AnnouncementViewModel viewModel){

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.swipeRefresh();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

    }


}
