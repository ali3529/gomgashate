package com.utabpars.gomgashteh.imagehelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentOpenImageBinding;

import java.util.ArrayList;
import java.util.List;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoomBottomSheet;

public class FragmentOpenImage extends Fragment {
    FragmentOpenImageBinding binding;
    static List<String> lists=new ArrayList<>();
    ZoomImageAdaptor zoomImageAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_open_image,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        zoomImageAdaptor=new ZoomImageAdaptor(lists);
        binding.slider.setSliderAdapter(zoomImageAdaptor);

        binding.backArrow.setOnClickListener(o->{
            Navigation.findNavController(view).navigateUp();
        });


    }

    public static void getImages(List<String> list){
       lists=list;
    }
}