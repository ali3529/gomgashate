package com.utabpars.gomgashteh.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.database.categoryDatabase.Attrebiute;
import com.utabpars.gomgashteh.database.categoryDatabase.Category;
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryDataBase;
import com.utabpars.gomgashteh.database.categoryDatabase.CategoryFromServerViewModel;
import com.utabpars.gomgashteh.database.categoryDatabase.Collection;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset;
import com.utabpars.gomgashteh.database.categoryDatabase.Subset2;
import com.utabpars.gomgashteh.database.citydatabase.City;
import com.utabpars.gomgashteh.database.citydatabase.CityDatabase;
import com.utabpars.gomgashteh.database.citydatabase.CityFromServerViewModel;
import com.utabpars.gomgashteh.database.citydatabase.Province;
import com.utabpars.gomgashteh.databinding.FragmentSplashBinding;
import com.utabpars.gomgashteh.utils.Utils;
import com.utabpars.gomgashteh.viewmodel.ChatNotificationViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FragmentSplash extends Fragment {
    FragmentSplashBinding binding;
    CategoryDataBase db ;
    CityDatabase dbc;
    CategoryFromServerViewModel viewModel;
    ChatNotificationViewModel chatNotificationViewModel;
    SharedPreferences sharedPreferences,save_category_to_database;
    boolean is_save=false;
    String user_id;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        chatNotificationViewModel=new ViewModelProvider(this).get(ChatNotificationViewModel.class);
        sharedPreferences=getActivity().getSharedPreferences("user_login", Context.MODE_PRIVATE);
        viewModel=new ViewModelProvider(this).get(CategoryFromServerViewModel.class);
        db= CategoryDataBase.getInstance(getContext());
        dbc= CityDatabase.getInstance(getContext());
        save_category_to_database=getActivity().getSharedPreferences("save_category",Context.MODE_PRIVATE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_splash,container,false);
        binding.setSplash(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         user_id = sharedPreferences.getString("user_id", "0");
        boolean save_category=save_category_to_database.getBoolean("category_status",true);
        chatNotificationViewModel.getChatNotification(user_id);
        chatNotificationViewModel.chatCounterMutableLiveData.observe(getViewLifecycleOwner(), notification -> {

            if (notification == 0) {

            } else {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomnav);
                BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.chat);
                badge.isVisible();
                badge.setNumber(notification);
            }

        });

        chatNotificationViewModel.chatNotificationModelMutableLiveData.observe(getViewLifecycleOwner(), user_status -> {
            if (user_status.equals("0")) {

            } else if (user_status.equals("1")) {

            } else {

            }
        });

        chatNotificationViewModel.category_update.observe(getViewLifecycleOwner(),is_update->{
            binding.tryAgain.setVisibility(View.GONE);
            if (save_category){
                getCategoryForDb();
                SharedPreferences.Editor editor = save_category_to_database.edit();
                editor.putBoolean("category_status",false);
                editor.putString("db_version",is_update);
                editor.apply();
                Log.d("sdvsdvsdv", "onViewCreated: "+"   first");
            }else {
                Log.d("sdvsdvsdv", "onViewCreated: "+"   second");
                if (!is_update.equals(save_category_to_database.getString("db_version","0"))){
                    db.clearAllTables();
                    dbc.clearAllTables();
                    getCategoryForDb();
                }else {
                    navigateTo();
                }
            }

        });


        chatNotificationViewModel.error.observe(getViewLifecycleOwner(),error->{
            binding.tryAgain.setVisibility(View.VISIBLE);
        });



        TextView textView=view.findViewById(R.id.app_versionm);
        textView.setText("ویرایش "+ Utils.versionName(getActivity()));

//        CategoryFromServerViewModel viewModel;
//        viewModel=new ViewModelProvider(ViewModelStore::new).get(CategoryFromServerViewModel.class);
//        String f=viewModel.databaseEntityModelMutableLiveData.getValue().getCategory().get(0).getId();
//        Log.d(TAG, "onViewCreated: "+f);
    }


    public void getCategoryForDb(){
        viewModel. getCategory();
        getCityFromDb();
        viewModel.databaseEntityModelMutableLiveData.observe(getViewLifecycleOwner(),t->{
            SharedPreferences.Editor editor = save_category_to_database.edit();
            editor.putString("db_version",t.getVersion_update());
            editor.apply();

            Log.d("sdvsdvv", "getCategoryForDb out for  ---: ");
            for (Category category:t.getCategory()) {
                db.categoryDao().setCategoryToDB(category);
                Log.d("sdvsdvv", "getCategoryForDb: ");
            }
            for (Collection collection:t.getCollection()) {
                db.categoryDao().setCollectionToDB(collection);
            }
            for (Subset subset:t.getSubset()) {
                db.categoryDao().setSubsetToDB(subset);
            }
            for (Subset2 subset2:t.getSubset2()) {
                db.categoryDao().setSubset2ToDB(subset2);
            }
            for (Attrebiute attrebiute:t.getAttribute()) {
                db.categoryDao().setAttrebiuteToDB(attrebiute);
            }
            navigateTo();
        });
          Log.d("sdvsdvv", "getCategoryForDb: "+is_save);

    }

    public void navigateTo(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.fragmentSplash, true)
                        .build();
                try {
                    Navigation.findNavController(getView()).navigate(R.id.action_fragmentSplash_to_fragmentAnnouncement, null, navOptions);

                }catch (Exception e){

                }
            }
        }, 2000);
    }


    public void tryAgain(){
        chatNotificationViewModel.getChatNotification(user_id);

    }

   private void getCityFromDb(){

       CityFromServerViewModel viewModel=new ViewModelProvider(ViewModelStore::new).get(CityFromServerViewModel.class);
       viewModel.cityDatabaseModelMutableLiveData.observe(getViewLifecycleOwner(),result->{
           for (Province province:result.getProvinces()) {
             dbc.cityDao().insertProvinceToDB(province);

           }

           for (City city:result.getCities()) {
               dbc.cityDao().insertCityToDB(city);
               //Log.d("sdvdsvdsv", "getCityFromDb: "+f);

           }
       });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}