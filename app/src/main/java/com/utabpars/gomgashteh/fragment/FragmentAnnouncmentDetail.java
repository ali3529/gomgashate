package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.ImageSliderAdaptor;
import com.utabpars.gomgashteh.chat.ChatAuthViewModel;
import com.utabpars.gomgashteh.chat.ChatStatusModel;
import com.utabpars.gomgashteh.chat.FirstMassageBottomSheet;
import com.utabpars.gomgashteh.chat.LoginAlertBottomSheet;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncmentDetailBinding;
import com.utabpars.gomgashteh.interfaces.ChatCallBack;
import com.utabpars.gomgashteh.model.DetailModel;
import com.utabpars.gomgashteh.utils.NavigateHelper;
import com.utabpars.gomgashteh.viewmodel.DetailViewModel;

import java.util.List;

public class FragmentAnnouncmentDetail extends Fragment implements ChatCallBack {
    MutableLiveData<DetailModel.Data> dataMutableLiveData;
    ImageSliderAdaptor sliderAdaptor;
    SliderView sliderView;
    FragmentAnnouncmentDetailBinding binding;
    DetailViewModel viewModel;
    ChatAuthViewModel chatAuthViewModel;
    SharedPreferences sharedPreferences;
    String anouns_id, user_id,sender_id,title;
    boolean user_status;
    LoginAlertBottomSheet loginAlertBottomSheet;
    FirstMassageBottomSheet firstMassageBottomSheet;
    boolean isMark=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_announcment_detail,container,false);
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        chatAuthViewModel=new ViewModelProvider(getActivity()).get(ChatAuthViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);
        firstMassageBottomSheet=new FirstMassageBottomSheet();
        // Inflate the layout for this fragment
        loginAlertBottomSheet=new LoginAlertBottomSheet();
        sliderView=binding.slider;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setProgress(true);
        int id=getArguments().getInt("id");
        Log.d("sdvdsvsdv", "onViewCreated: "+id);
        viewModel= new ViewModelProvider(this).get(DetailViewModel.class);
        viewModel.getDetail(id);
        viewModel.getView(binding);
        binding.setViemodel(viewModel);
        binding.setChatviewmodel(this);
        binding.setDid(id);
        dataMutableLiveData=viewModel.getMutableDetail();
        chatAuthViewModel.ChatInterface(this::ChatListener);
        user_id =sharedPreferences.getString("user_id","0000");
        user_status=sharedPreferences.getBoolean("user_login",false);



        dataMutableLiveData.observe(getViewLifecycleOwner(), new Observer<DetailModel.Data>() {
            @Override
            public void onChanged(DetailModel.Data data) {
                setSlider(data.getPictures());
                binding.setProgress(false);
                binding.setDetails(data);
                anouns_id=String.valueOf(data.getId());
                sender_id=data.getAnnouncer_id();
                title=data.getTitle();
                if (data.getAnnouncer_id().equals(user_id)){
                    binding.chatBtn.setVisibility(View.GONE);
                }else {
                    binding.chatBtn.setVisibility(View.VISIBLE);
                }

                if (data.getOtherCity()==null){
                    binding.othercityLayout.setVisibility(View.GONE);
                }else {
                    for (String s:data.getOtherCity()) {
                        binding.otherCitys.append(s+" , ");
                    }
                }
            }
        });

        bookmanrStatus();



        loginAlertBottomSheet.viewMutableLiveData.observe(getViewLifecycleOwner(), view1 -> {
            //go to login fragment
            // use live data as interface
            Navigation.findNavController(view).navigate(R.id.action_fragmentAnnouncmentDetail_to_fragmentLogin);
            loginAlertBottomSheet.dismiss();
        });

        firstMassageBottomSheet.closeBottensheet.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                firstMassageBottomSheet.dismiss();
            }
        });

        binding.chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    checkChatStatus();


            }
        });
    }

    private void bookmanrStatus() {
        binding.mark.setOnClickListener(v ->{
            if (isMark){
                binding.mark.setImageResource(R.drawable.ic_bookmark_unselected24);
                isMark=false;
            }else {
                binding.mark.setImageResource(R.drawable.ic_bookmark_selected24);
                isMark=true;
            }

        });
    }


    public void setSlider(List<String> pic){
        sliderAdaptor=new ImageSliderAdaptor(pic);
        sliderView.setSliderAdapter(sliderAdaptor);
    }

    public void checkChatStatus(){
        if (user_status){
            binding.chatProgress.setVisibility(View.VISIBLE);
            chatAuthViewModel.chatValidate(user_id,anouns_id);



        }else {


            loginAlertBottomSheet.show(getActivity().getSupportFragmentManager(),"login");

        }



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void ChatListener(ChatStatusModel chatStatusModel) {
        try {
            binding.chatProgress.setVisibility(View.GONE);
            if (chatStatusModel.getBlock_status().equals("no_block")){
                if (chatStatusModel.getStatus_ticket().equals("first")){
                    Log.d("fdhgdfg", "onChanged: first "+anouns_id+"    "+sender_id+" "+user_id);
                    firstMassageBottomSheet.getInfo(anouns_id,sender_id,user_id);


                    firstMassageBottomSheet.show(getActivity().getSupportFragmentManager(),"massage");
                }else if (chatStatusModel.getStatus_ticket().equals("second")){
                    Log.d("fdhgdfg", "onChanged: second "+chatStatusModel.getStatus_ticket());
                    Log.d("fdhgdfg", "onChanged: second "+chatStatusModel.getTicket_id());
                    Bundle bundle=new Bundle();
                    bundle.putString("ticket_id",chatStatusModel.getTicket_id());
                    bundle.putString("title",title);
                    bundle.putString("recever_id",sender_id);
                    bundle.putString("announcer_id",anouns_id);

                    Navigation.findNavController(getView()).navigate(R.id.action_fragmentAnnouncmentDetail_to_fragmentChatDetail,bundle);



                }
            }else {
                Toast.makeText(getActivity(), "امکان چت با این آگهی برای شما وجود ندارد", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "لطفا صبر کنید", Toast.LENGTH_SHORT).show();
        }

    }

    public void arrowBack(){
        Navigation.findNavController(getView()).navigateUp();
    }
}