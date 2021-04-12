package com.utabpars.gomgashteh.chat;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.AddImageAnnouncmentAdaptor;
import com.utabpars.gomgashteh.adaptor.FirstMassageAttr;
import com.utabpars.gomgashteh.category.attrebute.AtttrModel;
import com.utabpars.gomgashteh.category.attrebute.BottonShettCallback;
import com.utabpars.gomgashteh.category.attrebute.SpinnerAdaptor;
import com.utabpars.gomgashteh.category.attrebute.SpinnerBottomSheet;
import com.utabpars.gomgashteh.databinding.FragmentFirstMassageBottomSheetBinding;
import com.utabpars.gomgashteh.fragment.BottomSheetChooseImage;
import com.utabpars.gomgashteh.interfaces.PassDataCallBack;
import com.utabpars.gomgashteh.model.RmModel;
import com.utabpars.gomgashteh.utils.PlateNumber;
import com.utabpars.gomgashteh.utils.Utils;

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

    List<ChatStatusModel.attributes> attributes;
    RecyclerView attr_recyclerView;
    FirstMassageAttr attrAdaptor;
    SpinnerBottomSheet spinnerBottomSheet;
    ArrayList<AtttrModel> spinnerlist=new ArrayList<>();
    int adaptorItemCount;
    int position,counter=0;
    private List<String> listNassesary=new ArrayList<>();
    boolean is_nassasery_selected;
    boolean ok=false;


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
    public void getAttr(List<ChatStatusModel.attributes> attributes){
        this.attributes=attributes;
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).isNecessary()){
                listNassesary.add(attributes.get(i).getId());
            }
        }

    }

    public FirstMassageBottomSheet() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_first_massage_bottom_sheet,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Log.d("FDbdfbfdbdfb", "onCreateView: ");
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

                if (carddd(attributes.get(0).getCollection_id())){
                    if (getCardAttrValue(attributes.get(0).getCollection_id()).length()==0 && attributes.get(0).isNecessary()){
                        Toast.makeText(getContext(), "لطفا اطلاعات خواسته شده را کامل کنید", Toast.LENGTH_SHORT).show();
                    }else {
                        if (binding.masage.getText().toString().length() != 0) {
                            viewModel.sendFirstMassage(partLists, fetchData());
                            binding.firstMassageProgress.setVisibility(View.VISIBLE);
                            binding.textinputlayout.setVisibility(View.GONE);
                        }else {
                            Toast.makeText(getActivity(), "لطفا پیام خود را وارد کنید", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    binding.cardInfo.setVisibility(View.GONE);
                    binding.attrRecyclerview.setVisibility(View.GONE);
                    if (is_nassasery_selected) {

                        if (binding.masage.getText().toString().length() != 0) {
                            viewModel.sendFirstMassage(partLists, fetchData());
                            binding.firstMassageProgress.setVisibility(View.VISIBLE);
                            binding.textinputlayout.setVisibility(View.GONE);
                            binding.attrRecyclerview.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(getActivity(), "لطفا پیام خود را وارد کنید", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "لطفا ویژگی های الزامی را انتخاب کنید", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        binding.attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.checkPermissionStorage(getContext()) && Utils.checkPermissionStorageWrite(getContext())) {
                    bottomSheetChooseImage.show(getActivity().getSupportFragmentManager(), "c");
                    bottomSheetChooseImage.passData(passDataCallBack);
                }
            }
        });


        viewModel.firstChatStatus.observe(getViewLifecycleOwner(), new Observer<StatusModel>() {
            @Override
            public void onChanged(StatusModel rmModel) {
                Toast.makeText(getContext(), rmModel.getMassage()+rmModel.getStatus(), Toast.LENGTH_SHORT).show();
                binding.firstMassageProgress.setVisibility(View.GONE);
                binding.textinputlayout.setVisibility(View.VISIBLE);
                binding.attrRecyclerview.setVisibility(View.VISIBLE);
                closeBottensheet.setValue(rmModel.getStatus());

            }
        });

        viewModel.firstChatStatusError.observe(getViewLifecycleOwner(),t->{
            binding.firstMassageProgress.setVisibility(View.GONE);
            binding.textinputlayout.setVisibility(View.VISIBLE);
            binding.attrRecyclerview.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "خطا در ارسال اطلاعات", Toast.LENGTH_SHORT).show();
        });

        if (attributes==null){
            is_nassasery_selected=true;
            Log.d("fdbdfbdbgnn", "onViewCreated: if atttt nulll--- ");

        }else {
            if (carddd(attributes.get(0).getCollection_id())){
                Log.d("fdbdfbdbgnn", "onViewCreated: attrrr--- ");
                PlateNumber.setType(attributes.get(0).getCollection_id());
                binding.cardInfo.setVisibility(View.VISIBLE);
                binding.attrRecyclerview.setVisibility(View.GONE);
               // is_nassasery_selected=attributes.get(0).isNecessary();
                is_nassasery_selected=false;

            } else if (listNassesary.size() == 0) {
                is_nassasery_selected = true;
                attr_recyclerView = binding.attrRecyclerview;
                attr_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                attrAdaptor = new FirstMassageAttr(attributes, bottonShettCallback);
                attr_recyclerView.setAdapter(attrAdaptor);
                adaptorItemCount = attr_recyclerView.getAdapter().getItemCount();
                setIndec();
                Log.d("fdbdfbdbgnn", "onViewCreated: else if--- ");
            } else {
                attr_recyclerView = binding.attrRecyclerview;
                attr_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                attrAdaptor = new FirstMassageAttr(attributes, bottonShettCallback);
                attr_recyclerView.setAdapter(attrAdaptor);
                adaptorItemCount = attr_recyclerView.getAdapter().getItemCount();
                setIndec();
                Log.d("fdbdfbdbgnn", "onViewCreated: else --- ");
            }
        }
       
    }



    PassDataCallBack passDataCallBack=new PassDataCallBack() {
        @Override
        public void passUri(Uri uri, MultipartBody.Part partList) {

            uriList.add(uri);
            partLists.add(partList);
            adaptor=new AddImageAnnouncmentAdaptor(uriList, new AddImageAnnouncmentAdaptor.onDeleteImages() {
                @Override
                public void deleteImage(List<Uri> list, int position) {
                    //todo
                    uriList.remove(position);
                    partLists.remove(position);
                    adaptor.notifyDataSetChanged();
                }
            });
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
        RequestBody attr;
        if (carddd(attributes.get(0).getCollection_id())){
            List<String> list=new ArrayList<>();
            list.add(attributes.get(0).getId());
            list.add(getCardAttrValue(attributes.get(0).getCollection_id()));
             attr = RequestBody.create(MediaType.parse("attr"), list.toString());
        }else {
             attr = RequestBody.create(MediaType.parse("attr"), getData().toString());
        }
      massageData.put("sender_id",senderID);
      massageData.put("message",massage);
      massageData.put("statusTicket",statusTicket);
      massageData.put("announcement_id",announcementId);
      massageData.put("receiver_id",receiverId);
      massageData.put("ticket_id",ticketId);
      massageData.put("attr",attr);

        Log.d("sdvsdvdsvdsvsdv", "fetchData: "+ getCardAttrValue(attributes.get(0).getCollection_id()));

      return massageData;
    }

    BottonShettCallback bottonShettCallback=new BottonShettCallback() {
        @Override
        public void onClickSpinner(String id, List<String> values, int positio) {
            position=positio;
             spinnerBottomSheet = new SpinnerBottomSheet(values, id, attrCallback);
            spinnerBottomSheet.show(getActivity().getSupportFragmentManager(),"first");


        }


    };

    public void setIndec(){
        for (int i = 0; i < adaptorItemCount; i++) {
            spinnerlist.add(new AtttrModel("",""));

        }
    }

    SpinnerBottomSheet.AttrCallback attrCallback=new SpinnerBottomSheet.AttrCallback() {
        @Override
        public void getAttr(String id, String value, String value_id,boolean ischeck) {
            spinnerBottomSheet.dismiss();

            attrAdaptor.setText(value,position);
            AtttrModel atttrModel=new AtttrModel(id,value_id);
            spinnerlist.set(position,atttrModel);
            is_nassasery_selected=setNassasery(id);
        }
    };

    public List<String> getData(){
        List<String> list=new ArrayList<>();
        for (int i = 0; i < spinnerlist.size(); i++) {
            String[] strings=spinnerlist.get(i).getValue().split("@");
            list.add(spinnerlist.get(i).getId());
            list.add(strings[0]);
           // list.add(spinnerlist.get(i).getIscheck());
        }
        return list;
    }

    public boolean setNassasery(String id){
        if (listNassesary.size()==0){
            ok=true;
        }else {
            if (ok){

            }else {
                for (int j = 0; j < listNassesary.size(); j++) {
                    if (listNassesary.get(j).equals(id)){
                        counter++;
                        if (counter==listNassesary.size()){
                            ok=true;
                        }
                    }else {

                    }
                }
            }
        }



        return ok;
    }

    public boolean carddd(String type){
        boolean is_card=false;
        is_card= type.equals("39") || type.equals("127")
                || type.equals("116")
                || type.equals("38") || type.equals("117") || type.equals("162") ||
                type.equals("163") || type.equals("118") || type.equals("121") || type.equals("156") || type.equals("157")
                || type.equals("158")  || type.equals("154")  || type.equals("155") || type.equals("165") || type.equals("166");
        Log.d("dfbfdbdfb", "carddd: "+is_card);
        return is_card;
    }

    public String getCardAttrValue(String type){
        String card="0";

        if (type.equals("39") || type.equals("127")){
            card=binding.cardInfo.getCarPlateNumber();
        }else if (type.equals("116")){
            card=binding.cardInfo.getMotoNumber();
        }else if (type.equals("38") || type.equals("117") || type.equals("162") ||
                type.equals("163") || type.equals("118")
                || type.equals("121") || type.equals("156") || type.equals("157")
                || type.equals("158")
                || type.equals("154")  || type.equals("155")){
            card=binding.cardInfo.getCardNumber();
        }
        return card;
    }
}