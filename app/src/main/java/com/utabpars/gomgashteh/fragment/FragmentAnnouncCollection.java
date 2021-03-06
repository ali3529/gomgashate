package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

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
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncCollectionBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.paging.PagingAdaptor;
import com.utabpars.gomgashteh.paging.filterpaging.FilterAnouncmentViewModel;
import com.utabpars.gomgashteh.paging.filterpaging.FilterItemDataSource;

public class FragmentAnnouncCollection extends Fragment implements DetileCallBack {
    FragmentAnnouncCollectionBinding binding;
    RecyclerView recyclerView;
    FilterItemDataSource filterItemDataSource;
    FilterAnouncmentViewModel viewModel;
    PagingAdaptor pagingAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_announc_collection,container,false);
        initViews();
        viewModel= new ViewModelProvider(this).get(FilterAnouncmentViewModel.class);
        // Inflate the layout for this fragment
        Log.d("dvdsvthopoi", "onCreateView: "+"collection");
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id=getArguments().getString("id");
        String type=getArguments().getString("type");
        String title=getArguments().getString("title");
      //  Toast.makeText(getContext(), ""+id+"ppp", Toast.LENGTH_SHORT).show();
        binding.toolbartitle.setText(title);
        filterItemDataSource=new FilterItemDataSource();
        filterItemDataSource.getCallectionId(id,type);
        filterItemDataSource.getBind(binding);
        binding.setViemodel(viewModel);
        pagingAdaptor=new PagingAdaptor();
        pagingAdaptor.getDEtail(this::onItemClicked);

        viewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
            @Override
            public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {

                pagingAdaptor.submitList(detiles);
            }
        });
        recyclerView.setAdapter(pagingAdaptor);

        lastAnnouncmentAboveBtNavigation();
    }

    private void initViews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClicked(View view, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        Navigation.findNavController(view).navigate(R.id.action_fragmentAnnouncCollection_to_fragmentAnnouncmentDetail, bundle);
    }

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