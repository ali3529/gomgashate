package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.ImageSliderAdaptor;
import com.utabpars.gomgashteh.chat.ChatAuthViewModel;
import com.utabpars.gomgashteh.chat.ChatStatusModel;
import com.utabpars.gomgashteh.chat.FirstMassageBottomSheet;
import com.utabpars.gomgashteh.chat.LoginAlertBottomSheet;
import com.utabpars.gomgashteh.chat.reportchat.FragmentReportBottomSheet;
import com.utabpars.gomgashteh.databinding.FragmentAnnouncmentDetailBinding;
import com.utabpars.gomgashteh.imagehelper.FragmentOpenImage;
import com.utabpars.gomgashteh.interfaces.ChatCallBack;
import com.utabpars.gomgashteh.markannouncment.MarkModel;
import com.utabpars.gomgashteh.markannouncment.MarkViewModel;
import com.utabpars.gomgashteh.model.DetailModel;
import com.utabpars.gomgashteh.viewmodel.DetailViewModel;

import java.util.ArrayList;
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

    MarkViewModel markViewModel;
    String edit_status;
    String share_link;
    FragmentReportBottomSheet fragmentReportBottomSheet=new FragmentReportBottomSheet();


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
        markViewModel=new ViewModelProvider(this).get(MarkViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.bringToFront();
        binding.setProgress(true);
        int id=getArguments().getInt("id");
        String code=getArguments().getString("code");
        try {
            edit_status=getArguments().getString("edit");
        }catch (Exception e){
            edit_status="a";
        }

        user_id =sharedPreferences.getString("user_id","000");
        user_status=sharedPreferences.getBoolean("user_login",false);
        viewModel= new ViewModelProvider(this).get(DetailViewModel.class);
        try {
            //get detail whit id
            if (id==0){
                viewModel.getDetail(0,user_id,code);
            }else {
                viewModel.getDetail(id,user_id,"");
            }

        }catch (Exception e){
            //get detail whit share link
            viewModel.getDetail(0,user_id,code);
        }

        viewModel.getView(binding);
        binding.setViemodel(viewModel);
        binding.setChatviewmodel(this);
        binding.setDid(id);
        binding.setUser(user_id);
        dataMutableLiveData=viewModel.getMutableDetail();
        chatAuthViewModel.ChatInterface(this);





        dataMutableLiveData.observe(getViewLifecycleOwner(), new Observer<DetailModel.Data>() {
            @Override
            public void onChanged(DetailModel.Data data) {
                setSlider(data.getPictures());
                binding.setProgress(false);
                binding.setDetails(data);
                anouns_id=String.valueOf(data.getId());
                sender_id=data.getAnnouncer_id();
                title=data.getTitle();

                //hide attr
                if (data.getType().equals("گم شده")){
                    binding.layoutAttrebuts.setVisibility(View.VISIBLE);
                }else {
                    binding.layoutAttrebuts.setVisibility(View.GONE);
                }




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

                if (data.isMark()){
                    binding.mark.setImageResource(R.drawable.ic_bookmark_selected24);

                }else {

                    binding.mark.setImageResource(R.drawable.ic_bookmark_unselected24);

                }
                share_link=data.getShareLink();

                if (data.isReport()){
                    binding.report.setVisibility(View.GONE);
                }
                if (user_id.equals(data.getId())){
                    binding.report.setVisibility(View.GONE);
                }

                if (data.getPishkhan().equals("0")){
                    binding.pishkan.setVisibility(View.GONE);
                }



               fragmentReportBottomSheet.getList(data.getReport_list());
                fragmentReportBottomSheet.getAnnounceid(anouns_id,user_id);
                if (data.getType().equals("پیدا شده")){
                    binding.rewardLayout.setVisibility(View.INVISIBLE);
                }
                if (data.getReward().equals("0 تومان")){
                    binding.rewardLayout.setVisibility(View.INVISIBLE);
                }

                if (data.getShowAddress().equals("0")){
                    binding.addressLayout.setVisibility(View.GONE);
                    binding.tellNameLayout.setVisibility(View.GONE);
                }else {
                    binding.addressLayout.setVisibility(View.VISIBLE);
                    binding.showName.setText(data.getShowAddress());
                    binding.tellNameLayout.setVisibility(View.VISIBLE);
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
        markViewModel.markModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<MarkModel>() {
            @Override
            public void onChanged(MarkModel markModel) {
                Toast.makeText(getContext(), markModel.getMessage(), Toast.LENGTH_SHORT).show();
                if (markModel.isMark()){

                    binding.mark.setImageResource(R.drawable.ic_bookmark_selected24);

                }else {
                    binding.mark.setImageResource(R.drawable.ic_bookmark_unselected24);


                }
            }
        });



        binding.share.setOnClickListener( o ->{
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, share_link);
            startActivity(Intent.createChooser(shareIntent, "لینک آگهی"));
        });

        fragmentReportBottomSheet.reportResponsLiveData.observe(getViewLifecycleOwner(), t->{

            fragmentReportBottomSheet.dismiss();
        });
    }

    private void bookmanrStatus() {
        binding.mark.setOnClickListener(v ->{
//            if (isMark){
            if (user_status){
                markViewModel.markAnnouncement(user_id,anouns_id);
            }else {
                Toast.makeText(getContext(), "لطفا از قسمت پروفایل وارد حساب کارری خود شوید", Toast.LENGTH_SHORT).show();
            }


        });
    }


    public void setSlider(List<String> pic){
        sliderAdaptor=new ImageSliderAdaptor(pic,imageCallback);
        sliderView.setSliderAdapter(sliderAdaptor);
        sliderAdaptor.notifyDataSetChanged();

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
                    firstMassageBottomSheet.getInfo(anouns_id,sender_id,user_id);
                    Log.d("kjnkugv", "ChatListener: "+anouns_id+"-"+sender_id+"-"+user_id);
                    if (chatStatusModel.getAttributes().isEmpty()){

                    }else {
                        firstMassageBottomSheet.getAttr(chatStatusModel.getAttributes());
                   //whit attrebute

                    }



                    firstMassageBottomSheet.show(getActivity().getSupportFragmentManager(),"massage");
                }else if (chatStatusModel.getStatus_ticket().equals("second")){
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

    @Override
    public void ChatErrorListener() {
        binding.chatProgress.setVisibility(View.GONE);
    }

    public void arrowBack(){
        Navigation.findNavController(getView()).navigateUp();
    }
    
    public void goToRulst(){
  Navigation.findNavController(getView()).navigate(R.id.action_fragmentAnnouncmentDetail_to_fragmentRuls);
    }


    public void reportAnnouncment(){
       if (sharedPreferences.getBoolean("user_login",false)){
            fragmentReportBottomSheet.show(getActivity().getSupportFragmentManager(),"report_announcment");

        }else {
           Snackbar.make(getView(),"برای ثبت گزارش باید وارد حساب گمگشته خود شوید",Snackbar.LENGTH_SHORT)
                   .setTextColor(getResources().getColor(R.color.red)).show();

       }

    }

    ImageSliderAdaptor.imageCallback imageCallback=new ImageSliderAdaptor.imageCallback() {
        @Override
        public void ImageOnClick(List<String> url) {
            ArrayList<String> strings=new ArrayList<>();
            strings.addAll(url);
            Bundle bundle=new Bundle();
            bundle.putStringArrayList("url",strings);

           // FragmentOpenImage.getImages(url);
            Navigation.findNavController(getView()).navigate(R.id.action_fragmentAnnouncmentDetail_to_fragmentOpenImage,bundle);
        }
    };


}