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
        Log.d("mnm", "onCreateView: ffff");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            id=getArguments().getString("id");
            title=getArguments().getString("title");
            type =getArguments().getString("type");
            Log.d("dsdsfdsf", "onViewCreated: "+type);
        }catch (Exception e){

        }

        binding.toolbartitle.setText(title);
        subsetViewModel.getSubset(id,"sub_two",type);
        subsetViewModel.getCallBack(this);
        Log.d("sadsadsad", "getCategoryId: "+id);


    }

    CategoryCallBack categoryCallBack=new CategoryCallBack() {
        @Override
        public void getCategoryId(View view, String id, int position,String title) {
            Log.d("safasdsadsadwz", "getCategoryId: sub two "+id);
            id_show_announce=id;
             bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("title",title);
            subsetViewModel.getSubset(id,"sub_three",type);

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
            Log.d("safcaccv", "onViewCreated: "+subSetModel.getListData().get(0).getCategoryName());
            adaptor=new CategoryAdaptor(subSetModel.getListData(),categoryCallBack);
            recyclerView.setAdapter(adaptor);
        }else if (subSetModel.getMasasge().equals("sub_three")){
            bundle.putString("type",type);
            Navigation.findNavController(getView()).navigate(R.id.action_fragmentSubTwo_to_fragmentSubThree,bundle);
        }


    }

    @Override
    public void onAttributeCallback(SubSetModel SubSetModel) {
        //atrebiut
        Toast.makeText(getContext(), "attribute 2", Toast.LENGTH_SHORT).show();
        Bundle bundle=new Bundle();
        bundle.putString("id",id_show_announce);
        bundle.putString("type","sub_three");
        Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAttrebute,bundle);
    }

    @Override
    public void emptyCallback(boolean empty,SubSetModel subSetModel) {
        //no sub no attrebute
        if (type.equals("category")){
            Toast.makeText(getContext(), "زیر مجموعه وجود ندارد", Toast.LENGTH_SHORT).show();
            Bundle bundle=new Bundle();
            bundle.putString("id",id_show_announce);
            bundle.putString("type","sub_three");
            Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAnnouncCollection,bundle);
        }else {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("collaction_id",id_show_announce);
            editor.putString("type","sub_three");
            editor.putString("title",title);
            editor.apply();
            Toast.makeText(getContext(), "emtyyyyy", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_global_add);
        }

    }
}