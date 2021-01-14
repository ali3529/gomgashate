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
    String id,title,type,id_anounce;
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
         id=getArguments().getString("id");
         title=getArguments().getString("title");
        type =getArguments().getString("type");
        Log.d("dsdsfdsf", "onViewCreated: "+type);
        binding.toolbartitle.setText(title);
        subsetViewModel.getSubset(id,"sub_one",type);
        Log.d("ytjuyi", "onViewCreated: "+id);
        subsetViewModel.getCallBack(this);

    }

    CategoryCallBack categoryCallBack=new CategoryCallBack() {
        @Override
        public void getCategoryId(View view, String id, int position,String title) {
            bundle =new Bundle();
            bundle.putString("id",id);
            bundle.putString("title",title);
            Log.d("safasdsadsadwz", "getCategoryId: sub one "+id);
            subsetViewModel.getSubset(id,"sub_two",type);
            id_anounce=id;

        }
    };

    @Override
    public void onSubsetCallback(SubSetModel subSetModel) {
        if (subSetModel.getMasasge().equals("sub_one")){
            Toast.makeText(getContext(), "sub 1", Toast.LENGTH_SHORT).show();
            Log.d("safcaccv", "onViewCreated: "+subSetModel.getListData().get(0).getCategoryName());
            adaptor=new CategoryAdaptor(subSetModel.getListData(),categoryCallBack);
            recyclerView.setAdapter(adaptor);
        }else if (subSetModel.getMasasge().equals("sub_two")){
            bundle.putString("type",type);
            Navigation.findNavController(getView()).navigate(R.id.action_fragmentSubset_to_fragmentSubTwo,bundle);
            Log.d("safcaccv", "onViewCreated: gggg");
        }
    }

    @Override
    public void onAttributeCallback(SubSetModel SubSetModel) {
        Toast.makeText(getContext(), "attribute 1", Toast.LENGTH_SHORT).show();
        Bundle bundle=new Bundle();
        bundle.putString("id",id_anounce);
        bundle.putString("type","sub_two");
        Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAttrebute,bundle);
    }

    @Override
    public void emptyCallback(boolean empty,SubSetModel subSetModel) {
        if (type.equals("category")){
            Toast.makeText(getContext(), "زیر مجموعه وجود ندارد", Toast.LENGTH_SHORT).show();
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("type","sub_one");
            Log.d("saavavavs", "emptyCallback: iiiiiiiiiiii");
            Navigation.findNavController(getView()).navigate(R.id.action_global_fragmentAnnouncCollection,bundle);
        }else {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("collaction_id",id);
            editor.putString("type","sub_one");
            editor.putString("title",title);
            editor.apply();
            Toast.makeText(getContext(), "emtyyyyy", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_global_add);
        }



    }
}