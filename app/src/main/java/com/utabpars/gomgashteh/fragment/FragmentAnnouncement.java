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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.TopFilterAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncementBinding;
import com.utabpars.gomgashteh.databinding.ItemFilterTopBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.interfaces.TopFilterCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.AppVersionModel;
import com.utabpars.gomgashteh.model.RmModel;
import com.utabpars.gomgashteh.paging.AnnouncementViewModel;
import com.utabpars.gomgashteh.paging.ItemDataSource;
import com.utabpars.gomgashteh.paging.PagingAdaptor;
import com.utabpars.gomgashteh.paging.provinceFilter.ProvinceFilterDataSource;
import com.utabpars.gomgashteh.paging.topfilterpaging.TopFilterDataSource;
import com.utabpars.gomgashteh.paging.topfilterpaging.TopFilterViewModelPaging;
import com.utabpars.gomgashteh.utils.Utils;
import com.utabpars.gomgashteh.viewmodel.CheckUpdateViewModel;
import com.utabpars.gomgashteh.paging.provinceFilter.FilterAnouncmentByProvinceViewModel;
import com.utabpars.gomgashteh.viewmodel.TopFilterViewModel;

import java.lang.reflect.Type;
import java.util.List;

public class FragmentAnnouncement extends Fragment implements DetileCallBack {
   public int itemCoun;
    RecyclerView recyclerView,topFilterRecyclerview;
    PagingAdaptor adaptor;
    FragmentAnnouncementBinding binding;
    AnnouncementViewModel viewModel;
    CheckUpdateViewModel updateViewModel;
    MutableLiveData<AppVersionModel> appVersionModelMutableLiveData;
    Toolbar toolbar;

    MaterialSearchBar searchView;
    SharedPreferences shPref;
    TopFilterViewModel topfilterViewModel;
    TopFilterDataSource topFilterDataSource;
    TopFilterViewModelPaging topFilterViewModelPaging;
    TopFilterAdaptor topFilterAdaptor;
    FilterAnouncmentByProvinceViewModel provinceViewModel;
    ProvinceFilterDataSource provinceFilterDataSource;
    static MutableLiveData<RmModel> rmModelMutableLiveDatattt;

    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AnnouncementViewModel.class);

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





        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_announcement_to_fragmentSearch);
            }
        });

        sharedPreferences=getActivity().getSharedPreferences("main_city",Context.MODE_PRIVATE);
        try {
            if (getMainCity()==null){
                binding.setCity( "انتخاب");
            }else {
                binding.setCity(getMainCity().size()+"شهر");
            }
        }catch (Exception e){

            binding.setCity( "انتخاب");
        }


        binding.layoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("navigate", "choose");
                Navigation.findNavController(view).navigate(R.id.action_announcement_to_fragmentChoosecity, bundle);
            }
        });

        //todo
        // province filter anouncmement
      setAnounsmentFilter();

        recyclerView.setAdapter(adaptor);


        topfilterViewModel.rmModelMutableLiveData.observe(getActivity(), topFilter -> {
            topFilterAdaptor = new TopFilterAdaptor(getContext(), topFilter.getTopFilterData(), new TopFilterCallBack() {
                @Override
                public void OnClickCallback(String id,boolean selected, ItemFilterTopBinding binding) {
                    if (selected) {


                    topFilterDataSource = new TopFilterDataSource(id);
                    topFilterViewModelPaging.topFilterd();

                    Log.d("sdfsdfdf", "onViewCreated: interface onclick");


                    topFilterViewModelPaging.listLiveData.observe(getViewLifecycleOwner(), detail -> {
                        adaptor.submitList(detail);

                        Log.d("sdfsdfdf", "onViewCreated: viewmodel");
                    });
                    }else {
                        setAnounsmentFilter();
                    }
                }
            });
            topFilterRecyclerview.setAdapter(topFilterAdaptor);

        });

        //when go to last item item dont underbottom navigation
        lastAnnouncmentAboveBtNavigation();


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
                Log.d("dsgfdgfdg", "onScrolled: "+itemcount);
                Log.d("dsgfdgfdg", "onScrolled: "+lastvisi);
                if (lastvisi==itemcount-1){
                    Log.d("dsgfdgfdg", "last: ");
                    binding.gggg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setAnounsmentFilter() {
        try {
            if (getMainCity()!=null) {



                Log.d("Sgsgsgse", "onViewCreated: "+getMainCity().toString());

                provinceFilterDataSource = new ProvinceFilterDataSource(getMainCity().toString());
                provinceViewModel.getProvinceFilter();


                provinceViewModel.listLiveData.observe(getViewLifecycleOwner(), province -> {



                    adaptor.submitList(province);

                });


            }else {
                viewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
                    @Override
                    public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {
                        adaptor.submitList(detiles);

                    }
                });


            }
        }catch (Exception e){

            viewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
                @Override
                public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {
                    adaptor.submitList(detiles);


                }
            });


        }
    }


    private void initViews() {
        recyclerView = binding.recycler;
        toolbar=binding.toolbar;
        searchView=binding.search;
        searchView.setPlaceHolder("جستجو در همه آگهی ها");

        topFilterRecyclerview=binding.topListRecyclerview;
        topFilterRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,LinearLayoutManager.HORIZONTAL));
        topFilterRecyclerview.setHasFixedSize(true);
        topfilterViewModel=new ViewModelProvider(this).get(TopFilterViewModel.class);
        topFilterViewModelPaging=new ViewModelProvider(this).get(TopFilterViewModelPaging.class);
        provinceViewModel=new ViewModelProvider(getActivity()).get(FilterAnouncmentByProvinceViewModel.class);



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
    public List<String> getMainCity(){
        Gson gson=new Gson();

        String s=sharedPreferences.getString("main_city","w");

        Type type=new TypeToken<List<String>>(){

        }.getType();

        List<String>  j=gson.fromJson(s,type);



        Log.d("sfesfsef", "getCategoryId: "+j.get(0));
        return j;
    }

}