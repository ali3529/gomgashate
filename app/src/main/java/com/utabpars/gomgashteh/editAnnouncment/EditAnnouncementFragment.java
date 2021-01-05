package com.utabpars.gomgashteh.editAnnouncment;

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
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentEditAnnouncementBinding;
import com.utabpars.gomgashteh.model.DetailModel;
import com.utabpars.gomgashteh.model.SaveAnnouncementModel;
import com.utabpars.gomgashteh.viewmodel.DetailViewModel;

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
    DetailViewModel viewModel;
    int id;
    boolean load=true;
    RecyclerView recyclerView;
    ImageEditAdaptor adaptor;
    SharedPreferences sharedPreferences,user_status;
    String type;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_edit_announcement,container,false);
        viewModel=new ViewModelProvider(getActivity()).get(DetailViewModel.class);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        recyclerView=binding.imgRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        sharedPreferences = getActivity().getSharedPreferences("editcity", Context.MODE_PRIVATE);
        user_status=getActivity().getSharedPreferences("user_login",Context.MODE_PRIVATE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id=getArguments().getInt("id");
      //  id=457;
        binding.setFrag(this);
        Log.d("asfcascc", "onViewCreated: "+id);
        binding.load.setVisibility(View.VISIBLE);
        viewModel.getDetail(id,"0");

        viewModel.getMutableDetail().observe(getViewLifecycleOwner(), new Observer<DetailModel.Data>() {
            @Override
            public void onChanged(DetailModel.Data data) {
                Log.d("safsafsaf", "onChanged: "+data.getId());

                if (load){
                    loadData(data);
                    Log.d("drdgfdbfdb", "onChanged: inside "+load);
                }
                load=false;
                Log.d("drdgfdbfdb", "onChanged: outside "+load);

                binding.load.setVisibility(View.GONE);

            }
        });



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
        }else {
            binding.othercity.setText(getSHaredList().size()+"شهر");
        }


        //Log.d("sdfdsfdsf", "onViewCreated: edit"+city);
        //set city
     //   binding.setCity(sharedPreferences.getString("city_name",binding.fgfd.getText().toString()));
        binding.fgfd.setText(sharedPreferences.getString("city_name","")+", "+sharedPreferences.getString("province_name",""));
        //    binding.fgfd.setText(sharedPreferences.getString("city_name",""));

        binding.saveAnnounce.setOnClickListener(o ->{
            List<MultipartBody.Part> file=new ArrayList<>();
            ApiInterface apiInterface= ApiClient.getApiClient();
            CompositeDisposable compositeDisposable=new CompositeDisposable();
            compositeDisposable.add(apiInterface.edittAnnouncment(fetchdata(),file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<SaveAnnouncementModel>() {
                @Override
                public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull SaveAnnouncementModel saveAnnouncementModel) {
                    Toast.makeText(getContext(), ""+saveAnnouncementModel.getMasg(), Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putInt("id",id);
                    bundle.putString("edit","edit");
                    Navigation.findNavController(view).navigateUp();

                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    Log.d("dvdsvdsvdsv", "onError: "+e.toString());
                }
            }));

        });

    }

    private void loadData(DetailModel.Data data) {
        binding.setData(data);
        binding.title.setText(data.getTitle());
        binding.description.setText(data.getTitle());
        adaptor=new ImageEditAdaptor(data.getPictures());
        recyclerView.setAdapter(adaptor);
        try {
            for (int i = 0; i < data.getOtherCity().size(); i++) {
                binding.othercity.append(data.getOtherCity().get(i)+" ");
            }
        }catch (Exception e){

        }
        if (data.getType().equals("پیدا شده")){
            binding.find.toggle();
            type="1";
        }else {
            binding.lost.toggle();
            type="2";
        }
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



        addAnnouncement.put("title",title);
        addAnnouncement.put("type",utype);
        addAnnouncement.put("province_id",province_id);
        addAnnouncement.put("city_id",city_id);
        addAnnouncement.put("other_city",other_city);
        addAnnouncement.put("detail",detail);
        addAnnouncement.put("announcer_id",announcer_id);
        addAnnouncement.put("reward",reward);
        addAnnouncement.put("id",id_announc);





        return addAnnouncement;
    }
}