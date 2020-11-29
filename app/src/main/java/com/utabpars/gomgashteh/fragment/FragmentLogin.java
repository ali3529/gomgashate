package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

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
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentLoginBinding;
import com.utabpars.gomgashteh.model.RmModel;
import com.utabpars.gomgashteh.viewmodel.UserAuthenticationViewModel;

public class FragmentLogin extends Fragment {
    FragmentLoginBinding binding;
    UserAuthenticationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);
        viewModel=new ViewModelProvider(this).get(UserAuthenticationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setSendotp(this);


        viewModel.phoneNumberResponseLiveData.observe(getViewLifecycleOwner(), new Observer<RmModel>() {
            @Override
            public void onChanged(RmModel rmModel) {

                    Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
                    binding.setProgress(false);
                    Bundle bundle=new Bundle();
                    bundle.putString("phone_num",binding.inputPhonenumber.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.action_fragmentLogin_to_fragmentOtp,bundle);


            }
        });


    }
    public void sendOtp(){
        if (binding.inputPhonenumber.getText().toString().length()==11){
            viewModel.userAuthentication(binding.inputPhonenumber.getText().toString());
            binding.setProgress(true);
        }else
            Toast.makeText(getContext(), "لطفا شماره همراه را وارد کنید", Toast.LENGTH_SHORT).show();


    }
    public void backStack(){
        Navigation.findNavController(getView()).popBackStack();
        Toast.makeText(getContext(), "sdfgestggzsd", Toast.LENGTH_SHORT).show();
    }
}