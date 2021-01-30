package com.utabpars.gomgashteh.category.attrebute;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentAttrebuteBinding;

import java.util.ArrayList;
import java.util.List;


public class FragmentAttrebute extends Fragment implements BottonShettCallback{
    FragmentAttrebuteBinding binding;
    RecyclerView recyclerView;
    SpinnerAdaptor spinnerAdaptor;
    AttrebuteViewModel viewModel;
    SharedPreferences sharedPreferences;
    ArrayList<AtttrModel> spinnerlist=new ArrayList<>();
    int adaptorItemCount;
    SpinnerBottomSheet spinnerBottomSheet;
    int position;
    String id_for_emty_attr;
    String check_string="0";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_attrebute,container,false);
        viewModel=new ViewModelProvider(this).get(AttrebuteViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        initVews();
        return binding.getRoot();
    }

    private void initVews() {
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id=getArguments().getString("id");
        String type=getArguments().getString("type");
        Log.d("DSgsdgsdg", "onViewCreated: "+id);
        viewModel.getAttrebute(id,type);

        viewModel.spinnerModelMutableLiveData.observe(getViewLifecycleOwner(),t ->{
            spinnerAdaptor=new SpinnerAdaptor(t,this);
            recyclerView.setAdapter(spinnerAdaptor);
            adaptorItemCount=recyclerView.getAdapter().getItemCount();
            id_for_emty_attr=t.getAttrebuteData().get(0).getId();
            setIndec();
        });


        binding.save.setOnClickListener( o->{
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("collaction_id",getData().toString());
            editor.putString("type","");
            editor.putString("title","ویژگی");
            editor.putString("emty_status",id_for_emty_attr);
            editor.apply();
            Navigation.findNavController(o).navigate(R.id.action_fragmentAttrebute_to_add);
            Log.d("sgfsegesgeg", "onViewCreated: "+getData().toString());


        });

    }

    public void setIndec(){
        for (int i = 0; i < adaptorItemCount; i++) {
            spinnerlist.add(new AtttrModel("","",""));

        }
    }
    public List<String> getData(){
        List<String> list=new ArrayList<>();
        for (int i = 0; i < spinnerlist.size(); i++) {
            String[] strings=spinnerlist.get(i).getValue().split("@");
            list.add(spinnerlist.get(i).getId());
            list.add(strings[0]);
            list.add(spinnerlist.get(i).getIscheck());
        }
        return list;
    }

    @Override
    public void onClickSpinner(String id, List<String> values, int position) {
        this.position=position;
        spinnerBottomSheet=new SpinnerBottomSheet(values,id,attrCallback);
        spinnerBottomSheet.show(getActivity().getSupportFragmentManager(),"");
    }
    SpinnerBottomSheet.AttrCallback attrCallback=new SpinnerBottomSheet.AttrCallback() {
        @Override
        public void getAttr(String id, String value, String value_id,boolean isCheck) {
            spinnerBottomSheet.dismiss();
            Log.d("sdgsdbmm", "ItemClick:--- "+value);
            Log.d("sdgsdbmm", "ItemClick: -----"+value_id);
            Log.d("sdgsdbmm", "ItemClick: -----"+id);
            Log.d("sdgsdbmm", "ItemClick: -----"+isCheck);
            if (isCheck){
                check_string="1";
            }else check_string="0";

            spinnerAdaptor.setText(value,position);
            AtttrModel atttrModel=new AtttrModel(id,value_id,check_string);
            spinnerlist.set(position,atttrModel);



        }
    };


}