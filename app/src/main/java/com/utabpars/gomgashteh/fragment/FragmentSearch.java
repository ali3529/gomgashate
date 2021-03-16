package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.SearchAdaptor;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.databinding.FragmentSearchBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.paging.PagingAdaptor;
import com.utabpars.gomgashteh.paging.keysearchpaging.KeyFilterDataSource;
import com.utabpars.gomgashteh.paging.keysearchpaging.KeyFilterDataSourceViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment  {
    FragmentSearchBinding binding;
    EditText searchBar;
    RecyclerView searchRecyClerView;
    SearchAdaptor searchAdaptor;
    ImageView backArrow,clear_text;
    static AnoncmentModel Save_anoncmentModel;
    SharedPreferences sharedPreferences;
    CityDatabase cityDatabase;
    KeyFilterDataSourceViewModel viewModel;
    PagingAdaptor adaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);
       getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        sharedPreferences=getActivity().getSharedPreferences("main_city",Context.MODE_PRIVATE);
        initViews();
        cityDatabase=CityDatabase.getInstance(getContext());
        viewModel=new ViewModelProvider(this).get(KeyFilterDataSourceViewModel.class);
        adaptor =new PagingAdaptor();
        return binding.getRoot();

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String type=getArguments().getString("type");
        InputMethodManager inputMethodManager =
                (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (searchBar.requestFocus()){
            inputMethodManager.toggleSoftInputFromWindow(
                    getView().getApplicationWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);
        }
        adaptor.getDEtail(detileCallBack);

        searchRecyClerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                return false;
            }
        });




        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!listCity().isEmpty()) {
                    searcgKey(listCity().toString(), type, charSequence.toString());
                    Log.d(" ", "onTextChanged: ");
                }else {
                    searcgKey("", type, charSequence.toString());
                    Log.d("fbfdbdfbdfb", "onTextChanged: else"+type);
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.getText().clear();
            }
        });

            viewModel.listLiveData.observe(getViewLifecycleOwner(), new Observer<PagedList<AnoncmentModel.Detile>>() {
                @Override
                public void onChanged(PagedList<AnoncmentModel.Detile> detiles) {

                    adaptor.submitList(detiles);
                    Log.d("sbvsdbsdbbds", "onViewCreated: livedata"+detiles.size());
                    searchRecyClerView.setAdapter(adaptor);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.progress.setVisibility(View.GONE);
                        }
                    },2000);
                    binding.layouterror.setVisibility(View.GONE);

                }
            });


    }

    private void initViews() {
        searchBar=binding.searchtwo;
        searchRecyClerView=binding.searchrecyclerview;
        backArrow=binding.backArrow;
        clear_text=binding.cleanText;
        searchRecyClerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyClerView.setHasFixedSize(true);
    }

    private void searcgKey(String city,String type,String keySearch) {
        binding.progress.setVisibility(View.VISIBLE);
       viewModel.setKeyFilterData(city,type,keySearch,emtySearchResult);
        viewModel.refresh();
        Log.d("sbvsdbsdbbds", "onViewCreated: serach");

    }



    public List<String> listCity(){
        List<String> list=new ArrayList<>();
        for (City city: cityDatabase.cityDao().getCitySelectedForFilterAnnounce()) {
            list.add(city.getCity_id());
        }
        return list;

    }

DetileCallBack detileCallBack=new DetileCallBack() {
    @Override
    public void onItemClicked(View view, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        Navigation.findNavController(view).navigate(R.id.action_fragmentSearch_to_fragmentAnnouncmentDetail, bundle);
    }
};

    KeyFilterDataSource.EmtySearchResult emtySearchResult = new KeyFilterDataSource.EmtySearchResult() {
        @Override
        public void onEmptySEarch() {
            binding.layouterror.setVisibility(View.VISIBLE);
        }
    };
}