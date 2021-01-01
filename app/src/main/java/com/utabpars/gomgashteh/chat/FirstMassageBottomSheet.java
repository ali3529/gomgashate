package com.utabpars.gomgashteh.chat;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.AddImageAnnouncmentAdaptor;
import com.utabpars.gomgashteh.databinding.FragmentFirstMassageBottomSheetBinding;
import com.utabpars.gomgashteh.fragment.BottomSheetChooseImage;
import com.utabpars.gomgashteh.interfaces.PassDataCallBack;
import com.utabpars.gomgashteh.model.RmModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FirstMassageBottomSheet extends BottomSheetDialogFragment {
FragmentFirstMassageBottomSheetBinding binding;
public String anounccer_id;
public String receive_id;
public String sender_id;
    BottomSheetChooseImage bottomSheetChooseImage;
    RecyclerView recyclerView;
    AddImageAnnouncmentAdaptor adaptor;
    List<Uri> uriList=new ArrayList<>();
    List<MultipartBody.Part> partLists=new ArrayList<>();
    ChatAuthViewModel viewModel;

    public MutableLiveData<String> closeBottensheet=new MutableLiveData<>();


    public FirstMassageBottomSheet(String anounccer_id, String receive_id, String sender_id) {
        this.anounccer_id = anounccer_id;
        this.receive_id = receive_id;
        this.sender_id=sender_id;
    }

    public void getInfo(String anounccer_id, String receive_id, String sender_id){
        this.anounccer_id = anounccer_id;
        this.receive_id = receive_id;
        this.sender_id=sender_id;
    }

    public FirstMassageBottomSheet() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_first_massage_bottom_sheet,container,false);

        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        viewModel=new ViewModelProvider(this).get(ChatAuthViewModel.class);
        bottomSheetChooseImage=new BottomSheetChooseImage();
        recyclerView=binding.imageFirstMassage;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if (binding.masage.getText().toString().length()!=0){
                 viewModel.sendFirstMassage(partLists,fetchData());
                 binding.firstMassageProgress.setVisibility(View.VISIBLE);
                 binding.textinputlayout.setVisibility(View.GONE);
                 Log.d("iufdsbfdhs", "onClick: onsss");
             }else {
                 Toast.makeText(getActivity(), "لطفا پیام خود را وارد کنید", Toast.LENGTH_SHORT).show();
             }

            }
        });


        binding.attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetChooseImage.show(getActivity().getSupportFragmentManager(),"c");
                bottomSheetChooseImage.passData(passDataCallBack);
            }
        });


        viewModel.firstChatStatus.observe(getViewLifecycleOwner(), new Observer<StatusModel>() {
            @Override
            public void onChanged(StatusModel rmModel) {
                Toast.makeText(getContext(), rmModel.getMassage()+rmModel.getStatus(), Toast.LENGTH_SHORT).show();
                binding.firstMassageProgress.setVisibility(View.GONE);
                binding.textinputlayout.setVisibility(View.VISIBLE);
                closeBottensheet.setValue(rmModel.getStatus());
            }
        });
    }



    PassDataCallBack passDataCallBack=new PassDataCallBack() {
        @Override
        public void passUri(Uri uri, MultipartBody.Part partList) {
            Toast.makeText(getContext(), "hi bech", Toast.LENGTH_SHORT).show();
            uriList.add(uri);
            partLists.add(partList);
            adaptor=new AddImageAnnouncmentAdaptor(uriList);
            recyclerView.setAdapter(adaptor);
            bottomSheetChooseImage.dismiss();

        }
    };

    private HashMap<String,RequestBody> fetchData(){
        HashMap<String , RequestBody> massageData=new HashMap<>();
        RequestBody senderID=RequestBody.create(MediaType.parse("sender_id"),sender_id);
        RequestBody massage=RequestBody.create(MediaType.parse("message"),binding.masage.getText().toString());
        RequestBody statusTicket=RequestBody.create(MediaType.parse("statusTicket"),"first");
        RequestBody announcementId=RequestBody.create(MediaType.parse("announcement_id"),anounccer_id);
        RequestBody receiverId=RequestBody.create(MediaType.parse("receiver_id"),receive_id);
        RequestBody ticketId=RequestBody.create(MediaType.parse("ticket_id"),"");

      massageData.put("sender_id",senderID);
      massageData.put("message",massage);
      massageData.put("statusTicket",statusTicket);
      massageData.put("announcement_id",announcementId);
      massageData.put("receiver_id",receiverId);
      massageData.put("ticket_id",ticketId);

        Log.d("dfgdfgfd", "fetchData: sender_id "+sender_id);
        Log.d("dfgdfgfd", "fetchData:  anouncment_id"+anounccer_id);
        Log.d("dfgdfgfd", "fetchData:  receive id"+receive_id);

      return massageData;
    }
}