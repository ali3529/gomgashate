package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentListBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.model.CategoryModel;
import com.utabpars.gomgashteh.viewmodel.CategoryViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentList extends Fragment {
    FragmentListBinding binding;
    RecyclerView recyclerView;
    CategoryAdaptor categoryAdaptor;
    CategoryViewModel categoryViewModel;
    static MutableLiveData<CategoryModel> saveInstanceList;
    Toolbar toolbar;
    String test="0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_list,container,false);
        initViews();
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        categoryViewModel=new ViewModelProvider(this).get(CategoryViewModel.class);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        try {
            test=getArguments().getString("test");
        }catch (Exception e){

        }



        if (true){
            categoryViewModel.categoriesMutableLiveData().observe(getViewLifecycleOwner(), new Observer<CategoryModel>() {
                @Override
                public void onChanged(CategoryModel categoryModel) {
                    categoryAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
                        @Override
                        public void getCategoryId(View view, int id,int position) {
                            if (test.equals("1")){
                                Log.d("fdtjnfngdbfv", "getCategoryId: dnknvx [test]");

                                Bundle bundle = new Bundle();
                                bundle.putString("title", categoryModel.getListData().get(position).getCategoryName());
                                bundle.putString("id", String.valueOf(id));
                                bundle.putString("test", test);
                                Navigation.findNavController(view).navigate(R.id.action_list_to_fragmentCallection, bundle);
                            }else {


                                Bundle bundle = new Bundle();
                                bundle.putString("title", categoryModel.getListData().get(position).getCategoryName());
                                bundle.putString("id", String.valueOf(id));
                                bundle.putString("test", "0");
                                Navigation.findNavController(view).navigate(R.id.action_list_to_fragmentCallection, bundle);
                            }
                        }
                    });
                    Log.d("sdvsdvds", "onchange: "+categoryModel.getResponse());
                    Log.d("sdvsdvds", "onchange: "+categoryModel.getListData().get(0).getCategoryName());
                    recyclerView.setAdapter(categoryAdaptor);
                    saveInstanceList =categoryViewModel.categoriesMutableLiveData();
                }
            });
       }
//        else {
//            saveInstanceList.observe(getViewLifecycleOwner(), new Observer<CategoryModel>() {
//                @Override
//                public void onChanged(CategoryModel categoryModel) {
//                    categoryAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
//                        @Override
//                        public void getCategoryId(View view, int id,int position) {
//                            Bundle bundle=new Bundle();
//                            bundle.putString("title",categoryModel.getListData().get(position).getCategoryName());
//                            bundle.putString("id",String.valueOf(id));
//                            Navigation.findNavController(view).navigate(R.id.action_list_to_fragmentCallection,bundle);
//                            Toast.makeText(getContext(), ""+id, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    Log.d("sdvsdvds", "instance: "+categoryModel.getResponse());
//                    Log.d("sdvsdvds", "instance: "+categoryModel.getListData().get(0).getCategoryName());
//                    recyclerView.setAdapter(categoryAdaptor);
//                }
//            });
//        }




    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                Navigation.findNavController(getView()).navigate(R.id.action_list_to_announcement);


            };

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    private void initViews() {
        recyclerView=binding.categoryrecyclerview;
        toolbar=binding.toolbar;
    }


    private void getCategory(){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getcategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoryModel categoryModel) {
                        if (categoryModel.getResponse().equals("1")){
                            Log.d("dsfgdsfds", "onSuccess: "+categoryModel.getResponse());
                            Log.d("dsfgdsfds", "onSuccess: "+categoryModel.getListData().get(0).getCategoryName());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("dsfsd", "onError: "+e.toString());
                    }
                }));
    }


}