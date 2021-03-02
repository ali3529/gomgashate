package com.utabpars.gomgashteh.fragment;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CityAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentCity2Binding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.viewmodel.CityViewModel;

public class FragmentCity extends Fragment {
    FragmentCity2Binding binding;
    RecyclerView recyclerView;
    CityAdaptor cityAdaptor;
    String province_id,province_name,navigation;
    SharedPreferences sharedPreferences;
    CityViewModel cityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_city2,container,false);
        cityViewModel=new ViewModelProvider(this).get(CityViewModel.class);
        initviews();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigation=getArguments().getString("navigate");
         province_id =getArguments().getString("province");
         province_name =getArguments().getString("province_name");
        Log.d("afd", "onSuccess: "+ province_id);
        cityViewModel.getProvinceFromDB(province_id);
       // getProvinces(province_id);

        cityViewModel.provinceMutableLiveData.observe(getViewLifecycleOwner(),province->{
            cityAdaptor =new CityAdaptor(province, new CategoryCallBack() {
                @Override
                public void getCategoryId(View view, String id, int position,String title) {

                    //shared
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("province_id",province_id);
                    editor.putString("province_name",province_name);
                    editor.putString("city_id",String.valueOf(id));
                    editor.putString("city_name",province.get(position).getCity_name());
                    editor.apply();
                    if (navigation.equals("city_add")){
                        Navigation.findNavController(view).popBackStack(R.id.add,false);
                    }else if (navigation.equals("choose")){
                        Navigation.findNavController(view).navigate(R.id.action_fragmentCity2_to_announcement);
                        SharedPreferences preferences=getActivity().getSharedPreferences("cityName",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1=preferences.edit();
                        editor1.putString("city_name_announcment",province_name);
                        editor1.putString("province_id",province_id);
                        editor1.apply();
                    }else if (navigation.equals("city_edit")){

                        SharedPreferences preferences=getActivity().getSharedPreferences("editcity",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_city=preferences.edit();
                        editor_city.putString("province_id",province_id);
                        editor_city.putString("province_name",province_name);
                        editor_city.putString("city_id",String.valueOf(id));
                        editor_city.putString("city_name",province.get(position).getCity_name());
                        editor_city.apply();
                        Navigation.findNavController(view).popBackStack(R.id.editAnnouncementFragment,false);
                    }


                }
            });
            recyclerView.setAdapter(cityAdaptor);
        });

    }


//    private void getProvinces(String id){
//        ApiInterface apiInterface= ApiClient.getApiClient();
//        CompositeDisposable compositeDisposable=new CompositeDisposable();
//        compositeDisposable.add(apiInterface.cities(id,"city")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
//                    @Override
//                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoryModel categoryModel) {
//                        if (categoryModel.getResponse().equals("1")) {
//
//
//                     categoryAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
//                         @Override
//                         public void getCategoryId(View view, String id, int position,String title) {
//
//                             //shared
//                             SharedPreferences.Editor editor=sharedPreferences.edit();
//                             editor.putString("province_id",province_id);
//                             editor.putString("province_name",province_name);
//                             editor.putString("city_id",String.valueOf(id));
//                             editor.putString("city_name",categoryModel.getListData().get(position).getCategoryName());
//                             editor.apply();
//                             if (navigation.equals("city_add")){
//                                 Navigation.findNavController(view).popBackStack(R.id.add,false);
//                             }else if (navigation.equals("choose")){
//                               Navigation.findNavController(view).navigate(R.id.action_fragmentCity2_to_announcement);
//                               SharedPreferences preferences=getActivity().getSharedPreferences("cityName",Context.MODE_PRIVATE);
//                               SharedPreferences.Editor editor1=preferences.edit();
//                                 editor1.putString("city_name_announcment",province_name);
//                                 editor1.putString("province_id",province_id);
//                                 editor1.apply();
//                             }else if (navigation.equals("city_edit")){
//
//                                 SharedPreferences preferences=getActivity().getSharedPreferences("editcity",Context.MODE_PRIVATE);
//                                 SharedPreferences.Editor editor_city=preferences.edit();
//                                 editor_city.putString("province_id",province_id);
//                                 editor_city.putString("province_name",province_name);
//                                 editor_city.putString("city_id",String.valueOf(id));
//                                 editor_city.putString("city_name",categoryModel.getListData().get(position).getCategoryName());
//                                 editor_city.apply();
//                                 Navigation.findNavController(view).popBackStack(R.id.editAnnouncementFragment,false);
//                             }
//
//
//                         }
//                     });
//                     recyclerView.setAdapter(categoryAdaptor);
//                        }
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                        Log.d("tfhgfb", "onSuccess: "+e.toString()+"6");
//                    }
//                }));
//    }

    private void initviews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        sharedPreferences = getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
    }

}