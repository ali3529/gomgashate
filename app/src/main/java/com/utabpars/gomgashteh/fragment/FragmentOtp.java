package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentOtpBinding;
import com.utabpars.gomgashteh.model.RmModel;
import com.utabpars.gomgashteh.viewmodel.UserAuthenticationViewModel;


public class FragmentOtp extends Fragment {
    FragmentOtpBinding binding;
    UserAuthenticationViewModel viewModel;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_otp,container,false);
        viewModel=new ViewModelProvider(this).get(UserAuthenticationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setSendotp(this);
        sharedPreferences=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        //String phone_num=getArguments().getString("phone_num");
        String phone_num="09179204186";

        binding.ttt.setText("کد تایید پیامک شده به "+" "+phone_num+" "+"وارد کنید");
        binding.validateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.inputOtp.getText().toString().length()==4){
                    validateOtp(phone_num,binding.inputOtp.getText().toString());
                    binding.setProgress(true);
                }else Toast.makeText(getContext(), "کد تایید نا معتبر", Toast.LENGTH_SHORT).show();

            }
        });

        viewModel.phoneNumberResponseLiveData.observe(getViewLifecycleOwner(), new Observer<RmModel>() {
            @Override
            public void onChanged(RmModel rmModel) {
                binding.setProgress(false);
            }
        });

        viewModel.otpResponseLiveData.observe(getViewLifecycleOwner(), new Observer<RmModel>() {
            @Override
            public void onChanged(RmModel rmModel) {
                if (rmModel.getResponse().equals("1")){


                Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("user_login",true);
                editor.apply();
                binding.setProgress(false);
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentOtp_to_perofile);
                }else if (rmModel.getResponse().equals("0")){
                    Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putString("phone_num",phone_num);
                    bundle.putString("otp_code",binding.inputOtp.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.action_fragmentOtp_to_fragmentRegister,bundle);
                }else if (rmModel.getResponse().equals("2")){
                    Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.timerOtp();
        viewModel.timerOtp.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                binding.timerOtp.setText(DateUtils.formatElapsedTime(aLong));
               // binding.setProgress(false);

            }
        });

        viewModel.timerOtpFinish.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.timerOtp.setText(s);

                binding.timerOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.timerOtp();
                        viewModel.userAuthentication(phone_num);
                        binding.setProgress(true);
                    }
                });
            }
        });


    }


    public void backstack(){
        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).popBackStack();
                Toast.makeText(getContext(), "saf", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void validateOtp(String phone,String code){
        viewModel.validateOtp(phone,code);
    }
}