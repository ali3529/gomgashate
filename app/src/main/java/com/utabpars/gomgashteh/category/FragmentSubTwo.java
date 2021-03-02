package com.utabpars.gomgashteh.category;

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
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.category.attrebute.Subset2ViewModel;
import com.utabpars.gomgashteh.database.categoryDatabase.Attrebiute;
import com.utabpars.gomgashteh.database.categoryDatabase.Category;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset2;
import com.utabpars.gomgashteh.databinding.FragmentSubTwoBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentSubTwo extends Fragment
        //implements SubSetCallBack
{
FragmentSubTwoBinding binding;
    Subset2ViewModel subset2ViewModel;
    RecyclerView recyclerView;
    CategoryAdaptor adaptor;
    String id,title,id_show_announce,type;
    Bundle bundle;
    SharedPreferences sharedPreferences;
    boolean test=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_sub_two,container,false);
        subset2ViewModel =new ViewModelProvider(this).get(Subset2ViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        initViews();
        Log.d("ntnykgyuj", "onCreateView: ");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            id=getArguments().getString("id");
            title=getArguments().getString("title");
        }catch (Exception e){

        }
        binding.toolbartitle.setText(title);
        lastAnnouncmentAboveBtNavigation();
        setSubsetDataToRecyclerView(id);


        subset2ViewModel.attributeMutableLiveData.observe(getViewLifecycleOwner(),attrebiute->{
            if (test) {
                if (isComeFromAdd()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id_show_announce);
                    bundle.putString("type", "sub_three");
                    Log.d("ntnykgyuj", "onAttributeCallback --1: ");
                    try {
                        Log.d("sdbvsdbsdbb", "onAttributeCallback:---action_global_fragmentAttrebute -----  onAttributeCallback");
                        Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAttrebute, bundle);
                    } catch (Exception e) {

                    }
                } else {
                    Log.d("ntnykgyuj", "onAttributeCallback --2: ");
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id_show_announce);
                    bundle.putString("type", "sub_three");
                    bundle.putString("title", title);
                    Log.d("sdbvsdbsdbb", "onAttributeCallback:---action_global_fragmentAttrebute --  else -----  onAttributeCallback");
                    Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAnnouncCollection, bundle);
                }
                test=false;
            }
        });

        subset2ViewModel.emptySubSet.observe(getViewLifecycleOwner(),emty->{
            if (!isComeFromAdd()) {
                Log.d("ntnykgyuj", "emptyCallback --1: ");
                Bundle bundle = new Bundle();
                bundle.putString("id", id_show_announce);
                bundle.putString("type", "sub_three");
                bundle.putString("title", title);
                Log.d("sdbvsdbsdbb", "onAttributeCallback:---action_global_fragmentAnnouncCollection -----  emptyCallback");
                Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAnnouncCollection, bundle);
            } else {
                Log.d("ntnykgyuj", "emptyCallback --2: ");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("collaction_id", id_show_announce);
                editor.putString("type", "sub_three");
                editor.putString("title", title);
                editor.apply();
                try {
                    Log.d("sdbvsdbsdbb", "onAttributeCallback:---action_global_fragmentAnnouncCollection--- else -----  emptyCallback");
                    Navigation.findNavController(getView()).navigate(R.id.action_global_add);
                } catch (Exception e) {

                }

            }

        });

    }

    CategoryCallBack categoryCallBack=new CategoryCallBack() {
        @Override
        public void getCategoryId(View view, String id, int position,String title) {
            id_show_announce=id;
             bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("title",title);
            test=true;
                subset2ViewModel.getSubset2FromDb(id);


            saveCategoryNames(title);

        }
    };


    private void initViews() {
        recyclerView=binding.subsetrecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void setSubsetDataToRecyclerView(String subset_id){
        subset2ViewModel.getSubsetToRecyclerView(subset_id).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(result->{
            List<Category> categories = new ArrayList<>();
            for (Subset2 c : result) {
                Category category = new Category();
                category.setId(c.getId());
                category.setName(c.getName());
                categories.add(category);


                adaptor = new CategoryAdaptor(categories, categoryCallBack);
                recyclerView.setAdapter(adaptor);
            }
        });
    }


    private void saveCategoryNames(String title) {
        SharedPreferences SaveCategoryName;
        SharedPreferences.Editor SaveCategoryNameEditor;
        SaveCategoryName=getActivity().getSharedPreferences("save_category_name",Context.MODE_PRIVATE);
        SaveCategoryNameEditor=SaveCategoryName.edit();
        SaveCategoryNameEditor.putString("sub_two",title);
        SaveCategoryNameEditor.putString("sub_three","");
        SaveCategoryNameEditor.apply();
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
    private boolean isComeFromAdd(){
        SharedPreferences from_add_shpref=getActivity().getSharedPreferences("from_add",Context.MODE_PRIVATE);
        return from_add_shpref.getBoolean("from_add",false);
    }

}

