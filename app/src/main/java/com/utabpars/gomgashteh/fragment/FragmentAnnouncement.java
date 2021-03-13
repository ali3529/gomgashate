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
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.utabpars.gomgashteh.R;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncementBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.maincity.FragmentChoosecity;
import com.utabpars.gomgashteh.maincity.SelectetdCityAdaptor;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.AppVersionModel;
import com.utabpars.gomgashteh.model.RmModel;
import com.utabpars.gomgashteh.paging.AnnouncementViewModel;
import com.utabpars.gomgashteh.paging.ItemDataSource;
import com.utabpars.gomgashteh.paging.PagingAdaptor;
import com.utabpars.gomgashteh.paging.provinceFilter.ProvinceFilterDataSource;

import com.utabpars.gomgashteh.utils.Utils;
import com.utabpars.gomgashteh.viewmodel.CheckUpdateViewModel;
import com.utabpars.gomgashteh.paging.provinceFilter.FilterAnouncmentByProvinceViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentAnnouncement extends Fragment implements DetileCallBack {
    RecyclerView recyclerView;

    PagingAdaptor adaptor;
    FragmentAnnouncementBinding binding;
    AnnouncementViewModel viewModel;
    CheckUpdateViewModel updateViewModel;
    MutableLiveData<AppVersionModel> appVersionModelMutableLiveData;
    boolean  province_state=true,type_state=true;
     String type="";

    MutableLiveData<String> typeLiveData=new MutableLiveData<>();
    boolean lost_selected=false,fins_selected=false;
    TextView searchView;
    SharedPreferences shPref;

    FilterAnouncmentByProvinceViewModel provinceViewModel;
    ProvinceFilterDataSource provinceFilterDataSource;

    List<String> selectedCities=new ArrayList<>();
    MutableLiveData<Boolean>liveData=new MutableLiveData<>();
    CityDatabase cityDatabase;

    Parcelable stareee;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AnnouncementViewModel.class);

        // Inflate the layout for this fragment
        initViews();
        shPref = getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        //   ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        updateViewModel = new ViewModelProvider(this).get(CheckUpdateViewModel.class);
        cityDatabase=CityDatabase.getInstance(getContext());
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

        if (type.equals("1")){
            findSelected(true,false);
        }else if (type.equals("2")){
            lostSelected(true,false);
        }



        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("type",type);
                Navigation.findNavController(view).navigate(R.id.action_announcement_to_fragmentSearch,bundle);
            }
        });




        binding.layoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("navigate", "choose");
                Navigation.findNavController(view).navigate(R.id.action_announcement_to_fragmentChoosecity, bundle);
                province_state=true;
                type_state=true;
                Log.d("hmmjmmmmmm", "onClick: ");
            }
        });



        //setAnounsmentFilter();

        setCityForFilterAnnouncement();

//setAnounsmentFilter();
        recyclerView.setAdapter(adaptor);



        //when go to last item item dont underbottom navigation

        lastAnnouncmentAboveBtNavigation();


        binding.lost.setOnClickListener(o ->{
            type_state=true;
            province_state=true;

            //         setFilterViews(1);
            if (lost_selected){
                lostSelected(false,true);
                lost_selected=false;
                typeLiveData.setValue("");
            }else {
                typeLiveData.setValue("2");
            }


        });

        binding.find.setOnClickListener(o ->{
            type_state=true;
            province_state=true;

            //  setFilterViews(2);
            if (fins_selected){
                findSelected(false,true);
                fins_selected=false;
                typeLiveData.setValue("");
            }else {
                typeLiveData.setValue("1");

            }

        });


        typeLiveData.observe(getViewLifecycleOwner(),t->{
                Log.d("kkkkkkkkkkkkkkk", "onViewCreated: ");
                type = t;
                if (t.equals("1")) {
                    findSelected(true, false);
                    lostSelected(false, false);
                    fins_selected = true;
                    lost_selected = false;
                } else if (t.equals("2")) {
                    findSelected(false, false);
                    lostSelected(true, false);
                    lost_selected = true;
                    fins_selected = false;
                }

        });

        binding.srefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (selectedCities.size()!=0 || type.length()>0){
                    provinceViewModel.refreshData();
                }else {
                    viewModel.refresh();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.srefresh.setRefreshing(false);
                    }
                },2000);
            }
        });


    }

