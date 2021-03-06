package com.utabpars.gomgashteh.fragment;

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
import com.utabpars.gomgashteh.adaptor.CityAdaptor;
import com.utabpars.gomgashteh.adaptor.ProvinceAdaptor;
import com.utabpars.gomgashteh.database.citydatabase.Province;
import com.utabpars.gomgashteh.databinding.FragmentProvinceBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.viewmodel.ProvinceViewModel;

public class FragmentProvince extends Fragment {
    FragmentProvinceBinding binding;
    ProvinceViewModel provinceViewModel;
    RecyclerView recyclerView;
    ProvinceAdaptor cityAdaptor;
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

        provinceViewModel.provinceMutableLiveData.observe(getViewLifecycleOwner(),province->{
            cityAdaptor=new ProvinceAdaptor(province, new CategoryCallBack() {
                    @Override
                    public void getCategoryId(View view, String id, int position,String title) {
                        try {
                            navigate=getArguments().getString("navigate");
                            if (navigate.equals("city_add")) {


                                Bundle bundle = new Bundle();
                                bundle.putString("province", String.valueOf(id));
                                bundle.putString("province_name", province.get(position).getProvince_name());
                                bundle.putString("navigate", navigate);
                                Navigation.findNavController(view).navigate(R.id.action_fragmentCity_to_fragmentCity2, bundle);
                            }else if (navigate.equals("city_edit")){
                                Bundle bundle = new Bundle();
                                bundle.putString("province", String.valueOf(id));
                                bundle.putString("province_name", province.get(position).getProvince_name());
                                bundle.putString("navigate", "city_edit");
                                Navigation.findNavController(view).navigate(R.id.action_fragmentCity_to_fragmentCity2, bundle);
                            }
                        }catch (Exception e){

                                 Bundle bundle = new Bundle();
                                 bundle.putString("province", String.valueOf(id));
                                 bundle.putString("province_name", province.get(position).getProvince_name());
                                 Navigation.findNavController(view).navigate(R.id.action_fragmentProvince_to_fragmentMainCity2, bundle);

                        }



                    }
                },itemProvinceSelected);
                recyclerView.setAdapter(cityAdaptor);
                //ecyclerView.computeVerticalScrollExtent();
               // recyclerView.computeVerticalScrollOffset();

            });

    }


    private void initviews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    ProvinceAdaptor.ItemProvinceSelected itemProvinceSelected=new ProvinceAdaptor.ItemProvinceSelected() {
        @Override
        public void clearAllCity(Province city) {
            provinceViewModel.clearAllCity(city.getProvince_id());
            provinceViewModel.onSelectProvince(city.getProvince_id());
        }
    };
}