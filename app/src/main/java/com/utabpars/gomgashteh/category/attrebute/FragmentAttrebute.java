package com.utabpars.gomgashteh.category.attrebute;

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
import com.utabpars.gomgashteh.databinding.FragmentAttrebuteBinding;

import java.util.ArrayList;
import java.util.List;


public class FragmentAttrebute extends Fragment implements SpiinerCallback{
    FragmentAttrebuteBinding binding;
    RecyclerView recyclerView;
    SpinnerAdaptor spinnerAdaptor;
    AttrebuteViewModel viewModel;
    SharedPreferences sharedPreferences;
    ArrayList<AtttrModel> spinnerlist=new ArrayList<>();
    int adaptorItemCount;




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
        viewModel.getAttrebute(id,type);

        viewModel.spinnerModelMutableLiveData.observe(getViewLifecycleOwner(),t ->{
            spinnerAdaptor=new SpinnerAdaptor(t, this);
            recyclerView.setAdapter(spinnerAdaptor);
            adaptorItemCount=recyclerView.getAdapter().getItemCount();
            setIndec();
        });


        binding.save.setOnClickListener( o->{
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("collaction_id",getData().toString());
            editor.putString("type","");
            editor.putString("title","ویژگی");
            editor.apply();
            Navigation.findNavController(o).navigate(R.id.action_fragmentAttrebute_to_add);
            Log.d("sgfsegesgeg", "onViewCreated: "+getData().toString());


        });

    }


    @Override
    public void SpinnerItemCallBack(String id, String value,boolean isCkeck,int position) {
        String ischeck;

        if (isCkeck){
            ischeck="1";
        }else {
            ischeck="0";
        }
        AtttrModel atttrModel=new AtttrModel(id,value,ischeck);

        spinnerlist.set(position,atttrModel);

        for (int i = 0; i < spinnerlist.size(); i++) {
            Log.d("sdgsdvbbvmmmm", "SpinnerItemCallBack: "+spinnerlist.get(i).getId());
            Log.d("sdgsdvbbvmmmm", "SpinnerItemCallBack: "+spinnerlist.get(i).getValue());
            Log.d("sdgsdvbbvmmmm", "SpinnerItemCallBack: "+spinnerlist.get(i).getIscheck());
            Log.d("sdgsdvbbvmmmm", "SpinnerItemCallBack: "+spinnerlist.size());
            Log.d("sdgsdvbbvmmmm", "-------------------------------------------------");
        }



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
            list.add(spinnerlist.get(i).getId().replaceAll("\\s+",""));
            list.add(strings[0].replaceAll("\\s+",""));
            list.add(spinnerlist.get(i).getIscheck().replaceAll("\\s+",""));
        }
        return list;
    }

    public void getDataspace(){


        Log.d("fgdfhdfh", "getDataspace: "+getData().get(1).replaceAll("\\s+",""));


    }


}