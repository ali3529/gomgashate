package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.ImageSliderAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncmentDetailBinding;
import com.utabpars.gomgashteh.databinding.ItemImageSliderBinding;
import com.utabpars.gomgashteh.model.DetailModel;
import com.utabpars.gomgashteh.viewmodel.DetailViewModel;

import java.util.List;

public class FragmentAnnouncmentDetail extends Fragment {
    MutableLiveData<DetailModel.Data> dataMutableLiveData;
    ImageSliderAdaptor sliderAdaptor;
    SliderView sliderView;
    FragmentAnnouncmentDetailBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_announcment_detail,container,false);
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
    // Inflate the layout for this fragment
        sliderView=binding.slider;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setProgress(true);
        int id=getArguments().getInt("id");
        DetailViewModel viewModel= new ViewModelProvider(this).get(DetailViewModel.class);
        viewModel.getDetail(id);
        dataMutableLiveData=viewModel.getMutableDetail();


        dataMutableLiveData.observe(getViewLifecycleOwner(), new Observer<DetailModel.Data>() {
            @Override
            public void onChanged(DetailModel.Data data) {
                setSlider(data.getPictures());
                binding.setProgress(false);
                binding.setDetails(data);
            }
        });
    }


    public void setSlider(List<String> pic){
        sliderAdaptor=new ImageSliderAdaptor(pic);
        sliderView.setSliderAdapter(sliderAdaptor);
    }
}