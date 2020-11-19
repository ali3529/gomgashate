package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import com.google.gson.JsonObject;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentAddAnnouncementBinding;
import com.utabpars.gomgashteh.model.SaveAnnouncementModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentAddAnnouncement extends Fragment {
    FragmentAddAnnouncementBinding binding;
    EditText edTitle, edDescription;
    Button save_announcement;
    SharedPreferences shPref;
    RadioGroup radioGroup;
    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_announcement,container,false);
        shPref = getActivity().getSharedPreferences("add_announce", Context.MODE_PRIVATE);
        initViews();
        binding.setFrag(this);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                Navigation.findNavController(view).navigate(R.id.action_add_to_fragmentCity);
            }
        });

        //set category
            String title=shPref.getString("title_list","انتخاب کنید");
            String title_collection=shPref.getString("title_categ","");
            String id=shPref.getString("id_categ",null);
            String id_list=shPref.getString("id_list",null);

            binding.setCategory(title+", "+title_collection);



        //set city

        binding.setCity(shPref.getString("city_name","انتخاب کنید")+", "+shPref.getString("province_name",""));


        save_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edTitle.getText().toString().length()!=0){
                    if (edDescription.getText().toString().length()!=0){
                        if (type!=null){
                            sendAnnouncment(fetchdata());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Navigation.findNavController(view).navigate(R.id.action_add_to_announcement);
                                    SharedPreferences.Editor editor=shPref.edit();
                                    editor.clear();
                                    editor.apply();
                                }
                            },2000);


                        }else Toast.makeText(getContext(), "لطفا نوع آگهی را مشخص کنید", Toast.LENGTH_SHORT).show();
                    }else    Toast.makeText(getContext(), "لطفا توضیحات آگهی را وارد کنید", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "لطفا عنوان آگهی را وارد کنید", Toast.LENGTH_SHORT).show();
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
    }

private void sendAnnouncment(JsonObject jsonObject){
    ApiInterface apiInterface= ApiClient.getApiClient();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    compositeDisposable.add(apiInterface.insertAnnouncment(jsonObject)
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeWith(new DisposableSingleObserver<SaveAnnouncementModel>() {
        @Override
        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull SaveAnnouncementModel saveAnnouncementModel) {
            if (saveAnnouncementModel.getResponse().equals("1")){
                Toast.makeText(getContext(), saveAnnouncementModel.getMasg(), Toast.LENGTH_SHORT).show();
            }else if (saveAnnouncementModel.getResponse().equals("0")) {
                Toast.makeText(getContext(), saveAnnouncementModel.getMasg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

        }
    }));
}

public JsonObject fetchdata(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("title",edTitle.getText().toString());
        jsonObject.addProperty("type",type);
        jsonObject.addProperty("category_id",shPref.getString("id_categ",null));
        jsonObject.addProperty("collection_id",shPref.getString("id_list",null));
        jsonObject.addProperty("province_id",shPref.getString("province_id",null));
        jsonObject.addProperty("city_id",shPref.getString("city_id",null));
        jsonObject.addProperty("detail",edDescription.getText().toString());
        jsonObject.addProperty("announcer_id","1");

    Log.e("dgdfg", "fetchdata: "+jsonObject );

        return jsonObject;
}
}

