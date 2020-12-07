package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentProvinceBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.model.CategoryModel;
import com.utabpars.gomgashteh.viewmodel.ProvinceViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentProvince extends Fragment {
    FragmentProvinceBinding binding;
    ProvinceViewModel provinceViewModel;
    RecyclerView recyclerView;
    CategoryAdaptor categoryAdaptor;
    String navigate;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_province,container,false);
        provinceViewModel=new ViewModelProvider(this).get(ProvinceViewModel.class);
        initviews();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigate=getArguments().getString("navigate");
        provinceViewModel.categoryModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<CategoryModel>() {
            @Override
            public void onChanged(CategoryModel categoryModel) {
                categoryAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
                    @Override
                    public void getCategoryId(View view, int id, int position) {
                        if (navigate.equals("city_add")) {


                            Bundle bundle = new Bundle();
                            bundle.putString("province", String.valueOf(id));
                            bundle.putString("province_name", categoryModel.getListData().get(position).getCategoryName());
                            bundle.putString("navigate", navigate);
                            Navigation.findNavController(view).navigate(R.id.action_fragmentCity_to_fragmentCity2, bundle);
                        }else if (navigate.equals("otherCity")){
                            Bundle bundle = new Bundle();
                            bundle.putString("province", String.valueOf(id));
                            bundle.putString("province_name", categoryModel.getListData().get(position).getCategoryName());
                            Navigation.findNavController(view).navigate(R.id.action_fragmentCity_to_fragmentOtherCity, bundle);
                        }
                        else if (navigate.equals("choose")){
                            Bundle bundle = new Bundle();
                            bundle.putString("province", String.valueOf(id));
                            bundle.putString("province_name", categoryModel.getListData().get(position).getCategoryName());
                            bundle.putString("navigate", "choose");
                            Navigation.findNavController(view).navigate(R.id.action_fragmentCity_to_fragmentCity2, bundle);
                        }
                    }
                });
                recyclerView.setAdapter(categoryAdaptor);
            }
        });
    }


    private void initviews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }


}