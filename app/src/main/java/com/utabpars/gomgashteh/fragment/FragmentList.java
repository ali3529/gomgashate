package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentListBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;
import com.utabpars.gomgashteh.model.CategoryModel;
import com.utabpars.gomgashteh.viewmodel.CategoryViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentList extends Fragment {
    FragmentListBinding binding;
    RecyclerView recyclerView;
    CategoryAdaptor categoryAdaptor;
    CategoryViewModel categoryViewModel;
    static MutableLiveData<CategoryModel> saveInstanceList;
    Toolbar toolbar;
    String type ="0";
    boolean from_add=false;

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
        binding.setProgress(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        try {
            type =getArguments().getString("type");
        }catch (Exception e){
            type="category";
        }

        try {
            from_add=getArguments().getBoolean("from_add");
        }catch (Exception e){
            from_add=false;
        }
        SharedPreferences from_add_shpref=getActivity().getSharedPreferences("from_add",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_from_add=from_add_shpref.edit();
        editor_from_add.putBoolean("from_add",from_add);
        editor_from_add.apply();


        categoryViewModel.listMutableLiveDataFromDb.observe(getViewLifecycleOwner(),categoryModel -> {
            Log.d("dvdsvdsv", "lastAnnouncmentAboveBtNavigation: "+categoryModel.get(0).getId());
            binding.setProgress(false);
            categoryAdaptor=new CategoryAdaptor(categoryModel, new CategoryCallBack() {
                @Override
                public void getCategoryId(View view, String id,int position,String title) {

                    saveCategoryNames(title);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", categoryModel.get(position).getName());
                    bundle.putString("id", String.valueOf(id));
                    bundle.putString("type", type);
                    Navigation.findNavController(view).navigate(R.id.action_list_to_fragmentCallection, bundle);

                }
            });
            recyclerView.setAdapter(categoryAdaptor);
            saveInstanceList =categoryViewModel.categoriesMutableLiveData();
        });

        lastAnnouncmentAboveBtNavigation();

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

              //  Navigation.findNavController(getView()).navigate(R.id.action_list_to_announcement);
               Navigation.findNavController(getView()).popBackStack();



            };

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    private void initViews() {
        recyclerView=binding.categoryrecyclerview;
        toolbar=binding.toolbar;
    }

private void saveCategoryNames(String title) {
    SharedPreferences SaveCategoryName;
    SharedPreferences.Editor SaveCategoryNameEditor;
    SaveCategoryName = getActivity().getSharedPreferences("save_category_name", Context.MODE_PRIVATE);
    SaveCategoryNameEditor = SaveCategoryName.edit();
    SaveCategoryNameEditor.putString("category", title);
    SaveCategoryNameEditor.apply();
}


}