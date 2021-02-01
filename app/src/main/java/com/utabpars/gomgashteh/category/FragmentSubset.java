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
import com.utabpars.gomgashteh.databinding.FragmentSubsetBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;

public class FragmentSubset extends Fragment implements SubSetCallBack{
     FragmentSubsetBinding binding;
     SubsetViewModel subsetViewModel;
     RecyclerView recyclerView;
     CategoryAdaptor adaptor;
    String idx,title,type,id_anounce;
    Bundle bundle;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_subset,container,false);
        subsetViewModel=new ViewModelProvider(this).get(SubsetViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        recyclerView=binding.subsetrecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         idx =getArguments().getString("id");
         title=getArguments().getString("title");
        type =getArguments().getString("type");

        binding.toolbartitle.setText(title);
        subsetViewModel.getSubset(idx,"sub_one",type);
        subsetViewModel.getCallBack(this);

        lastAnnouncmentAboveBtNavigation();

    }

    CategoryCallBack categoryCallBack=new CategoryCallBack() {
        @Override
        public void getCategoryId(View view, String id, int position,String title) {
            bundle =new Bundle();
            bundle.putString("id",id);
            bundle.putString("title",title);

            id_anounce=id;
            subsetViewModel.getSubset(id,"sub_two",type);

            saveCategoryNames(title);

        }
    };

    @Override
    public void onSubsetCallback(SubSetModel subSetModel) {
        if (subSetModel.getMasasge().equals("sub_one")){
            adaptor=new CategoryAdaptor(subSetModel.getListData(),categoryCallBack);
            recyclerView.setAdapter(adaptor);
        }else if (subSetModel.getMasasge().equals("sub_two")){
            bundle.putString("type",type);
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_fragmentSubset_to_fragmentSubTwo,bundle);
            }catch (Exception e){

            }


        }
    }

    @Override
    public void onAttributeCallback(SubSetModel SubSetModel) {

        Bundle bundle=new Bundle();
        bundle.putString("id",id_anounce);
        bundle.putString("type","sub_two");

        try {
            Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAttrebute,bundle);
        }catch (Exception e){

        }

    }

    @Override
    public void emptyCallback(boolean empty,SubSetModel subSetModel) {
        if (type.equals("category")){
            Bundle bundle=new Bundle();
            bundle.putString("id", id_anounce);
            bundle.putString("type","sub_two");
            bundle.putString("title",title);
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAnnouncCollection,bundle);
            }catch (Exception e){

            }

        }else {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("collaction_id", id_anounce);
            editor.putString("type","sub_two");
            editor.putString("title",title);
            editor.apply();
            Toast.makeText(getContext(), "emty_one", Toast.LENGTH_SHORT).show();
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
        SaveCategoryNameEditor.putString("sub_one",title);
        SaveCategoryNameEditor.putString("sub_two","");
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
}