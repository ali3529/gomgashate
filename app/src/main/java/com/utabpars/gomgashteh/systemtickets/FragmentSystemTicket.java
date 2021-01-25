package com.utabpars.gomgashteh.systemtickets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentSystemTicketBinding;


public class FragmentSystemTicket extends Fragment {
    FragmentSystemTicketBinding binding;
    RecyclerView recyclerView;
    SystemTicketViewModel viewModel;
    SystemTicketAdaptor adaptor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_system_ticket,container,false);
        viewModel=new ViewModelProvider(this).get(SystemTicketViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String user_id=getArguments().getString("user_id");
        viewModel.getSystemTicket(user_id);
        recyclerView=binding.systemRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        viewModel.systemTicketModelMutableLiveData.observe(getViewLifecycleOwner(), t->{
adaptor=new SystemTicketAdaptor(t);
recyclerView.setAdapter(adaptor);
        });

        binding.arrowback.setOnClickListener(o->{
            Navigation.findNavController(o).navigateUp();
        });
    }
}