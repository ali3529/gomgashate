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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.utabpars.gomgashteh.R;

import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncementBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
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
    Toolbar toolbar;
    String type="",test="";
    static int type_for_view=0;

    TextView searchView;
    SharedPreferences shPref;

    FilterAnouncmentByProvinceViewModel provinceViewModel;
    ProvinceFilterDataSource provinceFilterDataSource;

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
        setFilterViews(type_for_view);


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
                Bundle bundle=new Bundle();
                bundle.putString("type",type);
                Navigation.findNavController(view).navigate(R.id.action_announcement_to_fragmentSearch,bundle);
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


        //when go to last item item dont underbottom navigation
        lastAnnouncmentAboveBtNavigation();


        binding.lost.setOnClickListener(o ->{

            setFilterViews(1);

        });

        binding.find.setOnClickListener(o ->{

            setFilterViews(2);
        });


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

                //todo

                Log.d("Sgsgsgse", "onViewCreated: getcity ok "+getMainCity().toString());



                provinceFilterDataSource.FilterDataSource(getMainCity().toString(),type,"");
                provinceViewModel.getProvinceFilter();
                Log.d("Sgsgsgse", "setAnounsmentFilter: "+type);


                provinceViewModel.listLiveData.observe(getViewLifecycleOwner(), province -> {



                    adaptor.submitList(province);

                });


            }
        }catch (Exception e){

            if (type.length()>0){

                provinceFilterDataSource.FilterDataSource("",type,"");
                provinceViewModel.getProvinceFilter();


                provinceViewModel.listLiveData.observe(getViewLifecycleOwner(), province -> {



                    adaptor.submitList(province);

                    Log.d("Sgsgsgse", "onViewCreated: getcity null type ok "+type);

                });


            }
            else {
                viewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
                    @Override
                    public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {
                        adaptor.submitList(detiles);


                    }
                });
                Log.d("Sgsgsgse", "onViewCreated: getcity null type null  all "+type);
            }


        }

//        ApiInterface apiInterface= ApiClient.getApiClient();
//        CompositeDisposable compositeDisposable=new CompositeDisposable();
//        List<String> list=new ArrayList<>();
//        list.add("17");
//        compositeDisposable.add(apiInterface.filterAnnouncement(getMainCity().toString())
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
//            @Override
//            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
//                Log.d("sgsdfsdf", "onSuccess: ");
//            }
//
//            @Override
//            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                Log.d("sgsdfsdf", "onError: "+e.toString());
//            }
//        }));
    }


    private void initViews() {
        recyclerView = binding.recycler;
        toolbar=binding.toolbar;
        searchView=binding.search;
        provinceViewModel=new ViewModelProvider(getActivity()).get(FilterAnouncmentByProvinceViewModel.class);
        provinceFilterDataSource = new ProvinceFilterDataSource();



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

    public void setFilterViews(int i){
        if (i==1){
            binding.lost.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_filter_item_selected));
            binding.find.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_filter_item));
            binding.lost.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.find.setTextColor(getContext().getResources().getColor(R.color.text_color_black));
            type_for_view=1;


            if (type.equals("2")){
                binding.lost.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.item_left_shape));
                binding.lost.setTextColor(getContext().getResources().getColor(R.color.text_color_black));
                type="";

            }else {
                type="2";
            }



            setAnounsmentFilter();
        }else if (i==2){
            binding.find.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.item_shape_selected));
            binding.lost.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.item_left_shape));
            binding.find.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.lost.setTextColor(getContext().getResources().getColor(R.color.text_color_black));

            type_for_view=2;
            if (type.equals("1")){
                binding.find.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_filter_item));
                binding.find.setTextColor(getContext().getResources().getColor(R.color.text_color_black));
                type="";
            }else {
                type="1";

            }



            setAnounsmentFilter();
        }
    }

}