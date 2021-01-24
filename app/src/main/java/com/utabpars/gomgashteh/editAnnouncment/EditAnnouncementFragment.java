package com.utabpars.gomgashteh.editAnnouncment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.adaptor.AddImageAnnouncmentAdaptor;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentEditAnnouncementBinding;
import com.utabpars.gomgashteh.fragment.BottomSheetChooseImage;
import com.utabpars.gomgashteh.interfaces.PassDataCallBack;
import com.utabpars.gomgashteh.model.DetailModel;
import com.utabpars.gomgashteh.model.SaveAnnouncementModel;
import com.utabpars.gomgashteh.utils.Utils;

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


public class EditAnnouncementFragment extends Fragment {
    FragmentEditAnnouncementBinding binding;
    EditAnnouncementViewModel viewModel;
    int id;
    boolean load=true;
    RecyclerView oldRecyclerView,recyclerView;
    ImageEditAdaptor oldAdaptor;
    SharedPreferences sharedPreferences,user_status;
    SharedPreferences other_city;
    SharedPreferences.Editor edit;


    String type;
    BottomSheetChooseImage bottomSheetChooseImage;
    AddImageAnnouncmentAdaptor adaptor;
    List<Uri> uriList=new ArrayList<>();
    List<MultipartBody.Part> partLists=new ArrayList<>();
    List<String> old_pic=new ArrayList<>();
     List<String> othercity=new ArrayList<>();
     static String city_save;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_announcement,container,false);
        viewModel=new ViewModelProvider(this).get(EditAnnouncementViewModel.class);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        //for old pic
        oldRecyclerView =binding.oldReyclerview;
        recyclerView=binding.imgRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        oldRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        sharedPreferences = getActivity().getSharedPreferences("editcity", Context.MODE_PRIVATE);
        other_city = getActivity().getSharedPreferences("other_city_edit", Context.MODE_PRIVATE);
        user_status=getActivity().getSharedPreferences("user_login",Context.MODE_PRIVATE);
        edit=other_city.edit();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id=getArguments().getInt("id");
       // id=519;
        binding.setFrag(this);
        Log.d("asfcascc", "onViewCreated: "+id);
        binding.load.setVisibility(View.VISIBLE);
        viewModel.getEditDetail(id);

        viewModel.dataMutableLiveData.observe(getViewLifecycleOwner(), new Observer<DetailModel.Data>() {
            @Override
            public void onChanged(DetailModel.Data data) {


                if (load){
                    loadData(data);
                    Log.d("drdgfdbfdb", "onChanged: inside "+load);
                }
                load=false;
                Log.d("drdgfdbfdb", "onChanged: outside "+load);

                binding.load.setVisibility(View.GONE);

            }
        });

