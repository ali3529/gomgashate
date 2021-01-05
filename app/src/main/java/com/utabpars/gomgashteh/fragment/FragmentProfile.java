package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentProfileBinding;

public class FragmentProfile extends Fragment {
    FragmentProfileBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MutableLiveData<Boolean> log_status=new MutableLiveData<>();
    BottomSheetLogout bottomSheetLogout=new BottomSheetLogout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        sharedPreferences=getActivity().getSharedPreferences("user_login",Context.MODE_PRIVATE);
       editor=sharedPreferences.edit();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id=sharedPreferences.getString("user_id",null);
        boolean isLogin=sharedPreferences.getBoolean("user_login",false);
        String phobe_num=sharedPreferences.getString("phone_num","0");
        log_status.setValue(isLogin);


        log_status.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                    if (aBoolean){
                        binding.title.setText(" شما با شماره موبایل "+phobe_num+" وارد شده اید و آگهی های ثبت شده با این شماره را مشاهده می کنید");
                        binding.textLog.setText("خروج از حساب");
                        binding.loginLayout.setVisibility(View.VISIBLE);
                    }else {
                        binding.title.setText("برای استفاده از تمام امکانات گمگشته مانند ثبت آگهی و چت وارد حساب گمگشته خود شوید");
                        binding.loginLayout.setVisibility(View.GONE);
                        binding.textLog.setText("ورود به حساب");
                    }

            }
        });



        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (log_status.getValue()){
                    bottomSheetLogout.show(getActivity().getSupportFragmentManager(),"logout");
//                    log_status.setValue(false);
//                    editor.putBoolean("user_login",false);
//                    editor.apply();
                }else {
                    Navigation.findNavController(view).navigate(R.id.action_perofile_to_fragmentLogin);
                }
            }
        });

        bottomSheetLogout.isLogOut.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    log_status.setValue(false);
                    editor.putBoolean("user_login",false);
                    editor.apply();
                    bottomSheetLogout.dismiss();


                }else{
                    bottomSheetLogout.dismiss();
                }
            }
        });


        binding.announcment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_perofile_to_fragmentMyAnnouncment2);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_perofile_to_fragmentMark);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                Navigation.findNavController(getView()).navigate(R.id.action_perofile_to_announcement);


            };

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }
}