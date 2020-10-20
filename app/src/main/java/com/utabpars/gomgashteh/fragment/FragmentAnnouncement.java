package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncementBinding;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.ProgressModel;
import com.utabpars.gomgashteh.paging.AnnouncementViewModel;
import com.utabpars.gomgashteh.paging.ItemDataSource;
import com.utabpars.gomgashteh.paging.PagingAdaptor;

public class FragmentAnnouncement extends Fragment {
    RecyclerView recyclerView;
    PagingAdaptor adaptor;
    FragmentAnnouncementBinding binding;
    AnnouncementViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_announcement,container,false);
        viewModel= new ViewModelProvider(this).get(AnnouncementViewModel.class);
        // Inflate the layout for this fragment
        initViews();

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Navigation.findNavController(view).popBackStack(R.id.action_fragmentSplash_to_fragmentAnnouncement,false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adaptor=new PagingAdaptor();

        ItemDataSource itemDataSource=new ItemDataSource();
        itemDataSource.getbind(binding,getContext());
        binding.setLifecycleOwner(this);

        viewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
            @Override
            public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {
                adaptor.submitList(detiles);


            }
        });

        recyclerView.setAdapter(adaptor);
    }

    private void initViews() {
        recyclerView=binding.recycler;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                getActivity().finish();


            };

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}