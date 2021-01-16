package com.utabpars.gomgashteh.managerAnnouncement;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.SliderView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.ImageSliderAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentShowEditBinding;
import com.utabpars.gomgashteh.editAnnouncment.EditAnnouncementViewModel;
import com.utabpars.gomgashteh.model.DetailModel;

import java.util.List;

public class FragmentShowEdit extends Fragment {
    FragmentShowEditBinding binding;
    EditAnnouncementViewModel viewModel;
    ImageSliderAdaptor sliderAdaptor;
    SliderView sliderView;
    String desteniion="g";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_show_edit,container,false);
        viewModel=new ViewModelProvider(this).get(EditAnnouncementViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int id=getArguments().getInt("id");
        try {
            desteniion=getArguments().getString("desti");
        }catch (Exception e){

        }

        sliderView=binding.slider;
        binding.setProgress(true);
        viewModel.getEditDetail(id);
        viewModel.dataMutableLiveData.observe(getViewLifecycleOwner(), new Observer<DetailModel.Data>() {
            @Override
            public void onChanged(DetailModel.Data data) {
                binding.setDetails(data);
                setSlider(data.getPictures());
                binding.setProgress(false);
            }
        });
//
//        OnBackPressedCallback callback=new OnBackPressedCallback(true) {
//
//            @Override
//            public void handleOnBackPressed() {
//
//                //   Navigation.findNavController(getView()).navigate(R.id.action_chat_to_announcement);
//                try {
//                    if (desteniion.equals("add")){
//                        Navigation.findNavController(getView()).navigate(R.id.action_fragmentShowEdit_to_fragmentMyAnnouncment2);
//                    }else {
//                        Navigation.findNavController(getView()).navigateUp();
//                    }
//                }catch (Exception e){
//                    Navigation.findNavController(view).navigateUp();
//                }
//
//
//
//
//            };
//
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(),callback);

    }
    public void setSlider(List<String> pic){
        sliderAdaptor=new ImageSliderAdaptor(pic);
        sliderView.setSliderAdapter(sliderAdaptor);
    }


}