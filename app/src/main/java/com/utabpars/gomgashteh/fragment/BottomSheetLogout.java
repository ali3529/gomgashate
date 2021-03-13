package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.LogoutBootonsheetBinding;


public class BottomSheetLogout extends BottomSheetDialogFragment {
    LogoutBootonsheetBinding binding;
    RelativeLayout bottonshetLayout;
    BottomSheetBehavior bottomSheetBehavior;
  public   MutableLiveData<Boolean> isLogOut=new MutableLiveData<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.logout_bootonsheet,container,false);
        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        bottonshetLayout=binding.sheetlatout;
        bottomSheetBehavior=BottomSheetBehavior.from(bottonshetLayout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        binding.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isLogOut.setValue(true);
                Toast.makeText(getContext(), "خارج شدید", Toast.LENGTH_SHORT).show();
            }
        });


        binding.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogOut.postValue(false);
            }
        });

    }
}