//    private void setCityForFilterAnnouncement() {
//
//        cityDatabase.cityDao().getCitySelectedForFilterAnnounce().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(selectetd_city->{
//                    selectedCities.clear();
//                    if (selectetd_city.size()==0){
//                        binding.setCity("انتخاب");
//                    }else {
//                        binding.setCity(selectetd_city.size()+"شهر");
//                    }
//                    for (City city:selectetd_city) {
//                        selectedCities.add(city.getCity_id());
//                        Log.d("fbfdnfdغn", "onViewCreated: "+city.getCity_id());
//                    }
//                    setAnounsmentFilter();
//                });


    //}

     private void setCityForFilterAnnouncement() {

         List<City> cities=cityDatabase.cityDao().getCitySelectedForFilterAnnounce();

                     selectedCities.clear();
                     if (cities.size() == 0) {
                         binding.setCity("انتخاب");
                     } else {
                         binding.setCity(cities.size() + "شهر");
                     }
                     for (City city : cities) {
                         selectedCities.add(city.getCity_id());
                         Log.d("fbfdnfdغn", "onViewCreated: " + city.getCity_id());
                     }
                     setAnounsmentFilter();

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

    private void setAnounsmentFilter() {

        if (selectedCities.size()!=0) {
            // Log.d("dvdsvvvd", "setAnounsmentFilter if: ");
            Log.d("dvdsvvvdccc", "setAnounsmentFilter if: "+selectedCities.get(0));
            Log.d("dvdsvvvdccc", "setAnounsmentFilter ---type: "+type);
            provinceFilterDataSource.FilterDataSource(selectedCities.toString(), type, "", emptyAnnouncementCallBack);
            if (province_state) {

                provinceViewModel.getProvinceFilter();
                province_state=false;

                Log.d("dvdsvvvdccc", "setAnounsmentFilter iftype----: "+type);
            }
            Log.d("dvdsvvvdccc", "setAnounsmentFilter outtype----: "+type);
            provinceViewModel.listLiveData.observe(getViewLifecycleOwner(), province -> {
                adaptor.submitList(province);

            });

            binding.emptyAnnounce.setVisibility(View.GONE);
        } else if (type.length()>0){
            Log.d("dvdsvvvd", "setAnounsmentFilter try  if---: ");
            Log.d("dvdsvvvd", "setAnounsmentFilter try  ifggggg---: "+selectedCities.toString());
            Log.d("dvdsvvvd", "setAnounsmentFilter try  if---: "+selectedCities.size());
            if (type_state){
                provinceFilterDataSource.FilterDataSource("",type,"",emptyAnnouncementCallBack);
                provinceViewModel.getProvinceFilter();
                type_state=false;
                province_state=false;

                Log.d("DSvsdvdsvdsvsdv", "setAnounsmentFilter: ");
            }else {
                Log.d("DSvsdvdsvdsvsdv", "setAnounsmentFilter: else");
            }


            provinceViewModel.listLiveData.observe(getViewLifecycleOwner(), province -> {

                adaptor.submitList(province);
            });

            binding.emptyAnnounce.setVisibility(View.GONE);
        } else {

                //viewModel.getAnnouncement();

            viewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
                @Override
                public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {

                        adaptor.submitList(detiles);
                        Log.d("dvdsvvvd", "setAnounsmentFilter try  else---: ");

                    {
                        Log.d("dvdsvvvd", "setAnounsmentFilter try  else---  rrrrrrrr: ");

                    }
                }

            });
            binding.emptyAnnounce.setVisibility(View.GONE);
        }

    }


    private void initViews() {
        recyclerView = binding.recycler;
        searchView=binding.search;
        provinceViewModel=new ViewModelProvider(getActivity()).get(FilterAnouncmentByProvinceViewModel.class);
        provinceFilterDataSource = new ProvinceFilterDataSource();



    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        stareee=recyclerView.getLayoutManager().onSaveInstanceState();
//        outState.putParcelable("SAVE",stareee);
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState!=null)
//        stareee=savedInstanceState.getParcelable("SAVE");
//    }
//
//        @Override
//    public void onResume() {
//        super.onResume();
//        if (stareee != null) {
//            recyclerView.getLayoutManager().onRestoreInstanceState(stareee);
//        }
//    }

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

                        appUpdateAlert(appVersionModel.getMessage(),true);
                    } else{

                        appUpdateAlert(appVersionModel.getMessage(),false);
                    }
                }
            }
        });
    }

    public void findSelected(boolean selected,boolean z){
        if (selected) {
            binding.find.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.item_shape_selected));
            binding.find.setTextColor(getContext().getResources().getColor(R.color.white));
            type="1";
            setAnounsmentFilter();
        }else {
            binding.find.setTextColor(getContext().getResources().getColor(R.color.text_color_black));
            binding.find.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_filter_item));

            if (z){
                type="";
                setAnounsmentFilter();
            }
        }
        Log.d("gfhgffgnt", "findSelected: "+type);
    }
    public void lostSelected(boolean selected,boolean z){
        if (selected) {
            binding.lost.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_filter_item_selected));
            binding.lost.setTextColor(getContext().getResources().getColor(R.color.white));
            type="2";
            setAnounsmentFilter();
        }else {
            binding.lost.setTextColor(getContext().getResources().getColor(R.color.text_color_black));
            binding.lost.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.item_left_shape));
            if (z){
                type="";
                setAnounsmentFilter();

            }
        }
        Log.d("gfhgffgnt", "lostSelected: "+type);
    }

    public void appUpdateAlert(String massage, boolean is_force){
        LayoutInflater inflater = this.getLayoutInflater();
        View help_layout = inflater.inflate(R.layout.alert_dialog_update, null);
        TextView massages= help_layout.findViewById(R.id.massage_update);
        Button update= help_layout.findViewById(R.id.btn_update);
        Button cancel= help_layout.findViewById(R.id.btn_cancle);
        massages.setText(massage);
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(help_layout);

        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        update.setOnClickListener(o->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://gomgashteh.com/dl/"));
            startActivity(intent);
        });

        if (!is_force) {
            cancel.setText("آلان نه");
            cancel.setOnClickListener(o->{
                alertDialog.dismiss();
            });

        } else {
            cancel.setText("خروج");
            builder.setCancelable(false);
            cancel.setOnClickListener(o->{
                getActivity().finish();
            });



        }

    }


    ProvinceFilterDataSource.EmptyAnnouncement emptyAnnouncementCallBack=new ProvinceFilterDataSource.EmptyAnnouncement() {
        @Override
        public void onEmptyAnnouncement() {
            binding.emptyAnnounce.setVisibility(View.VISIBLE);
        }
    };


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                getActivity().finish();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}