//        if (load){
//
//        }else {
//            try {
//                for (int i = 0; i < othercity.size(); i++) {
//                    binding.othercity.append(data.getOtherCity().get(i)+" ");
//                }
//            }catch (Exception e){
//
//            }
//        }



        binding.setcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("navigate","city_edit");
                Navigation.findNavController(view).navigate(R.id.action_editAnnouncementFragment_to_fragmentCity,bundle);
            }
        });

        binding.setOthercity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("navigate","otherCityEdit");
                Navigation.findNavController(view).navigate(R.id.action_editAnnouncementFragment_to_fragmentCity,bundle);
            }
        });

       // Log.d("sdfsdfdsfdsf", "onViewCreated: "+editData.getData().getTitle());

        //set other city
        if (getSHaredList()==null){
//            binding.othercity.setText("انتخاب کنید");
            for (int i = 0; i < othercity.size(); i++) {
                binding.othercity.append(othercity.get(i)+" ");
            }
        }else {
            SharedPreferences otherCityName=getActivity().getSharedPreferences("other_city_edit",Context.MODE_PRIVATE);
            binding.othercity.setText(otherCityName.getString("otherCity_name","00"));
        }


        try {
            adaptor.notifyDataSetChanged();
            recyclerView.setAdapter(adaptor);
            oldRecyclerView.setAdapter(oldAdaptor);

        }catch (Exception r){

        }

        if (sharedPreferences.getString("city_name","0").equals("0")){
            binding.fgfd.setText(city_save);
        }else {
            binding.fgfd.setText(sharedPreferences.getString("city_name","")+", "+sharedPreferences.getString("province_name",""));

        }


        binding.saveAnnounce.setOnClickListener(o ->{
            ApiInterface apiInterface= ApiClient.getApiClient();
            CompositeDisposable compositeDisposable=new CompositeDisposable();
            compositeDisposable.add(apiInterface.edittAnnouncment(fetchdata(),partLists)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<SaveAnnouncementModel>() {
                @Override
                public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull SaveAnnouncementModel saveAnnouncementModel) {
                    if (saveAnnouncementModel.getResponse().equals("1")){
                        Toast.makeText(getContext(), ""+saveAnnouncementModel.getMasg(), Toast.LENGTH_SHORT).show();
                        Bundle bundle=new Bundle();
                        bundle.putInt("id",id);
                        bundle.putString("edit","edit");
                        Navigation.findNavController(view).navigateUp();
                    }


                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    Log.d("dvdsvdsvdsv", "onError: "+e.toString());
                }
            }));

        });


        binding.layoutAddImage.setOnClickListener(o ->{
            if (Utils.checkPermissionStorage(getContext()) && Utils.checkPermissionStorageWrite(getContext())) {
                bottomSheetChooseImage=new BottomSheetChooseImage();
                bottomSheetChooseImage.show(getActivity().getSupportFragmentManager(),"");
                bottomSheetChooseImage.passData(passDataCallBack);

            }
        });



    }

    private void loadData(DetailModel.Data data) {
        binding.setData(data);
        binding.setCateg.setText(data.getCollection());
        binding.title.setText(data.getTitle());
        binding.description.setText(data.getDetail());
        binding.fgfd.setText(data.getCity());
        city_save=data.getCity();
        binding.surpriseText.setText(data.getReward());
        oldAdaptor =new ImageEditAdaptor(data.getPictures(),DeleteImage);
        oldRecyclerView.setAdapter(oldAdaptor);

        try {
            othercity.addAll(data.getOtherCity());
            for (int i = 0; i < othercity.size(); i++) {
                binding.othercity.append(othercity.get(i)+" ");
            }
        }catch (Exception e){

        }
        if (data.getType().equals("پیدا شده")){
            binding.find.toggle();
            type="1";
            binding.suprise.setVisibility(View.GONE);
        }else {
            binding.lost.toggle();
            type="2";
            binding.suprise.setVisibility(View.VISIBLE);
        }
        if (data.getPictures().get(0).equals("https://gomgashteh.com/uploads/announce/camera-icon.jpg")){
            Toast.makeText(getContext(), "not [ivture", Toast.LENGTH_SHORT).show();
            data.getPictures().remove(0);
        }
        old_pic=data.getPictures();
        Log.d("sfsefesf", "fetchdata: "+data.getPictures().toString());
        Log.d("sfsefesf", "fetchdata: "+old_pic.toString());


    }

    public List<String> getSHaredList() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("other_city_edit", Context.MODE_PRIVATE);

        String s = sharedPreferences.getString("otherCityList", null);
        Type type = new TypeToken<List<String>>() {

        }.getType();
        List<String> j = gson.fromJson(s, type);



        return j;


    }
    public void onClickRadio(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.find:
                if (checked){
                    type="1";
                    Log.d("jhvhjvj", "onClickRadio: "+type);
                    binding.suprise.setVisibility(View.GONE);
                }

                break;
            case R.id.lost:
                if (checked) {
                    type = "2";
                    Log.d("jhvhjvj", "onClickRadio: " + type);
                    binding.suprise.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public HashMap<String, RequestBody> fetchdata(){

        HashMap<String,RequestBody> addAnnouncement=new HashMap<>();

        RequestBody id_announc=RequestBody.create(MediaType.parse("id"),String.valueOf(id));
        RequestBody title=RequestBody.create(MediaType.parse("title"),binding.title.getText().toString());
        RequestBody utype=RequestBody.create(MediaType.parse("type"),type);
        RequestBody province_id=RequestBody.create(MediaType.parse("province_id"),sharedPreferences.getString("province_id",""));
        RequestBody city_id=RequestBody.create(MediaType.parse("city_id"),sharedPreferences.getString("city_id",""));
        RequestBody detail=RequestBody.create(MediaType.parse("detail"),binding.description.getText().toString());
        RequestBody reward=RequestBody.create(MediaType.parse("reward"),binding.surpriseText.getText().toString());
        RequestBody announcer_id=RequestBody.create(MediaType.parse("announcer_id"),user_status.getString("user_id",""));
        RequestBody picture_old=RequestBody.create(MediaType.parse("old_picture"),old_pic.toString());


        RequestBody other_city;
        if (getSHaredList()==null){
            other_city=RequestBody.create(MediaType.parse("other_city"),"");
        }else {

            other_city=RequestBody.create(MediaType.parse("other_city"),getSHaredList().toString());
        }

        Log.d("dfdsfdsfdsfdsf", "title: "+binding.title.getText().toString());
        Log.d("dfdsfdsfdsfdsf", "type: "+type);
        Log.d("dfdsfdsfdsfdsf", "province_id: "+sharedPreferences.getString("province_id",""));
        Log.d("dfdsfdsfdsfdsf", "city_id: "+sharedPreferences.getString("city_id",""));
        Log.d("dfdsfdsfdsfdsf", "description: "+binding.description.getText().toString());
        Log.d("dfdsfdsfdsfdsf", "surpriseText: "+binding.surpriseText.getText().toString());
        Log.d("dfdsfdsfdsfdsf", "fetchdata: "+other_city);
        Log.d("dfdsfdsfdsfdsf", "fetchdata: "+binding.surpriseText.getText());



        addAnnouncement.put("title",title);
        addAnnouncement.put("type",utype);
        addAnnouncement.put("province_id",province_id);
        addAnnouncement.put("city_id",city_id);
        addAnnouncement.put("other_city",other_city);
        addAnnouncement.put("detail",detail);
        addAnnouncement.put("announcer_id",announcer_id);
        addAnnouncement.put("reward",reward);
        addAnnouncement.put("id",id_announc);
        addAnnouncement.put("old_picture",picture_old);





        return addAnnouncement;
    }

PassDataCallBack passDataCallBack=new PassDataCallBack() {
    @Override
    public void passUri(Uri uri, MultipartBody.Part partList) {
        uriList.add(uri);
        partLists.add(partList);
        adaptor=new AddImageAnnouncmentAdaptor(uriList,onDeleteImages);
        recyclerView.setAdapter(adaptor);
        bottomSheetChooseImage.dismiss();
        Log.d("dssdvsdvv", "deleteImage: "+uriList.size());
        Log.d("dssdvsdvv", "deleteImage: "+partLists.size());
    }
};

    onDeleteImage DeleteImage=new onDeleteImage() {
        @Override
        public void deleteImage(List<String> list) {
            Log.d("fbcgnfng", "deleteImage: "+list.size());
        oldAdaptor.notifyDataSetChanged();
            Log.d("sfsefesf", "deleteImage: "+list.toString());
        old_pic=list;
            Log.d("sfsefesf", "deleteImage: "+old_pic.toString());

        }
    };
    AddImageAnnouncmentAdaptor.onDeleteImages onDeleteImages=new AddImageAnnouncmentAdaptor.onDeleteImages() {
        @Override
        public void deleteImage(List<Uri> list, int position) {
            uriList.remove(position);
            partLists.remove(position);
            adaptor.notifyDataSetChanged();
            Log.d("dssdvsdvv", "deleteImage: "+uriList.size());
            Log.d("dssdvsdvv", "deleteImage: "+partLists.size());
        }
    };

    @Override
    public void onPause() {
        super.onPause();

        Log.d("dsfdsfdsf", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("dsfdsfdsf" ,"onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("dsfdsfdsf", "onDestroy: ");
                edit.clear();
        edit.apply();
    }
}