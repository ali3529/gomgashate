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
import android.view.WindowManager;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentOtpBinding;
import com.utabpars.gomgashteh.interfaces.LoginRespondeCallBack;
import com.utabpars.gomgashteh.model.RegisterModel;
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_otp,container,false);
        viewModel=new ViewModelProvider(this).get(UserAuthenticationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding.setSendotp(this);
        sharedPreferences=getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);
        String phone_num=getArguments().getString("phone_num");
        //String phone_num="09337067554";
       // boolean is_validate=true;
        boolean is_validate=getArguments().getBoolean("is_validate");


        binding.ttt.setText("کد تایید پیامک شده به "+" "+phone_num+" "+"وارد کنید");
        viewModel.phoneNumberInterface(loginRespondeCallBack);
        if (is_validate) {


            binding.validateOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.inputOtp.getText().toString().length() == 4) {
                        validateOtp(phone_num, binding.inputOtp.getText().toString());
                        binding.setProgress(true);
                    } else
                        Toast.makeText(getContext(), "کد تایید نا معتبر", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            binding.layoutRegister.setVisibility(View.VISIBLE);

           binding.validateOtp.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (binding.inputOtp.getText().toString().length() == 4) {
                       if (binding.inputFirstname.getText().toString().length()>0){
                           if (binding.inputLastname.getText().toString().length()>0){
                               register(phone_num,
                                       binding.inputOtp.getText().toString(),
                                       binding.inputFirstname.getText().toString(),
                                       binding.inputLastname.getText().toString());



                               binding.setProgress(true);

                           }else
                               Toast.makeText(getContext(), "نام خانوادگی خود را وارد کنید", Toast.LENGTH_SHORT).show();

                       }else
                           Toast.makeText(getContext(), "نام خود را وارد کنید", Toast.LENGTH_SHORT).show();

                   } else
                       Toast.makeText(getContext(), "کد تایید نا معتبر", Toast.LENGTH_SHORT).show();
               }
           });
        }



        viewModel.phoneNumberResponseLiveData.observe(getViewLifecycleOwner(), new Observer<RmModel>() {
            @Override
            public void onChanged(RmModel rmModel) {
                binding.setProgress(false);
            }
        });

        viewModel.otpResponseLiveData.observe(getViewLifecycleOwner(), new Observer<RegisterModel>() {
            @Override
            public void onChanged(RegisterModel rmModel) {
                if (rmModel.getResponse().equals("1")){


                Toast.makeText(getContext(), rmModel.getMessage(), Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("user_login",true);
                    editor.putString("user_id",String.valueOf(rmModel.getData().getId()));
                    editor.putString("phone_num",rmModel.getData().getPhone_num());
                    editor.putString("first_name",rmModel.getData().getFirst_name());
                    editor.putString("last_name",rmModel.getData().getLast_name());
                editor.apply();
                binding.setProgress(false);
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentOtp_to_perofile);

                }else if (rmModel.getResponse().equals("2")){
                    Toast.makeText(getContext(), rmModel.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.setProgress(false);
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

        viewModel.registerUserLiveData.observe(getViewLifecycleOwner(), new Observer<RegisterModel>() {
            @Override
            public void onChanged(RegisterModel rmModel) {
                if (rmModel.getResponse().equals("1")){


                Toast.makeText(getContext(), rmModel.getMessage(), Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("user_login",true);
                editor.putString("user_id",String.valueOf(rmModel.getData().getId()));
                editor.putString("phone_num",rmModel.getData().getPhone_num());
                editor.putString("first_name",rmModel.getData().getFirst_name());
                editor.putString("last_name",rmModel.getData().getLast_name());
                editor.apply();
                binding.setProgress(false);
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentOtp_to_perofile);

                }else{
                    Toast.makeText(getContext(), rmModel.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.setProgress(false);
                }
            }
        });

        viewModel.error.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.setProgress(false);
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });




    }


    public void backstack(){
     Navigation.findNavController(getView()).navigateUp();
    }

    public void validateOtp(String phone,String code){
        viewModel.validateOtp(phone,code);
    }

    public void register(String phone,String code,String name,String lastName){

        viewModel.registerUser(phone,code,name,lastName);
    }
    LoginRespondeCallBack loginRespondeCallBack=new LoginRespondeCallBack() {
        @Override
        public void otpCallback(RmModel rmModel) {
            Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
            binding.setProgress(false);
        }
    };
}