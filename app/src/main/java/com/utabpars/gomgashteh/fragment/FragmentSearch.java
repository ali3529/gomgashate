package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.SearchAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentSearchBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentSearch extends Fragment  {
    FragmentSearchBinding binding;
    FloatingSearchView searchBar;
    RecyclerView searchRecyClerView;
    SearchAdaptor searchAdaptor;
    static AnoncmentModel Save_anoncmentModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);
        initViews();
        return binding.getRoot();

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        
        searchBar.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                Toast.makeText(getContext(), "exit", Toast.LENGTH_SHORT).show();
            }
        });
        searchBar.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                searcgKey(oldQuery);

            }
        });
    }

    private void initViews() {
        searchBar=binding.searchtwo;
        searchRecyClerView=binding.searchrecyclerview;
        searchRecyClerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyClerView.setHasFixedSize(true);
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
                            binding.setSearchvisibility(true);
                            Save_anoncmentModel=anoncmentModel;
                            searchAdaptor=new SearchAdaptor(anoncmentModel.getData(), new DetileCallBack() {
                                @Override
                                public void onItemClicked(View view, int id) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id", id);

                                    Navigation.findNavController(view).navigate(R.id.action_fragmentSearch_to_fragmentAnnouncmentDetail, bundle);
                                }
                            });
                            searchRecyClerView.setAdapter(searchAdaptor);

                        }else Toast.makeText(getContext(), "نا موفق", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                }));
    }


}