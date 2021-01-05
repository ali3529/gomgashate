package com.utabpars.gomgashteh.managerAnnouncement;

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
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.databinding.FragmentOptionsBinding;
import com.utabpars.gomgashteh.interfaces.DetileCallBack;


public class FragmentOptions extends Fragment {

    FragmentOptionsBinding binding;
    RecyclerView recyclerView;
    ManagerAdaptor adaptor;
    OptionViewModel viewModel;
    int annoouncment_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.VISIBLE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_options,container,false);
        viewModel=new ViewModelProvider(this).get(OptionViewModel.class);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        annoouncment_id=getArguments().getInt("id");
                recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int id=getArguments().getInt("id");
        viewModel.getTabs(String.valueOf(id));
        binding.load.setVisibility(View.VISIBLE);

        viewModel.manageModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ManageModel>() {
            @Override
            public void onChanged(ManageModel manageModel) {
                binding.load.setVisibility(View.GONE);
                binding.setStatus(manageModel.getStatus());
                adaptor=new ManagerAdaptor(manageModel.getOptions(),detileCallBack);
                recyclerView.setAdapter(adaptor);
                if (manageModel.getStatus_type().equals("1")){
                    binding.stat.setTextColor(getResources().getColor(R.color.orange));
                    binding.fff.setText("آگهی شما ثبت شده و در صف انتشار است. حداکثر تا 4 ساعت دیگر منتشر خواهد شد");
                }else if (manageModel.getStatus_type().equals("2")){
                    binding.stat.setTextColor(getResources().getColor(R.color.colorAccent));
                    binding.fff.setText("آگهی شما در انتظار پرداخت است");
                }else if (manageModel.getStatus_type().equals("3")){
                    binding.stat.setTextColor(getResources().getColor(R.color.greennew));
                    binding.fff.setText("آگهی شما منتشر شده است");
                }else if (manageModel.getStatus_type().equals("4")){
                    binding.stat.setTextColor(getResources().getColor(R.color.red));
                    binding.fff.setText("آگهی شما توسط رد شده است");
                }else if (manageModel.getStatus_type().equals("5")){
                    binding.stat.setTextColor(getResources().getColor(R.color.lowblack));
                    binding.fff.setText("آگهی شما حذف شده است");
                }
            }
        });

    }

   DetileCallBack detileCallBack=new DetileCallBack() {
       @Override
       public void onItemClicked(View view, int id) {
           if (id==3){
               Bundle bundle=new Bundle();
               bundle.putInt("id",annoouncment_id);
               Navigation.findNavController(view).navigate(R.id.action_fragmentOptions_to_editAnnouncementFragment,bundle);
           }
           Toast.makeText(getContext(), ""+id, Toast.LENGTH_SHORT).show();
       }
   };
}