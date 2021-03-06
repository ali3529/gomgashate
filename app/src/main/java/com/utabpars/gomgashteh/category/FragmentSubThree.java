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
import com.utabpars.gomgashteh.databinding.FragmentSubThreeBinding;
import com.utabpars.gomgashteh.interfaces.CategoryCallBack;


public class FragmentSubThree extends Fragment
        //implements SubSetCallBack
{

    FragmentSubThreeBinding binding;
    SubsetViewModel subsetViewModel;
    RecyclerView recyclerView;
    CategoryAdaptor adaptor;
    String id,title,t_id,type;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_sub_three,container,false);
        subsetViewModel=new ViewModelProvider(this).get(SubsetViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        initViews();
        Log.d("dbfdboooi", "onCreateView: "+"subttt");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         id=getArguments().getString("id");
         title=getArguments().getString("title");
        type =getArguments().getString("type");
        binding.toolbartitle.setText(title);
      //  subsetViewModel.getSubset(id,"sub_three",type);
      //  subsetViewModel.getCallBack(this);
        lastAnnouncmentAboveBtNavigation();

    }

    CategoryCallBack categoryCallBack=new CategoryCallBack() {
        @Override
        public void getCategoryId(View view, String id, int position,String title) {

            //subsetViewModel.getSubset(id,"attr",type);
            t_id=id;
            saveCategoryNames(title);
        }
    };
    private void initViews() {
        recyclerView=binding.subsetrecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

//    @Override
//    public void onSubsetCallback(SubSetModel subSetModel) {
////        adaptor=new CategoryAdaptor(subSetModel.getListData(),categoryCallBack);
////        recyclerView.setAdapter(adaptor);
//    }
//
//    @Override
//    public void onAttributeCallback(SubSetModel SubSetModel) {
//        Bundle bundle=new Bundle();
//        bundle.putString("id",t_id);
//        bundle.putString("type","attr");
//        try {
//            Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAttrebute,bundle);
//        }catch (Exception e){
//
//        }
//
//    }
//
//    @Override
//    public void emptyCallback(boolean empty,SubSetModel subSetModel) {
//        if (type.equals("category")){
//            Bundle bundle=new Bundle();
//            bundle.putString("id",t_id);
//            bundle.putString("type","attr");
//            bundle.putString("title",title);
//            try {
//                Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAnnouncCollection,bundle);
//            }catch (Exception e){
//
//            }
//
//        }else {
//            SharedPreferences.Editor editor=sharedPreferences.edit();
//            editor.putString("collaction_id",t_id);
//            editor.putString("type","attr");
//            editor.putString("title",title);
//            editor.apply();
//            try {
//                Navigation.findNavController(getView()).navigate(R.id.action_global_add);
//            }catch (Exception e){
//
//            }
//
//        }
//
//    }
    private void saveCategoryNames(String title) {
        SharedPreferences SaveCategoryName;
        SharedPreferences.Editor SaveCategoryNameEditor;
        SaveCategoryName=getActivity().getSharedPreferences("save_category_name",Context.MODE_PRIVATE);
        SaveCategoryNameEditor=SaveCategoryName.edit();
        SaveCategoryNameEditor.putString("sub_three",title);
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