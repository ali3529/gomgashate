package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.ChatsAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentChatBinding;
import com.utabpars.gomgashteh.interfaces.ChatOnclick;
import com.utabpars.gomgashteh.model.ChatsModel;
import com.utabpars.gomgashteh.viewmodel.ChatNotificationViewModel;
import com.utabpars.gomgashteh.viewmodel.ChatsViewModel;


public class FragmentChat extends Fragment {
    FragmentChatBinding binding;
    SharedPreferences sharedPreferences;
    ChatsViewModel chatsViewModel;
    String user_id;
    ChatsAdaptor chatsAdaptor;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ChatNotificationViewModel chatNotificationViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_chat,container,false);
        sharedPreferences=getActivity().getSharedPreferences("user_login",Context.MODE_PRIVATE);
        chatNotificationViewModel=new ViewModelProvider(this).get(ChatNotificationViewModel.class);
        chatsViewModel=new ViewModelProvider(this).get(ChatsViewModel.class);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        recyclerView=binding.chatRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        progressBar=binding.progressbar;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean is_login=sharedPreferences.getBoolean("user_login",false);
        user_id=sharedPreferences.getString("user_id","0");

        if (!is_login){
            binding.login.setVisibility(View.VISIBLE);
            binding.chatRecyclerview.setVisibility(View.GONE);
            binding.systemTicket.setVisibility(View.GONE);

        }else {
            binding.chatRecyclerview.setVisibility(View.VISIBLE);
            binding.systemTicket.setVisibility(View.VISIBLE);
            chatsViewModel.getTickets(user_id);
            progressBar.setVisibility(View.VISIBLE);
            chatNotificationViewModel.getChatNotification(user_id);
            Log.d("bdfbfdbdf", "onChanged: logined "+user_id);
        }


        binding.chatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_chat_to_fragmentLogin);
            }
        });
        chatNotificationViewModel.chatCounterMutableLiveData.observe(getViewLifecycleOwner(),notification->{
            if (notification==0){
                BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottomnav);
                BadgeDrawable badge= bottomNavigationView.getOrCreateBadge(R.id.chat);
                badge.setVisible(false);

            }else {
                BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottomnav);
                BadgeDrawable badge= bottomNavigationView.getOrCreateBadge(R.id.chat);
                badge.isVisible();
                badge.setNumber(notification);
            }
        });


        chatsViewModel.chatsModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ChatsModel>() {
            @Override
            public void onChanged(ChatsModel chatsModel) {
                Log.d("bdfbfdbdf", "onChanged: ");
                progressBar.setVisibility(View.GONE);
                chatsAdaptor=new ChatsAdaptor(chatsModel.getTicketList(),chatOnclick);
                recyclerView.setAdapter(chatsAdaptor);

                if (chatsModel.getSystem_messages().equals("0")){
                    binding.notificationsBadge.setVisibility(View.GONE);
                }else {

                    if (Integer.parseInt(chatsModel.getSystem_messages())>10){
                        binding.notificationsBadge.setText("9+");
                    }else {
                        binding.notificationsBadge.setText(chatsModel.getSystem_messages());
                    }

                }

              
            }
        });

        lastAnnouncmentAboveBtNavigation();
       // BadgeDrawable badgeDrawable= getActivity().findViewById(R.id.bottomnav).getbad
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottomnav);
        BadgeDrawable badge= bottomNavigationView.getOrCreateBadge(R.id.chat);
        bottomNavigationView.removeBadge(R.id.chat);

        binding.systemTicket.setOnClickListener(o->{
            Bundle bundle=new Bundle();
            bundle.putString("user_id",user_id);
            Navigation.findNavController(o).navigate(R.id.action_chat_to_fragmentSystemTickets,bundle);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

             //   Navigation.findNavController(getView()).navigate(R.id.action_chat_to_announcement);
                Navigation.findNavController(getView()).navigate(R.id.action_chat_to_announcement);


            };

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);

    }

    ChatOnclick chatOnclick=new ChatOnclick() {
        @Override
        public void onChatItemClicked(View view, String ticketId,String title,String announcer_id,String announcement_id,boolean block) {
            Bundle bundle=new Bundle();
            bundle.putString("ticket_id",ticketId);
            bundle.putString("user_id",user_id);
            bundle.putString("title",title);
            bundle.putString("recever_id",announcer_id);
            bundle.putString("announcer_id",announcement_id);
            bundle.putBoolean("block",block);
            Log.d("ticketid", "onChatItemClicked: ticketid"+ticketId);
            Navigation.findNavController(view).navigate(R.id.action_chat_to_fragmentChatDetail,bundle);
        }
    };
    private void lastAnnouncmentAboveBtNavigation() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                binding.gggg.setVisibility(View.GONE);
                LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemcount=layoutManager.getItemCount();
                int lastvisi=layoutManager.findLastVisibleItemPosition();
                Log.d("dsgfdgfdg", "onScrolled: "+itemcount);
                Log.d("dsgfdgfdg", "onScrolled: "+lastvisi);
                if (lastvisi==itemcount-1){
                    Log.d("dsgfdgfdg", "last: ");
                    binding.gggg.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}