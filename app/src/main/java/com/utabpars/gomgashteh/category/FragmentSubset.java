package com.utabpars.gomgashteh.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentSubsetBinding;

class FragmentSubset extends Fragment {
     FragmentSubsetBinding binding;
     SubsetViewModel subsetViewModel;
     RecyclerView recyclerView;
     CategoryAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_subset,container,false);
        subsetViewModel=new ViewModelProvider(this).get(SubsetViewModel.class);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        recyclerView=binding.subsetrecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subsetViewModel.getSubset("106");

        subsetViewModel.subsetMutableLiveData.observe(getViewLifecycleOwner(),t -> {
            adaptor=new CategoryAdaptor(t.getListData());
            recyclerView.setAdapter(adaptor);
        });

    }
}