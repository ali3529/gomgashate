package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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

import com.google.gson.JsonObject;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentCallectionBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.model.CategoryModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentCallection extends Fragment {
    FragmentCallectionBinding binding;
    Toolbar toolbar;
    RecyclerView recyclerView;
    CategoryAdaptor collectionAdaptor;
    String test="0";
    String title;
    String list_id;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_callection,container,false);
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

        try {
            test=getArguments().getString("test");
        }catch (Exception e){

        }

        binding.setTitle(title);

        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.getcallection(list_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<CategoryModel>() {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoryModel categoryModel) {
                if (categoryModel.getResponse().equals("1")){
                    binding.setProgress(false);
                    collectionAdaptor=new CategoryAdaptor(categoryModel.getListData(), new CategoryCallBack() {
                        @Override
                        public void getCategoryId(View view, int id,int position) {
                            if (test.equals("1")){
                                Log.d("fdtjnfngdbfv", "getCategoryId: dnknvx {cpllent}");
                                Bundle bundle=new Bundle();
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("title_list",title);
                                editor.putString("id_categ",String.valueOf(id));
                                editor.putString("id_list",String.valueOf(list_id));
                                editor.putString("title_categ",categoryModel.getListData().get(position).getCategoryName());
                                editor.apply();

//                                bundle.putString("title_list",title);
//                                bundle.putString("id_categ",String.valueOf(id));
//                                bundle.putString("id_list",String.valueOf(list_id));
                                Navigation.findNavController(view).navigate(R.id.action_fragmentCallection_to_add,bundle);
                            }else {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", String.valueOf(id));
                                Navigation.findNavController(view).navigate(R.id.action_fragmentCallection_to_fragmentAnnouncCollection, bundle);
                                Toast.makeText(getContext(), "" + id, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    recyclerView.setAdapter(collectionAdaptor);
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }
        }));


    }

    private void initViews() {
        toolbar=binding.toolbar;
        recyclerView=binding.recyvlerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
    }
}