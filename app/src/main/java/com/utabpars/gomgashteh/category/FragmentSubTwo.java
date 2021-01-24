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
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.CategoryAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentSubTwoBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;

public class FragmentSubTwo extends Fragment implements SubSetCallBack {
FragmentSubTwoBinding binding;
    SubsetViewModel subsetViewModel;
    RecyclerView recyclerView;
    CategoryAdaptor adaptor;
    String id,title,id_show_announce,type;
    Bundle bundle;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_sub_two,container,false);
        subsetViewModel=new ViewModelProvider(this).get(SubsetViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        initViews();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            id=getArguments().getString("id");
            title=getArguments().getString("title");
            type =getArguments().getString("type");
        }catch (Exception e){

        }

        binding.toolbartitle.setText(title);
        subsetViewModel.getSubset(id,"sub_two",type);
        subsetViewModel.getCallBack(this);
        lastAnnouncmentAboveBtNavigation();

    }

    CategoryCallBack categoryCallBack=new CategoryCallBack() {
        @Override
        public void getCategoryId(View view, String id, int position,String title) {
            id_show_announce=id;
             bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("title",title);
            subsetViewModel.getSubset(id,"sub_three",type);
            saveCategoryNames(title);

        }
    };
    private void initViews() {
        recyclerView=binding.subsetrecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onSubsetCallback(SubSetModel subSetModel) {
        //sub
        if (subSetModel.getMasasge().equals("sub_two")){
            adaptor=new CategoryAdaptor(subSetModel.getListData(),categoryCallBack);
            recyclerView.setAdapter(adaptor);
        }else if (subSetModel.getMasasge().equals("sub_three")){
            bundle.putString("type",type);
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentSubTwo_to_fragmentSubThree,bundle);
            }catch (Exception e){

            }

        }


    }

    @Override
    public void onAttributeCallback(SubSetModel SubSetModel) {
        //atrebiut
        Bundle bundle=new Bundle();
        bundle.putString("id",id_show_announce);
        bundle.putString("type","sub_three");
        try {
            Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAttrebute,bundle);
        }catch (Exception e){

        }

    }

    @Override
    public void emptyCallback(boolean empty,SubSetModel subSetModel) {
        //no sub no attrebute
        if (type.equals("category")){
            Bundle bundle=new Bundle();
            bundle.putString("id",id_show_announce);
            bundle.putString("type","sub_three");
            bundle.putString("title",title);
            Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAnnouncCollection,bundle);
        }else {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("collaction_id",id_show_announce);
            editor.putString("type","sub_three");
            editor.putString("title",title);
            editor.apply();
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_global_add);
            }catch (Exception e){

            }

        }

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
                Log.d("dsgfdgfdg", "onScrolled: "+itemcount);
                Log.d("dsgfdgfdg", "onScrolled: "+lastvisi);
                if (lastvisi==itemcount-1){
                    Log.d("dsgfdgfdg", "last: ");
                    binding.gggg.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

