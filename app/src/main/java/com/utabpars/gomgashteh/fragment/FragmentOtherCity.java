package com.utabpars.gomgashteh.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentOtherCityBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.interfaces.ItemSelectedCallback;
import com.utabpars.gomgashteh.model.CategoryModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentOtherCity extends Fragment {
    FragmentOtherCityBinding binding;
    RecyclerView recyclerView;
    CategoryAdaptor otherCityAdaptor;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    static List<String> otherCityList =new ArrayList<>();
    Gson gson;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_other_city,container,false);
        initViews();
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String province_id=getArguments().getString("province");
        String province_name=getArguments().getString("province_name");
        String navigate=getArguments().getString("navigate");

        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.cities(province_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoryModel categoryModel) {
                        Log.d("ewfewvcsdce", "onSuccess: sdgdsg");
                        try {
                            for (int i = 0; i < categoryModel.getListData().size(); i++) {
                                for (int j = 0; j < getSHaredList().size(); j++) {
                                    if (String.valueOf(categoryModel.getListData().get(i).getId()).equals(getSHaredList().get(j))){
                                        categoryModel.getListData().get(i).setSelected(true);
                                        Log.d("ewfewvcsdce", "onSuccess: "+categoryModel.getListData().get(i).getId());
                                        Log.d("ewfewvcsdce", "onSuccess: "+getSHaredList().get(j));

                                    }
                                }
                            }
                        }catch (Exception e ){

                        }


                        otherCityAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
                            @Override
                            public void getCategoryId(View view, String id, int position) {

                                // Navigation.findNavController(view).navigate(R.id.action_fragmentOtherCity_to_add);

                            }
                        },itemSelectedCallback);
                        recyclerView.setAdapter(otherCityAdaptor);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                }));

    }

    private void initViews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        sharedPreferences=getActivity().getSharedPreferences("other_city", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }

    ItemSelectedCallback itemSelectedCallback=new ItemSelectedCallback() {
        @Override
        public void getSelectedItem(View view, CategoryModel.ListData categoryModel, int position,boolean a) {

            if (categoryModel.isSelected()){


                for (int i = 0; i < getSHaredList().size(); i++) {
                    if (String.valueOf(categoryModel.getId()).equals(getSHaredList().get(i))){
                        categoryModel.setSelected(false);
                        otherCityAdaptor.notifyDataSetChanged();
                        otherCityList.remove(i);
                        gson=new Gson();
                        String s=gson.toJson(otherCityList);
                        editor.putString("otherCityList", s);
                        editor.apply();
                    }

                }
            }else {
                try {
                    if (getSHaredList().size()>=10){
                        Toast.makeText(getContext(), "برای هر آگهی مجاز به انتخاب ده شهر هستید", Toast.LENGTH_SHORT).show();

                    }else {
                        categoryModel.setSelected(true);
                        otherCityAdaptor.notifyDataSetChanged();
                        gson=new Gson();
                        otherCityList.add(String.valueOf(categoryModel.getId()));
                        String s=gson.toJson(otherCityList);
                        editor.putString("otherCityList", s);
                        editor.apply();
                    }
                }catch (Exception e){   categoryModel.setSelected(true);
                    otherCityAdaptor.notifyDataSetChanged();
                    gson=new Gson();
                    otherCityList.add(String.valueOf(categoryModel.getId()));
                    String s=gson.toJson(otherCityList);
                    editor.putString("otherCityList", s);
                    editor.apply();}


            }

        }
    };


    public List<String> getSHaredList(){
        Gson gson=new Gson();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("other_city",Context.MODE_PRIVATE);
        String s=sharedPreferences.getString("otherCityList","w");
        Type type=new TypeToken<List<String>>(){

        }.getType();
        List<String>  j=gson.fromJson(s,type);

        Log.d("sfesfsef", "getCategoryId: "+j.get(0));
        return j;
    }


}