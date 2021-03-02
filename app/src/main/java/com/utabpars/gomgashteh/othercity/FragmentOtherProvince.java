package com.utabpars.gomgashteh.othercity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.citydatabase.Province;
import com.utabpars.gomgashteh.databinding.FragmentOtherProvinceBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.viewmodel.ProvinceViewModel;

public class FragmentOtherProvince extends Fragment {

    FragmentOtherProvinceBinding binding;
    ProvinceViewModel provinceViewModel;
    RecyclerView recyclerView;
    OtherProvinceAdaptor cityAdaptor;
    String navigate;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_other_province,container,false);
        provinceViewModel=new ViewModelProvider(this).get(ProvinceViewModel.class);
        initviews();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("other_city_navigate", Context.MODE_PRIVATE);
        navigate=sharedPreferences.getString("navigate","0");
        provinceViewModel.provinceMutableLiveData.observe(getViewLifecycleOwner(),province->{

            cityAdaptor=new OtherProvinceAdaptor(province, new CategoryCallBack() {
                @Override
                public void getCategoryId(View view, String id, int position, String name) {
                    if (navigate.equals("otherCity")){
                        Bundle bundle = new Bundle();
                        bundle.putString("province", String.valueOf(id));
                        bundle.putString("province_name", province.get(position).getProvince_name());
                        bundle.putString("navigate", "otherCity");
                        Navigation.findNavController(view).navigate(R.id.action_fragmentOtherProvince_to_fragmentOtherCity2, bundle);
                    }else if (navigate.equals("otherCityEdit")){
                        Bundle bundle = new Bundle();
                        bundle.putString("province", String.valueOf(id));
                        bundle.putString("province_name", province.get(position).getProvince_name());
                        bundle.putString("navigate", "otherCityEdit");
                        Navigation.findNavController(view).navigate(R.id.action_fragmentOtherProvince_to_fragmentOtherCity2, bundle);
                    }
                }
            },itemProvinceSelected);
            recyclerView.setAdapter(cityAdaptor);
        });


    }


    private void initviews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    OtherProvinceAdaptor.ItemProvinceSelected itemProvinceSelected=new OtherProvinceAdaptor.ItemProvinceSelected() {
        @Override
        public void clearAllOtherCity(Province city) {
            provinceViewModel.clearProvince(city.getProvince_id());
            provinceViewModel.clearAllOtherCity(city.getProvince_id());

        }
    };


}