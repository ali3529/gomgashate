package com.utabpars.gomgashteh.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncementBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.AppVersionModel;
import com.utabpars.gomgashteh.model.ProgressModel;
import com.utabpars.gomgashteh.paging.AnnouncementViewModel;
import com.utabpars.gomgashteh.paging.ItemDataSource;
import com.utabpars.gomgashteh.paging.ItemDataSourceFactory;
import com.utabpars.gomgashteh.paging.PagingAdaptor;
import com.utabpars.gomgashteh.utils.Utils;
import com.utabpars.gomgashteh.viewmodel.CheckUpdateViewModel;

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
    public LiveData<PagedList<AnoncmentModel.Detile>> listLiveDataُSearch;
    static LiveData<PagedList<AnoncmentModel.Detile>> saveInstanceAnnounc;
    FloatingSearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_announcement, container, false);
        viewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);
        // Inflate the layout for this fragment
        initViews();
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




        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                searcgKey(oldQuery);
            }
        });


    }


    private void searcgKey(String query) {
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.Search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AnoncmentModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AnoncmentModel anoncmentModel) {
                        if (anoncmentModel.getResponse().equals("1")){
                            Toast.makeText(getContext(), ""+anoncmentModel.getData().size(), Toast.LENGTH_SHORT).show();

                        }else Toast.makeText(getContext(), "نا موفق", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                }));
    }

    private void initViews() {
        recyclerView = binding.recycler;
        toolbar=binding.toolbar;
         searchView=binding.search;
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

}