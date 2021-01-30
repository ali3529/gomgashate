package com.utabpars.gomgashteh.category.attrebute;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentSpinnerBottomSheetBinding;

import java.util.ArrayList;
import java.util.List;

public class SpinnerBottomSheet extends BottomSheetDialogFragment {
    FragmentSpinnerBottomSheetBinding binding;
    List<String> list=new ArrayList<>();
    String id;
    RecyclerView recyclerView;
    SpinnerItemAdaptor adaptor;
    AttrCallback attrCallback;

    public SpinnerBottomSheet(List<String> list,String id,AttrCallback attrCallback) {
        this.list = list;
        this.id=id;
        this.attrCallback=attrCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_spinner_bottom_sheet,container,false);
        initViews();
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("fdbfdbdfb", "onViewCreated: "+list.size());
        if (getTag().equals("first")){
            binding.emportent.setVisibility(View.GONE);
        }
        adaptor=new SpinnerItemAdaptor(list,itemCallbacl);
        recyclerView.setAdapter(adaptor);
        if (getType().equals("2")){
            binding.emportent.setVisibility(View.GONE);
        }
    }
    private void initViews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    SpinnerItemAdaptor.ItemCallbacl itemCallbacl=new SpinnerItemAdaptor.ItemCallbacl() {
        @Override
        public void ItemClick(String value,String value_id) {
            Log.d("sdgsdbmm", "ItemClick: "+value);
            Log.d("sdgsdbmm", "ItemClick: "+value_id);
            Log.d("sdgsdbmm", "ItemClick: "+id);
            attrCallback.getAttr(id,value,value_id,binding.emportent.isChecked());

        }
    };

    public interface AttrCallback{
        void getAttr(String id,String value,String value_id,boolean isCheck);
    }
    public String getType(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Announce_type", Context.MODE_PRIVATE);
        String type=sharedPreferences.getString("type","0");
      return type;
    }
}