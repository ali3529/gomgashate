package com.utabpars.gomgashteh.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.TopFilterAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncementBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.AppVersionModel;
import com.utabpars.gomgashteh.paging.AnnouncementViewModel;
import com.utabpars.gomgashteh.paging.ItemDataSource;
import com.utabpars.gomgashteh.paging.PagingAdaptor;
import com.utabpars.gomgashteh.utils.Utils;
import com.utabpars.gomgashteh.viewmodel.CheckUpdateViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentAnnouncement extends Fragment implements DetileCallBack {

    RecyclerView recyclerView,topFilterRecyclerview;
    PagingAdaptor adaptor;
    FragmentAnnouncementBinding binding;
    AnnouncementViewModel viewModel;
    CheckUpdateViewModel updateViewModel;
    MutableLiveData<AppVersionModel> appVersionModelMutableLiveData;
    Toolbar toolbar;
    public LiveData<PagedList<AnoncmentModel.Detile>> listLiveDataُSearch;
    static LiveData<PagedList<AnoncmentModel.Detile>> saveInstanceAnnounc;
    MaterialSearchBar searchView;
    SharedPreferences shPref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);
        viewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);
        // Inflate the layout for this fragment
        initViews();
        shPref = getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        updateViewModel = new ViewModelProvider(this).get(CheckUpdateViewModel.class);

        appVersionModelMutableLiveData = updateViewModel.getAppVersionModelLiveData();
        getAppVersion();


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Navigation.findNavController(view).popBackStack(R.id.action_fragmentSplash_to_fragmentAnnouncement, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adaptor = new PagingAdaptor();

        ItemDataSource itemDataSource = new ItemDataSource();
        itemDataSource.getbind(binding, getContext());
        adaptor.getDEtail(this);
        viewModel.getProg(binding);

        binding.setViemodel(viewModel);

        if (saveInstanceAnnounc==null){
            viewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
                @Override
                public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {
                    adaptor.submitList(detiles);
                    saveInstanceAnnounc=viewModel.listLiveData;


                }
            });

        }else {
            saveInstanceAnnounc.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
                @Override
                public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {
                    adaptor.submitList(detiles);

                }
            });
        }


        recyclerView.setAdapter(adaptor);


        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_announcement_to_fragmentSearch);
            }
        });


        TopFilterAdaptor topFilterAdaptor=new TopFilterAdaptor(setTopfilterData());
        topFilterRecyclerview.setAdapter(topFilterAdaptor);


        SharedPreferences preferences=getActivity().getSharedPreferences("cityName",Context.MODE_PRIVATE);
        binding.setCity(preferences.getString("city_name_announcment","انتخاب"));
        binding.layoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("navigate","choose");
                Navigation.findNavController(view).navigate(R.id.action_announcement_to_fragmentCity,bundle);
            }
        });
    }


    private void initViews() {
        recyclerView = binding.recycler;
        toolbar=binding.toolbar;
         searchView=binding.search;
        searchView.setPlaceHolder("جستجو در همه آگهی ها");

        topFilterRecyclerview=binding.topListRecyclerview;
        topFilterRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,LinearLayoutManager.HORIZONTAL));
        topFilterRecyclerview.setHasFixedSize(true);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                getActivity().finish();


            }

            ;

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    @Override
    public void onItemClicked(View view, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        Navigation.findNavController(view).navigate(R.id.action_announcement_to_fragmentAnnouncmentDetail2, bundle);
    }

    private void getAppVersion() {
        appVersionModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<AppVersionModel>() {
            @Override
            public void onChanged(AppVersionModel appVersionModel) {
                if (appVersionModel.getLast_app_version().equals(Utils.versionCode(getActivity()))) {


                } else {
                    if (appVersionModel.getIs_force() == 1) {
                        AppVersionAlertDialog(getContext(), appVersionModel.getMessage(), true);
                    } else AppVersionAlertDialog(getContext(), appVersionModel.getMessage(), false);
                }
            }
        });
    }

    private void AppVersionAlertDialog(Context context, String massage, boolean is_force) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(massage)
                .setCancelable(false)
                .setPositiveButton("آپدیت", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://chidashop.ir"));
                        startActivity(intent);
                    }
                });
        if (!is_force) {
            builder.setNegativeButton("آلان نه", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    builder.setCancelable(true);
                }
            });

        } else {
            builder.setCancelable(false);
            builder.setNegativeButton("خروج", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getActivity().finish();
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public List<String> setTopfilterData(){
        List<String> list=new ArrayList<>();
        list.add("پیدا شده");
        list.add("گم شده");
        list.add("کالا");
        list.add("شخص");
        list.add("مالی");
        list.add("اشیاء");
        list.add("لوازم الکترونیکی");
        list.add("حیوانات");

        return list;
    }

}