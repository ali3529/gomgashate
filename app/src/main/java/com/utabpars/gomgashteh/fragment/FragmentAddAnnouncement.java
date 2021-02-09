package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.AddImageAnnouncmentAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;

import com.utabpars.gomgashteh.category.attrebute.AttrebuteViewModel;
import com.utabpars.gomgashteh.databinding.FragmentAddAnnouncementBinding;
import com.utabpars.gomgashteh.interfaces.PassDataCallBack;
import com.utabpars.gomgashteh.model.SaveAnnouncementModel;
import com.utabpars.gomgashteh.utils.Utils;
import com.utabpars.gomgashteh.viewmodel.AttrebuteNameViewModel;

import java.lang.reflect.Type;
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

import static com.utabpars.gomgashteh.utils.Utils.ReadExternalRequestCode;

public class FragmentAddAnnouncement extends Fragment  {
    FragmentAddAnnouncementBinding binding;
    EditText edTitle, edDescription;
    RelativeLayout save_announcement;
    SharedPreferences shPref,user_status,save_state,category_name;
    SharedPreferences.Editor editor_savestate;
    RadioGroup radioGroup;
    String type;
    String is_address_show="0";
     RecyclerView addImageRecyclerview;
    Intent intent;
    static AddImageAnnouncmentAdaptor addImageAnnouncmentAdaptor;
    static List<Uri> uriList =new ArrayList<>();
    static List<MultipartBody.Part> partLists=new ArrayList<>();
    BottomSheetChooseImage bottomSheetChooseImage;
    String title;
    AttrebuteNameViewModel attrebuteNameViewModel;
    boolean is_clear_category=false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_announcement,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        shPref = getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        user_status=getActivity().getSharedPreferences("user_login",Context.MODE_PRIVATE);
        initViews();
        binding.setFrag(this);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isLogin=user_status.getBoolean("user_login",false);
        if (isLogin){
            binding.setIsLogin(false);
            binding.setIsLogined(true);
        }else {
            binding.setIsLogin(true);
            binding.setIsLogined(false);
        }
        RelativeLayout button=binding.setcategory;
        RelativeLayout city=binding.setcity;
        type=save_state.getString("type","");
        binding.title.setText(save_state.getString("title",""));
        binding.description.setText(save_state.getString("descrip",""));

