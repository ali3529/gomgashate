package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentLoginBinding;
import com.utabpars.gomgashteh.interfaces.LoginRespondeCallBack;
import com.utabpars.gomgashteh.model.RmModel;
import com.utabpars.gomgashteh.viewmodel.UserAuthenticationViewModel;

public class FragmentLogin extends Fragment  {
    FragmentLoginBinding binding;
    UserAuthenticationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false);
        viewModel=new ViewModelProvider(this).get(UserAuthenticationViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setSendotp(this);
        binding.inputPhonenumber.setFocusable(true);
//        InputMethodManager inputMethodManager =
//                (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.toggleSoftInputFromWindow(
//                    binding.inputPhonenumber.getApplicationWindowToken(),
//                    InputMethodManager.SHOW_FORCED, 0);

viewModel.phoneNumberInterface(loginRespondeCallBack);

        viewModel.error.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.setProgress(false);
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
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



    LoginRespondeCallBack loginRespondeCallBack=new LoginRespondeCallBack() {
        @Override
        public void otpCallback(RmModel rmModel) {
            if (rmModel.getResponse().equals("1")){
                Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
                binding.setProgress(false);
                Bundle bundle=new Bundle();
                bundle.putString("phone_num",binding.inputPhonenumber.getText().toString());
                bundle.putBoolean("is_validate",true);
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentLogin_to_fragmentOtp,bundle);
                Log.d("drgdrggdr", "otpCallback: 1   -- "+rmModel.getResponse());
            }else if (rmModel.getResponse().equals("0")){
                Toast.makeText(getContext(), rmModel.getMassage(), Toast.LENGTH_SHORT).show();
                binding.setProgress(false);
                Bundle bundle=new Bundle();
                bundle.putString("phone_num",binding.inputPhonenumber.getText().toString());
                bundle.putBoolean("is_validate",false);
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentLogin_to_fragmentOtp,bundle);
                Log.d("drgdrggdr", "otpCallback: 2   -- "+rmModel.getResponse());
            }
        }
    };
}