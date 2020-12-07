package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentChatBinding;


public class FragmentChat extends Fragment {
    FragmentChatBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_chat,container,false);
        sharedPreferences=getActivity().getSharedPreferences("user_login",Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean is_login=sharedPreferences.getBoolean("user_login",false);

        if (!is_login){
            binding.login.setVisibility(View.VISIBLE);

        }else binding.chat.setVisibility(View.VISIBLE);


        binding.chatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_chat_to_fragmentLogin);
            }
        });



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                Navigation.findNavController(getView()).navigate(R.id.action_chat_to_announcement);


            };

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }
}