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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.AddImageAnnouncmentAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;

import com.utabpars.gomgashteh.databinding.FragmentAddAnnouncementBinding;
import com.utabpars.gomgashteh.interfaces.PassDataCallBack;
import com.utabpars.gomgashteh.model.SaveAnnouncementModel;
import com.utabpars.gomgashteh.utils.Utils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.ByteString;

import static com.utabpars.gomgashteh.utils.Utils.ReadExternalRequestCode;

public class FragmentAddAnnouncement extends Fragment  {
    FragmentAddAnnouncementBinding binding;
    EditText edTitle, edDescription;
    Button save_announcement;
    SharedPreferences shPref,user_status;
    RadioGroup radioGroup;
    String type;
    RecyclerView addImageRecyclerview;
    Intent intent;
    AddImageAnnouncmentAdaptor addImageAnnouncmentAdaptor;
    List<Uri> uriList =new ArrayList<>();
    List<MultipartBody.Part> partLists=new ArrayList<>();
    BottomSheetChooseImage bottomSheetChooseImage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_announcement,container,false);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("test","1");
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
        String title=shPref.getString("title_list","انتخاب کنید");
        String title_collection=shPref.getString("title_categ","");
        String id=shPref.getString("id_categ",null);
        String id_list=shPref.getString("id_list",null);

        binding.setCategory(title+", "+title_collection);



        //set other city
        if (getSHaredList()==null){
            binding.othercity.setText("انتخاب کنید");
        }else {
            binding.othercity.setText(getSHaredList().size()+"شهر");
        }



        //set city
        binding.setCity(shPref.getString("city_name","انتخاب کنید")+", "+shPref.getString("province_name",""));


        save_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edTitle.getText().toString().length()!=0){
                    if (edDescription.getText().toString().length()!=0){
                        if (type!=null){
                            sendAnnouncment(fetchdata());
                            Log.d("insetanosdijds", "onClick: goood");



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


    }


    public void setImageAnnounsmenet(){
       // ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, ReadExternalRequestCode);
        bottomSheetChooseImage =new BottomSheetChooseImage();
        bottomSheetChooseImage.show(getActivity().getSupportFragmentManager(),"ModalBottomShee");

        bottomSheetChooseImage.passData(new PassDataCallBack() {
            @Override
            public void passUri(Uri uri, MultipartBody.Part partList) {
                Toast.makeText(getContext(), "dsgsdgsdgsdg", Toast.LENGTH_SHORT).show();
                Log.d("fdyheszhb", "passUri: asfasfsaf"+uriList);
                bottomSheetChooseImage.dismiss();
                partLists.add(partList);
                uriList.add(uri);
                addImageAnnouncmentAdaptor=new AddImageAnnouncmentAdaptor(uriList);
                addImageRecyclerview.setAdapter(addImageAnnouncmentAdaptor);

            }
        });
    }

    public void onClickRadio(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.find:
                if (checked)
                    type="1";
                Log.d("jhvhjvj", "onClickRadio: "+type);
                break;
            case R.id.lost:
                if (checked)
                    type="2";
                Log.d("jhvhjvj", "onClickRadio: "+type);
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
                            Log.d("insetanosdijds", "onSuccess: "+saveAnnouncementModel.getMasg());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Navigation.findNavController(getView()).navigate(R.id.action_add_to_announcement);
                                    SharedPreferences.Editor editor=shPref.edit();
                                    editor.clear();
                                    editor.apply();

                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("other_city", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1=sharedPreferences.edit();
                                    editor1.clear();
                                    editor1.apply();
                                }
                            },2000);
                        }else {
                            Toast.makeText(getContext(), saveAnnouncementModel.getMasg().get(0), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("insetanosdijds", "onSuccess: "+e.toString());
                    }
                }));
    }

    public HashMap<String,RequestBody> fetchdata(){

        HashMap<String,RequestBody> addAnnouncement=new HashMap<>();

        RequestBody title=RequestBody.create(MediaType.parse("title"),edTitle.getText().toString());
        RequestBody utype=RequestBody.create(MediaType.parse("type"),type);
        RequestBody category_id=RequestBody.create(MediaType.parse("category_id"),shPref.getString("id_list",""));
        RequestBody collection_id=RequestBody.create(MediaType.parse("collection_id"),shPref.getString("id_categ",""));
        RequestBody province_id=RequestBody.create(MediaType.parse("province_id"),shPref.getString("province_id",""));
        RequestBody city_id=RequestBody.create(MediaType.parse("city_id"),shPref.getString("city_id",""));
        RequestBody detail=RequestBody.create(MediaType.parse("detail"),edDescription.getText().toString());

        RequestBody announcer_id=RequestBody.create(MediaType.parse("announcer_id"),user_status.getString("user_id",""));
        RequestBody other_city;
        if (getSHaredList()==null){
            other_city=RequestBody.create(MediaType.parse("other_city"),"");
        }else {

            other_city=RequestBody.create(MediaType.parse("other_city"),getSHaredList().toString());
        }





        addAnnouncement.put("title",title);
        addAnnouncement.put("type",utype);
        addAnnouncement.put("category_id",category_id);
        addAnnouncement.put("collection_id",collection_id);
        addAnnouncement.put("province_id",province_id);
        addAnnouncement.put("city_id",city_id);
        addAnnouncement.put("other_city",other_city);
        addAnnouncement.put("detail",detail);
        addAnnouncement.put("announcer_id",announcer_id);



        return addAnnouncement;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ReadExternalRequestCode) { // با کلید مربوط به خواندن مموری نتیجه را می خوانیم
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // اگر از درخواست دسترسی جواب مثبت برگشت دستورات شرط اجرا می شود.
                startActivityForResult(intent, 100);
                Toast.makeText(getContext(), "permision granted", Toast.LENGTH_SHORT).show();
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

}

