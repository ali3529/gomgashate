package com.utabpars.gomgashteh.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentLoginAlertButtenSheetBinding;


public class LoginAlertBottomSheet extends BottomSheetDialogFragment {
    FragmentLoginAlertButtenSheetBinding binding;
    public MutableLiveData<View> viewMutableLiveData=new MutableLiveData<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_login_alert_butten_sheet,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setFrag(this);





    }

    public void login(){
        viewMutableLiveData.setValue(getView());
    }
}