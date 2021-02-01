package com.utabpars.gomgashteh.myannouncment;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentMyAnnouncmentBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;

public class FragmentMyAnnouncment extends Fragment {
    FragmentMyAnnouncmentBinding binding;
    MyAnnouncmentViewModel viewModel;
    RecyclerView recyclerView;
    MyAnnouncmentAdaptor adaptor;
    SharedPreferences sharedPreferences;
    String user_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_announcment,container,false);
        sharedPreferences=getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);
        viewModel=new ViewModelProvider(this).get(MyAnnouncmentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        user_id=sharedPreferences.getString("user_id","000");
        viewModel.MyAnnouncment(user_id);
        binding.progressbar.setVisibility(View.VISIBLE);

        viewModel.myAnnouncmentLiveData.observe(getViewLifecycleOwner(), new Observer<AnoncmentModel>() {
            @Override
            public void onChanged(AnoncmentModel anoncmentModel) {
                binding.progressbar.setVisibility(View.GONE);
                if (anoncmentModel.getData().isEmpty()){
                    binding.emty.setVisibility(View.VISIBLE);
                }else {
                    adaptor=new MyAnnouncmentAdaptor(anoncmentModel.getData(),detileCallBack);
                    recyclerView.setAdapter(adaptor);
                }

            }
        });

        binding.arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        lastAnnouncmentAboveBtNavigation();
    }

    DetileCallBack detileCallBack=new DetileCallBack() {
        @Override
        public void onItemClicked(View view, int id) {
            Bundle bundle=new Bundle();
            bundle.putInt("id",id);
            Navigation.findNavController(view).navigate(R.id.action_fragmentMyAnnouncment2_to_fragmentOptions,bundle);
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
                if (lastvisi==itemcount-1){
                    binding.gggg.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}