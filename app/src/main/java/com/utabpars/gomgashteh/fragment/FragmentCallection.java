
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
import com.utabpars.gomgashteh.database.categoryDatabase.Attrebiute;
import com.utabpars.gomgashteh.database.categoryDatabase.Category;
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryDataBase;
import com.utabpars.gomgashteh.database.categoryDatabase.Collection;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset2;
import com.utabpars.gomgashteh.databinding.FragmentCallectionBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.model.CategoryModel;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

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
    SharedPreferences shPref;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_callection,container,false);
         subsetViewModel=new ViewModelProvider(this).get(SubsetViewModel.class);
        shPref = getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
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
        Log.d("Sdvsdv", "onViewCreated: "+list_id);

        type =getArguments().getString("type");


        binding.setTitle(title);
        subsetViewModel.getCallBack(this);
        CategoryDataBase categoryDataBase=CategoryDataBase.getInstance(getContext());
        categoryDataBase.categoryDao().getCollection(list_id).observe(getViewLifecycleOwner(),collection->{
            //Log.d("Dvdsvsdv", "onViewCreated: "+collection.get(0).getId());
            List<Category> categories=new ArrayList<>();
            for (Collection c : collection) {
                Category category=new Category();
                category.setId(c.getId());
                category.setName(c.getName());
                categories.add(category);

            }
//            Log.d("Dvdsvsdv", "onViewCreated: "+categories.get(0).getId());
            binding.setProgress(false);
            collectionAdaptor=new CategoryAdaptor(categories, new CategoryCallBack() {
                @Override
                public void getCategoryId(View view, String id,int position,String title) {

                    idg=id;
                    titleg=title;
                    subsetViewModel.getSubsetFromDb(id);
                    saveCategoryNames(title);
                    SharedPreferences.Editor editor=shPref.edit();
                    editor.putString("card_id",id);
                    editor.apply();

                }
            });
            recyclerView.setAdapter(collectionAdaptor);
        });


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
    public void onSubsetCallback(List<Subset> subSetModel) {
        Log.d("dfbdfbfdbfdb", "emptyCallback: "+"SubsetCallback");
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
    public void onSubset2Callback(List<Subset2> subSetModel) {

    }

    @Override
    public void onAttributeCallback(List<Attrebiute> SubSetModel) {
        Log.d("dfbdfbfdbfdb", "emptyCallback: "+"ttributeCallback");
        Bundle bundle=new Bundle();
        bundle.putString("id",idg);
        bundle.putString("type","sub_one");
        try {
            Navigation.findNavController(getView()).navigate(R.id.action_fragmentCallection_to_fragmentAttrebute,bundle);
        }catch (Exception e){

        }

    }

    @Override
    public void emptyCallback(boolean empty) {
        Log.d("dfbdfbfdbfdb", "emptyCallback: "+"emty callback");
        if (!isComeFromAdd()){
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

    private boolean isComeFromAdd(){
        SharedPreferences from_add_shpref=getActivity().getSharedPreferences("from_add",Context.MODE_PRIVATE);
        return from_add_shpref.getBoolean("from_add",false);
    }
}