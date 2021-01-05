package com.utabpars.gomgashteh.markannouncment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentMarkBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.myannouncment.MyAnnouncmentAdaptor;

public class FragmentMark extends Fragment {
    FragmentMarkBinding binding;
    MarkViewModel viewModel;
    SharedPreferences sharedPreferences;
    String user_id;
    RecyclerView recyclerView;
    MyAnnouncmentAdaptor adaptor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_mark,container,false);
        viewModel=new ViewModelProvider(this).get(MarkViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_id=sharedPreferences.getString("user_id","000");
        viewModel.myMark(user_id);
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.progressbar.setVisibility(View.VISIBLE);
        viewModel.myMarkMutableLiveData.observe(getViewLifecycleOwner(), new Observer<AnoncmentModel>() {
            @Override
            public void onChanged(AnoncmentModel anoncmentModel) {
                binding.progressbar.setVisibility(View.GONE);
                adaptor=new MyAnnouncmentAdaptor(anoncmentModel.getData(),detileCallBack);
                recyclerView.setAdapter(adaptor);

            }
        });
    }

    DetileCallBack detileCallBack=new DetileCallBack() {
        @Override
        public void onItemClicked(View view, int id) {
            Bundle bundle=new Bundle();
            bundle.putInt("id",id);
            Navigation.findNavController(view).navigate(R.id.action_fragmentMark_to_fragmentAnnouncmentDetail,bundle);
        }
    };

}