        if (type.equals("1")){
            binding.find.setChecked(true);
            binding.rootLayout.setVisibility(View.VISIBLE);
            binding.suprise.setVisibility(View.GONE);
            getType(type);
        }else if (type.equals("2")){
            binding.lost.setChecked(true);
            binding.rootLayout.setVisibility(View.VISIBLE);
            binding.suprise.setVisibility(View.VISIBLE);
            getType(type);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_clear_category=true;
                Bundle bundle=new Bundle();
                bundle.putString("type","");

                editor_savestate.putString("title",binding.title.getText().toString());
                editor_savestate.putString("descrip",binding.description.getText().toString());
                editor_savestate.putString("type",type);
                editor_savestate.apply();
                Navigation.findNavController(view).navigate(R.id.action_add_to_list,bundle);
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("navigate","city_add");
                Navigation.findNavController(view).navigate(R.id.action_add_to_fragmentCity,bundle);
            }
        });

        binding.setOthercity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("navigate","otherCity");
                Navigation.findNavController(view).navigate(R.id.action_add_to_fragmentCity,bundle);
            }
        });

        //set category
         title=shPref.getString("title","انتخاب کنید");
        String title_collection=shPref.getString("type","");
        String id=shPref.getString("collaction_id",null);
        String type_collaction=shPref.getString("type",null);
        //todo
        attrebuteNameViewModel.getAttrName(shPref.getString("collaction_id",null));

        binding.setCategory(title);
        binding.setattr.setVisibility(View.GONE);

        binding.categoryName.setText(category_name.getString("category","")+" - "+
                category_name.getString("collection","")+" - "+
                category_name.getString("sub_one","")+" - "+
                category_name.getString("sub_two","")+" - "+
                category_name.getString("sub_three",""));

        attrebuteNameViewModel.nameModelMutableLiveData.observe(getViewLifecycleOwner(),t->{

            if (attrebuteNameViewModel.eventHandle){
                binding.setattr.setVisibility(View.VISIBLE);
                binding.attrName.setText(t);
            }
            attrebuteNameViewModel.setEvant(false);

        });


        //set other city
        if (getSHaredList()==null){
            binding.othercity.setText("انتخاب کنید");
        }else {
            binding.othercity.setText(getSHaredList().size()+"شهر");
        }
        SharedPreferences city_name = getActivity().getSharedPreferences("other_city", Context.MODE_PRIVATE);
        binding.cityName.setText(city_name.getString("otherCity_name",""));



        //set city
        binding.setCity(shPref.getString("province_name","انتخاب کنید")+", "+shPref.getString("city_name",""));
        //notify image change
        try {
            addImageAnnouncmentAdaptor.notifyDataSetChanged();
            addImageRecyclerview.setAdapter(addImageAnnouncmentAdaptor);
        }catch (Exception r){
        }


        save_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edTitle.getText().toString().length()!=0){
                    if (edDescription.getText().toString().length()!=0){
                        if (type!=null){
                            if (!shPref.getString("collaction_id","0").equals("0")){
                                binding.addProgress.setVisibility(View.VISIBLE);
                                sendAnnouncment(fetchdata());

                            }else {
                                Toast.makeText(getContext(), "لطفادسته بندی را مشخص کنید", Toast.LENGTH_SHORT).show();
                            }


                        }else Toast.makeText(getContext(), "لطفا نوع آگهی را مشخص کنید", Toast.LENGTH_SHORT).show();
                    }else    Toast.makeText(getContext(), "لطفا توضیحات آگهی را وارد کنید", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "لطفا عنوان آگهی را وارد کنید", Toast.LENGTH_SHORT).show();




            }
        });


        binding.layoutAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.checkPermissionStorage(getContext()) && Utils.checkPermissionStorageWrite(getContext())) {
                    setImageAnnounsmenet();


                }
            }
        });
        RelativeLayout layout = binding.saveAnnounce;
        binding.checkruls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.checkruls.isChecked()){
                    layout.setVisibility(View.VISIBLE);
                    binding.desablsave.setVisibility(View.GONE);
                }else {
                    layout.setVisibility(View.INVISIBLE);
                    binding.desablsave.setVisibility(View.VISIBLE);

                }
            }
        });
        binding.desablsave.setOnClickListener(t ->{
            Toast.makeText(getContext(), "لطفا قوانین را مطالعه و تایید کنید", Toast.LENGTH_SHORT).show();
        });

        binding.restore.setOnClickListener( o->{
            //todo
            SharedPreferences.Editor add_category=shPref.edit();
            add_category.clear();
            add_category.apply();
            binding.attrName.setText("");
            editor_savestate.clear();
            editor_savestate.apply();
            uriList.clear();
            partLists.clear();
            //othercity
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("other_city", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedPreferences.edit();
            editor1.clear();
            editor1.apply();
            binding.rootLayout.setVisibility(View.GONE);




            type=save_state.getString("type","");
            binding.title.setText(save_state.getString("title",""));
            binding.description.setText(save_state.getString("descrip",""));
             title=shPref.getString("title","انتخاب کنید");
            binding.surpriseText.setText("");
            binding.setCity(shPref.getString("city_name","انتخاب کنید")+", "+shPref.getString("province_name",""));
            binding.setCategory(title);
            binding.radiogroup.clearCheck();



                try {
                    addImageAnnouncmentAdaptor.notifyDataSetChanged();
                }catch (Exception e){

                }

            //set other city
            if (getSHaredList()==null){
                binding.othercity.setText("انتخاب کنید");
            }else {
                binding.othercity.setText(getSHaredList().size()+"شهر");
            }
            SharedPreferences.Editor editor_category=category_name.edit();
            editor_category.clear();
            editor_category.apply();
            binding.cityName.setText("");
            binding.categoryName.setText("");

        });

        binding.help.setOnClickListener(o->{
            //todo set help
            Toast.makeText(getContext(), "راهنما", Toast.LENGTH_SHORT).show();
        });
//        SharedPreferences.Editor editor=shPref.edit();
//        editor.putString("collaction_id","0");
//        editor.apply();
      //  attrebuteNameViewModel.getAttrName(shPref.getString("collaction_id",null));

        if (user_status.getString("user_type","0000").equals("1")){
            binding.pishkanLayout.setVisibility(View.VISIBLE);
        }else {
            binding.pishkanLayout.setVisibility(View.GONE);
        }

    }


    public void setImageAnnounsmenet(){
       // ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, ReadExternalRequestCode);
        bottomSheetChooseImage =new BottomSheetChooseImage();
        bottomSheetChooseImage.show(getActivity().getSupportFragmentManager(),"ModalBottomShee");

        bottomSheetChooseImage.passData(new PassDataCallBack() {
            @Override
            public void passUri(Uri uri, MultipartBody.Part partList) {
                bottomSheetChooseImage.dismiss();
                partLists.add(partList);
                uriList.add(uri);

                addImageAnnouncmentAdaptor=new AddImageAnnouncmentAdaptor(uriList, new AddImageAnnouncmentAdaptor.onDeleteImages() {
                    @Override
                    public void deleteImage(List<Uri> list, int position) {
                        uriList.remove(position);
                        partLists.remove(position);
                        addImageAnnouncmentAdaptor.notifyDataSetChanged();
                    }
                });
                addImageRecyclerview.setAdapter(addImageAnnouncmentAdaptor);

            }
        });
    }
    

    public void onClickRadio(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.find:
                if (checked){
                    type="1";
                    editor_savestate.putString("type",type);
                    editor_savestate.apply();
                    binding.suprise.setVisibility(View.GONE);
                    binding.rootLayout.setVisibility(View.VISIBLE);
                    getType(type);


                }
                //todo

                break;
            case R.id.lost:
                if (checked) {
                    type = "2";
                    editor_savestate.putString("type",type);
                    editor_savestate.apply();
                    binding.suprise.setVisibility(View.VISIBLE);
                    binding.rootLayout.setVisibility(View.VISIBLE);
                    getType(type);
                    binding.setattr.setVisibility(View.GONE);

                }
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback=new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {

                Navigation.findNavController(getView()).navigate(R.id.action_add_to_announcement);


            };

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    private void initViews() {
        edTitle =binding.title;
        edDescription =binding.description;
        save_announcement=binding.saveAnnounce;
        radioGroup=binding.radiogroup;
        addImageRecyclerview=binding.imgRecyclerview;
        addImageRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
        addImageRecyclerview.setHasFixedSize(true);
        save_state=getActivity().getSharedPreferences("save_state",Context.MODE_PRIVATE);
        editor_savestate =save_state.edit();
        category_name = getActivity().getSharedPreferences("save_category_name", Context.MODE_PRIVATE);
        attrebuteNameViewModel =new ViewModelProvider(this).get(AttrebuteNameViewModel.class);


    }

    private void sendAnnouncment(HashMap<String,RequestBody> requestbody){


        ApiInterface apiInterface= ApiClient.getApiClient();
        CompositeDisposable compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(apiInterface.insertAnnouncment(requestbody,partLists)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SaveAnnouncementModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull SaveAnnouncementModel saveAnnouncementModel) {
                        if (saveAnnouncementModel.getResponse().equals("1")){
                            Toast.makeText(getContext(), saveAnnouncementModel.getMasg().get(0), Toast.LENGTH_SHORT).show();
                            binding.addProgress.setVisibility(View.GONE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("id",Integer.parseInt(saveAnnouncementModel.getAnnounce_id()));
                                    bundle.putString("add","add");
                                 ;
                                    Navigation.findNavController(getView()).navigate(R.id.action_add_to_fragmentOptions,bundle);

                                    SharedPreferences.Editor editor=shPref.edit();
                                    editor.clear();
                                    editor.apply();

                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("other_city", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1=sharedPreferences.edit();
                                    editor1.clear();
                                    editor1.apply();

                                    SharedPreferences.Editor add_category=shPref.edit();
                                    add_category.clear();
                                    add_category.apply();
                                    editor_savestate.clear();
                                    editor_savestate.apply();
                                    uriList.clear();
                                    partLists.clear();
                                    SharedPreferences.Editor editor_category=category_name.edit();
                                    editor_category.clear();
                                    editor_category.apply();



                                }
                            },1000);
                        }else {
                            Toast.makeText(getContext(), saveAnnouncementModel.getMasg().get(0), Toast.LENGTH_SHORT).show();
                            binding.addProgress.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(getContext(), "ارتباط با اینترنت قطع میباشد", Toast.LENGTH_SHORT).show();
                        binding.addProgress.setVisibility(View.GONE);

                    }
                }));
    }

    public HashMap<String,RequestBody> fetchdata(){

        HashMap<String,RequestBody> addAnnouncement=new HashMap<>();

        RequestBody title=RequestBody.create(MediaType.parse("title"),edTitle.getText().toString());
        RequestBody utype=RequestBody.create(MediaType.parse("type"),type);
        RequestBody category_id=RequestBody.create(MediaType.parse("case"),shPref.getString("type",""));
        RequestBody attrId=RequestBody.create(MediaType.parse("attr_id"),shPref.getString("emty_status",""));

        RequestBody collection_id=RequestBody.create(MediaType.parse("collection_id"),shPref.getString("collaction_id",""));
        RequestBody province_id=RequestBody.create(MediaType.parse("province_id"),shPref.getString("province_id",""));
        RequestBody city_id=RequestBody.create(MediaType.parse("city_id"),shPref.getString("city_id",""));
        RequestBody detail=RequestBody.create(MediaType.parse("detail"),edDescription.getText().toString());
        RequestBody reward=RequestBody.create(MediaType.parse("reward"),binding.surpriseText.getText().toString());
        if (binding.checkpishkan.isChecked()){
            is_address_show="1";
        }
        RequestBody pishkan=RequestBody.create(MediaType.parse("show_address"),is_address_show);


        RequestBody announcer_id=RequestBody.create(MediaType.parse("announcer_id"),user_status.getString("user_id",""));
        RequestBody other_city;
        if (getSHaredList()==null){
            other_city=RequestBody.create(MediaType.parse("other_city"),"");

        }else {

            other_city=RequestBody.create(MediaType.parse("other_city"),getSHaredList().toString());
            Log.d("fesbebdbbmi", "fetchdata: other city     "+getSHaredList().toString());
        }

//        Log.d("fesbebdbbmi", "fetchdata: title   "+edTitle.getText().toString());
//        Log.d("fesbebdbbmi", "fetchdata: type   "+type);
//        Log.d("fesbebdbbmi", "fetchdata: case   "+shPref.getString("type",""));
//        Log.d("fesbebdbbmi", "fetchdata: attr_id   "+shPref.getString("emty_status",""));
//        Log.d("fesbebdbbmi", "fetchdata: collection_id   "+shPref.getString("collaction_id",""));
//        Log.d("fesbebdbbmi", "fetchdata: province_id    "+shPref.getString("province_id",""));
//        Log.d("fesbebdbbmi", "fetchdata: city_id    "+shPref.getString("city_id",""));
//        Log.d("fesbebdbbmi", "fetchdata: detail    "+edDescription.getText().toString());
//        Log.d("fesbebdbbmi", "fetchdata: reward    "+binding.surpriseText.getText().toString());
//       Log.d("fesbebdbbmi", "fetchdata: announcer_id    "+user_status.getString("user_id",""));
//        Log.d("fesbebdbbmi", "fetchdata: announcer_id    "+is_address_show);




        addAnnouncement.put("title",title);
        addAnnouncement.put("type",utype);
        addAnnouncement.put("case",category_id);
        addAnnouncement.put("collection_id",collection_id);
        addAnnouncement.put("province_id",province_id);
        addAnnouncement.put("city_id",city_id);
        addAnnouncement.put("other_city",other_city);
        addAnnouncement.put("detail",detail);
        addAnnouncement.put("announcer_id",announcer_id);
        addAnnouncement.put("reward",reward);
        addAnnouncement.put("attr_id",attrId);
        addAnnouncement.put("show_address",pishkan);




        return addAnnouncement;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ReadExternalRequestCode) { // با کلید مربوط به خواندن مموری نتیجه را می خوانیم
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // اگر از درخواست دسترسی جواب مثبت برگشت دستورات شرط اجرا می شود.
                startActivityForResult(intent, 100);
                Toast.makeText(getContext(), " دسترسی داده شد", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "دسترسی داده نشد!", Toast.LENGTH_LONG).show(); // در صورتی که جواب منفی گرفتیم پیام دسترسی داده نشد را نمایش می دهیم.
            }
        }
    }



    public  List<String> getSHaredList() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("other_city", Context.MODE_PRIVATE);

        String s = sharedPreferences.getString("otherCityList", null);
        Type type = new TypeToken<List<String>>() {

        }.getType();
        List<String> j = gson.fromJson(s, type);



            return j;


    }

    public void getRegister(){
        Navigation.findNavController(getView()).navigate(R.id.action_add_to_fragmentLogin);
    }
public void goToRuls(){
      //  Navigation.findNavController(getView()).navigate(R.layout);
 Navigation.findNavController(getView()).navigate(R.id.action_add_to_fragmentRuls);
}

public void getType(String type){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Announce_type",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("type",type);
        editor.apply();
}

    @Override
    public void onPause() {
        super.onPause();
        if (is_clear_category){
            SharedPreferences.Editor editor=shPref.edit();
            editor.clear();
            editor.apply();
            binding.setattr.setVisibility(View.GONE);
            SharedPreferences.Editor editor_category=category_name.edit();
            editor_category.clear();
            editor_category.apply();
            binding.categoryName.setText("");
            Log.d("sdvsdvsdv", "onPause: ");
            is_clear_category=false;
        }

    }
}

