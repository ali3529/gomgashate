package com.utabpars.gomgashteh.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.chat.deletmassage.DeletMassageViewModel;
import com.utabpars.gomgashteh.chat.phoneconfirm.PhoneConfirmModel;
import com.utabpars.gomgashteh.chat.phoneconfirm.PhoneConfirmViewModel;
import com.utabpars.gomgashteh.chat.reportchat.FragmentReportBottomSheet;
import com.utabpars.gomgashteh.databinding.FragmentChatDetailBinding;
import com.utabpars.gomgashteh.fragment.BottomSheetChooseImage;
import com.utabpars.gomgashteh.imagehelper.FragmentOpenImage;
import com.utabpars.gomgashteh.interfaces.PassDataCallBack;
import com.utabpars.gomgashteh.model.BlockModel;
import com.utabpars.gomgashteh.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FragmentChatDetail extends Fragment {

    FragmentChatDetailBinding binding;
    RecyclerView recyclerView;
    MassageAdaptor massageAdaptor;
    List<TicketResponseModel.Massage> TicketResponseModels =new ArrayList<>();
    SharedPreferences sharedPreferences;
    String user_id,ticket_id,title,recever_id,anouncer_id;
    MutableLiveData<Boolean> block=new MutableLiveData<>();
    TicketViewModel viewModel;
    BottomSheetChooseImage bottomSheetChooseImage=new BottomSheetChooseImage();
    ChatAuthViewModel chatAuthViewModel;
    Toolbar toolbar;
    boolean isBlock=false;
    MutableLiveData<BlockModel> blockLiveData=new MutableLiveData<>();
    FragmentReportBottomSheet fragmentReportBottomSheet=new FragmentReportBottomSheet();
    List<String> list=new ArrayList<>();
    boolean test1;
    boolean test2;
    MutableLiveData<Boolean> isReport=new MutableLiveData<>();
    boolean isReport_clicked;
    PhoneConfirmViewModel phoneConfirmViewModel;
    DeletMassageViewModel deletMassageViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_chat_detail,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initViews();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user_id =sharedPreferences.getString("user_id","0000");
        ticket_id=getArguments().getString("ticket_id");
        title=getArguments().getString("title");
        recever_id=getArguments().getString("recever_id");
        anouncer_id=getArguments().getString("announcer_id");
        try {
            //    block=getArguments().getBoolean("block");

        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


        binding.title.setText(title);
        viewModel.getTicket(ticket_id,user_id);
        viewModel.ticketMutableLiveData.observe(getViewLifecycleOwner(), new Observer<TicketResponseModel>() {
            @Override
            public void onChanged(TicketResponseModel ticketResponseModel) {
                massageAdaptor.getMassageList(ticketResponseModel.getMassages());
                recyclerView.setAdapter(massageAdaptor);
                isReport_clicked=ticketResponseModel.isReport();
                if (ticketResponseModel.isReport()){
                    isReport.setValue(ticketResponseModel.isReport());

                }else {
                    block.setValue(ticketResponseModel.isBlock());
                }

                setBlockAndmassageStatus(ticketResponseModel);
                list=ticketResponseModel.getReport_list();

                fragmentReportBottomSheet.getList(ticketResponseModel.getReport_list());

                scrollToBottom(recyclerView);

                if (ticketResponseModel.isUser_confirmation()){
                    binding.phoneCheck.setChecked(true);
                    binding.phoneCheck.setEnabled(false);
                    if (ticketResponseModel.isRespondent_user()){
                        binding.phoneText.setText(ticketResponseModel.getPhone_number());
                    }
                }else binding.phoneCheck.setChecked(false);

            }


        });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.masage.getText().toString().length()<=0){
                    Toast.makeText(getContext(), "لطفا متن پیام خود را وارد کنید", Toast.LENGTH_SHORT).show();
                }else {
                    String m=binding.masage.getText().toString();
                    chatAuthViewModel.sendFirstMassage(null,fetchData(user_id,m,anouncer_id,recever_id,ticket_id));
                    massageAdaptor.addSendMassage(new TicketResponseModel.Massage(m,"2","0"));
                    massageAdaptor.notifyDataSetChanged();
                    binding.masage.setText("");
                    scrollToBottom(recyclerView);

                }

            }
        });


        binding.attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.checkPermissionStorage(getContext()) && Utils.checkPermissionStorageWrite(getContext())) {
                    bottomSheetChooseImage.show(getActivity().getSupportFragmentManager(), "attachsecond");
                    bottomSheetChooseImage.passData(passDataCallBack);
                }

            }
        });


        binding.backArrow.setOnClickListener( o ->{
            Navigation.findNavController(getView()).navigateUp();
        });

        chatAuthViewModel.firstChatStatus.observe(getViewLifecycleOwner(), new Observer<StatusModel>() {
            @Override
            public void onChanged(StatusModel statusModel) {
                //massage send
               // viewModel.getTicket(ticket_id,user_id);

            }
        });

        refresh();

        fragmentReportBottomSheet.reportResponsLiveData.observe(getViewLifecycleOwner(), t->{

                binding.setMassagelayoutvisibility(false);
                binding.setSecendmassagevisibility(true);
                viewModel.getTicket(ticket_id,user_id);
                fragmentReportBottomSheet.dismiss();




        });
        fragmentReportBottomSheet.notReportResponsLiveData.observe(getViewLifecycleOwner(),t->{
            fragmentReportBottomSheet.dismiss();
            viewModel.getTicket(ticket_id,user_id);

        });


        binding.phoneCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneConfirmViewModel.confirmPhone(user_id,ticket_id);
            }
        });

        phoneConfirmViewModel.phoneConfirmModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<PhoneConfirmModel>() {
            @Override
            public void onChanged(PhoneConfirmModel phoneConfirmModel) {
                Toast.makeText(getContext(), phoneConfirmModel.getMassage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBlockAndmassageStatus(TicketResponseModel ticketResponseModel) {
        if (ticketResponseModel.isBlockFromAnnouncer()){
            binding.setMassagelayoutvisibility(false);
            binding.setSecendmassagevisibility(true);
            binding.setMassage("کاربر گرامی شما مسدود شداید و امکان ارسال پیام ندارید ");
        }else {
            if (ticketResponseModel.status.equals("0")){
                test1=false;
                test2=true;

                binding.setMassage("کاربر گرامی شما پیام خود را قبلا ارسال کردید لطفا منتظر پاسخ آگهی دهنده باشید");
            }else {

                test1=true;
                test2=false;
            }
            //always true
            if (ticketResponseModel.isBlock()){
                test1=false;
                test2=true;
                binding.setMassage("کاربر گرامی شما این آگهی را مسدود کرده اید و امکان ارسال پیام ندارید  ");

            }else {
                if (ticketResponseModel.status.equals("0")){
                    test1=false;
                    test2=true;
                }else {
                    test1=true;
                    test2=false;
                }



            }
            binding.setMassagelayoutvisibility(test1);
            binding.setSecendmassagevisibility(test2);
        }
    }


    private void initViews() {
        binding.setMassagelayoutvisibility(true);
        binding.setSecendmassagevisibility(false);
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        sharedPreferences=getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);
        viewModel=new ViewModelProvider(this).get(TicketViewModel.class);
        chatAuthViewModel=new ViewModelProvider(this).get(ChatAuthViewModel.class);
        recyclerView=binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        massageAdaptor=new MassageAdaptor(deleteCallback);
        toolbar=binding.toolbar;
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        phoneConfirmViewModel=new ViewModelProvider(this).get(PhoneConfirmViewModel.class);
        deletMassageViewModel=new ViewModelProvider(this).get(DeletMassageViewModel.class);
    }

    private void scrollToBottom(final RecyclerView recyclerView) {
        // scroll to last item to get the view of last item
        try {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            final RecyclerView.Adapter adapter = recyclerView.getAdapter();
            final int lastItemPosition = adapter.getItemCount() - 1;

            layoutManager.scrollToPositionWithOffset(lastItemPosition, 0);
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    // then scroll to specific offset
                    View target = layoutManager.findViewByPosition(lastItemPosition);
                    if (target != null) {
                        int offset = recyclerView.getMeasuredHeight() - target.getMeasuredHeight();
                        layoutManager.scrollToPositionWithOffset(lastItemPosition, offset);
                    }
                }
            });
        }catch (Exception e){

        }



    }

    PassDataCallBack passDataCallBack=new PassDataCallBack() {
        @Override
        public void passUri(Uri uri, MultipartBody.Part partList) {

            List<MultipartBody.Part> imageList=new ArrayList<>();
            bottomSheetChooseImage.dismiss();
            imageList.add(partList);
            chatAuthViewModel.sendFirstMassage(imageList,fetchData(user_id,"",anouncer_id,recever_id,ticket_id));
            massageAdaptor.addSendMassage(new TicketResponseModel.Massage("","4","0",String.valueOf(uri)));
            massageAdaptor.notifyDataSetChanged();
            scrollToBottom(recyclerView);
        }
    };

    private HashMap<String, RequestBody> fetchData(String sender_id ,String massages,String anounccer_id,String receive_id,String ticket_id){
        HashMap<String , RequestBody> massageData=new HashMap<>();
        RequestBody senderID=RequestBody.create(MediaType.parse("sender_id"),sender_id);
        RequestBody massage=RequestBody.create(MediaType.parse("message"),massages);
        RequestBody statusTicket=RequestBody.create(MediaType.parse("statusTicket"),"second");
        RequestBody announcementId=RequestBody.create(MediaType.parse("announcement_id"),anounccer_id);
        RequestBody receiverId=RequestBody.create(MediaType.parse("receiver_id"),receive_id);
        RequestBody ticketId=RequestBody.create(MediaType.parse("ticket_id"),ticket_id);

        massageData.put("sender_id",senderID);
        massageData.put("message",massage);
        massageData.put("statusTicket",statusTicket);
        massageData.put("announcement_id",announcementId);
        massageData.put("receiver_id",receiverId);
        massageData.put("ticket_id",ticketId);

//        Log.d("dfgdfgfd", "fetchData: sender_id "+sender_id);
//        Log.d("dfgdfgfd", "fetchData:  massages"+massages);
//        Log.d("dfgdfgfd", "fetchData:  anounccer_id"+anounccer_id);
//        Log.d("dfgdfgfd", "fetchData:  receive_id"+receive_id);
//        Log.d("dfgdfgfd", "fetchData:  ticket_id"+ticket_id);

        return massageData;
    }
    public void refresh(){
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                viewModel.getTicket(ticket_id,user_id);
                scrollToBottom(recyclerView);
                refresh();
            }
        }.start();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.chat_menu,menu);
        block.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    menu.getItem(0).setTitle("ازاد");


                }else {
                    menu.getItem(0).setTitle(" مسدود");

                }
            }
        });

        isReport.observe(getViewLifecycleOwner(),t ->{
            if (t){
                menu.getItem(1).setTitle("ازاد");
            }else {
                menu.getItem(1).setTitle("'گزارش");
            }

            //todo ttt
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        blockLiveData.observe(getViewLifecycleOwner(), new Observer<BlockModel>() {
            @Override
            public void onChanged(BlockModel blockModel) {

                if (blockModel.isBlock()){
                    if (item.getItemId()==R.id.block){
                        item.setTitle("ازاد");
                        binding.setMassagelayoutvisibility(false);
                        binding.setSecendmassagevisibility(true);
                        binding.setMassage("کاربر گرامی شما این آگهی را مسدود کرده اید و امکان ارسال پیام ندارید  ");

                    }else {

                    }



                }else {
                    if (item.getItemId()==R.id.block) {
                        item.setTitle(" مسدود");
                        binding.setMassagelayoutvisibility(true);
                        binding.setSecendmassagevisibility(false);

                    }else {

                    }



                }

            }
        });

        fragmentReportBottomSheet.reportResponsLiveData.observe(getViewLifecycleOwner(), t->{

            if (item.getItemId()==R.id.report) {
                if (t) {
                    item.setTitle("ازاد");

                } else {

                    item.setTitle("گزارش");
                }
            }

     });




        switch (item.getItemId()){
            case R.id.block:

                blockUser(user_id,recever_id);

                break;
            case R.id.report:
                if (isReport_clicked){
                    fragmentReportBottomSheet.show(getActivity().getSupportFragmentManager(),"");
                    fragmentReportBottomSheet.reportUser(user_id,recever_id,"ثث");
                }else {
                    fragmentReportBottomSheet.show(getActivity().getSupportFragmentManager(),"");
                    fragmentReportBottomSheet.getUsers(user_id,recever_id);

                }


        }

        return super.onOptionsItemSelected(item);
    }


    public void blockUser(String blocker,String blocked){
        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.blockUeer(blocker,blocked)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BlockModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull BlockModel blockModel) {
                        Toast.makeText(getContext(), blockModel.getMassage(), Toast.LENGTH_SHORT).show();

                        blockLiveData.setValue(blockModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                }));
    }

    MassageAdaptor.DeleteCallback deleteCallback=new MassageAdaptor.DeleteCallback() {
        @Override
        public void ItemDeleted(int answerId) {
            deletMassageViewModel.deleteMassage(answerId);
            viewModel.getTicket(ticket_id,user_id);
        }

        @Override
        public void ItemImageZoom(String url) {
            ArrayList<String> lists=new ArrayList<>();
            lists.add(url);
            Bundle bundle=new Bundle();
            bundle.putStringArrayList("url",lists);

            Navigation.findNavController(getView()).navigate(R.id.action_fragmentChatDetail_to_fragmentzoompicture,bundle);
        }
    };
}