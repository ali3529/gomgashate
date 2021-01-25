package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.viewmodel.ChatNotificationViewModel;


public class FragmentSplash extends Fragment {
    ChatNotificationViewModel chatNotificationViewModel;
    SharedPreferences sharedPreferences;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        chatNotificationViewModel=new ViewModelProvider(this).get(ChatNotificationViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String user_id=sharedPreferences.getString("user_id","0");
        chatNotificationViewModel.getChatNotification(user_id);
        chatNotificationViewModel.chatCounterMutableLiveData.observe(getViewLifecycleOwner(),notification->{
            if (notification==0){

            }else {
                BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottomnav);
                BadgeDrawable badge= bottomNavigationView.getOrCreateBadge(R.id.chat);
                badge.isVisible();
                badge.setNumber(notification);
            }

        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.fragmentSplash, true)
                        .build();
                Navigation.findNavController(view).navigate(R.id.action_fragmentSplash_to_fragmentAnnouncement,null,navOptions);
            }
        },2000);


    }
}