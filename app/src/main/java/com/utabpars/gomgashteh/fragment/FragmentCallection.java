package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.category.SubSetCallBack;
import com.utabpars.gomgashteh.category.SubSetModel;
import com.utabpars.gomgashteh.category.SubsetViewModel;
import com.utabpars.gomgashteh.databinding.FragmentCallectionBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.model.CategoryModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentCallection extends Fragment implements SubSetCallBack {
    FragmentCallectionBinding binding;
    Toolbar toolbar;
    RecyclerView recyclerView;
    CategoryAdaptor collectionAdaptor;
    String type ="0";
    String title;
    String list_id;
    SharedPreferences sharedPreferences;
    String ca;
    String idg,titleg;
    SubsetViewModel subsetViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_callection,container,false);
         subsetViewModel=new ViewModelProvider(this).get(SubsetViewModel.class);
         initViews();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setProgress(true);
         title=getArguments().getString("title");
         list_id=getArguments().getString("id");

            type =getArguments().getString("type");


        binding.setTitle(title);
        subsetViewModel.getCallBack(this);
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getcallection(list_id,type)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoryModel categoryModel) {
                if (categoryModel.getResponse().equals("1")){
                    binding.setProgress(false);
                    collectionAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
                        @Override
                        public void getCategoryId(View view, String id,int position,String title) {

                             idg=id;
                             titleg=title;
                             subsetViewModel.getSubset(id,"sub_one",type);
                             saveCategoryNames(title);

                        }
                    });
                    recyclerView.setAdapter(collectionAdaptor);
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }
        }));

        lastAnnouncmentAboveBtNavigation();
    }

    private void saveCategoryNames(String title) {
        SharedPreferences SaveCategoryName;
        SharedPreferences.Editor SaveCategoryNameEditor;
        SaveCategoryName=getActivity().getSharedPreferences("save_category_name",Context.MODE_PRIVATE);
        SaveCategoryNameEditor=SaveCategoryName.edit();
        SaveCategoryNameEditor.putString("collection",title);
        SaveCategoryNameEditor.putString("sub_one","");
        SaveCategoryNameEditor.putString("sub_two","");
        SaveCategoryNameEditor.putString("sub_three","");
        SaveCategoryNameEditor.apply();
    }

    private void initViews() {
        toolbar=binding.toolbar;
        recyclerView=binding.recyvlerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
    }

    @Override
    public void onSubsetCallback(SubSetModel subSetModel) {
        Bundle bundle=new Bundle();
        bundle.putString("id",idg);
        bundle.putString("title",titleg);
        bundle.putString("type",type);
        try {
            Navigation.findNavController(getView()).navigate(R.id.action_fragmentCallection_to_fragmentSubset,bundle);
        }catch (Exception e){

        }

    }

    @Override
    public void onAttributeCallback(SubSetModel SubSetModel) {
        Bundle bundle=new Bundle();
        bundle.putString("id",idg);
        bundle.putString("type","sub_one");
        try {
            Navigation.findNavController(getView()).navigate(R.id.action_fragmentCallection_to_fragmentAttrebute,bundle);
        }catch (Exception e){

        }

    }

    @Override
    public void emptyCallback(boolean empty, SubSetModel subSetModel) {
        if (type.equals("category")){
            Bundle bundle=new Bundle();
            bundle.putString("id",idg);
            bundle.putString("type","sub_one");
            bundle.putString("title",title);
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentCallection_to_fragmentAnnouncCollection,bundle);
            }catch (Exception e){

            }

        }else {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("collaction_id",idg);
            editor.putString("type","sub_one");
            editor.putString("title",title);
            editor.apply();
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentCallection_to_add);
            }catch (Exception e){

            }

        }


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
                if (lastvisi==itemcount-1){
                    binding.gggg.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}