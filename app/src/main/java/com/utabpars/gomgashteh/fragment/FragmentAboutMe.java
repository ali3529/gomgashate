package com.utabpars.gomgashteh.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utabpars.gomgashteh.R;
import com.utabpars.gomgashteh.api.ApiClient;
import com.utabpars.gomgashteh.api.ApiInterface;
import com.utabpars.gomgashteh.databinding.FragmentAboutMeBinding;
import com.utabpars.gomgashteh.model.AboutModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentAboutMe extends Fragment {
    FragmentAboutMeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bottomnav).setVisibility(View.GONE);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_about_me,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ApiInterface apiInterface= ApiClient.getApiClient();
//        CompositeDisposable compositeDisposable=new CompositeDisposable();
//        compositeDisposable.add(apiInterface.getAboutMe()
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribeWith(new DisposableSingleObserver<AboutModel>() {
//            @Override
//            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AboutModel aboutModel) {
//                binding.setAboutme(aboutModel.getAbout_me());
//            }
//
//            @Override
//            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                Log.d("dsvdsv", "onError: "+e.toString());
//            }
//        }));
        String s="ای گمگشته دلها به جهان بازآی، ای منتقم خون خدا بازآی ، با این همه جرم و گنه باز منتظریم ، چون (لاتقنطوا من رحمه الله) بازآی\n" +
                "\n" +
                "شرکت همیاران گمگشته دلها 780 در سال 1399 با شماره ثبت 53871 با عنایات الطاف الهی و استمداد از قطب عالم امکان حضرت ولی عصر (عج) برای رفاه حال هموطنان عزیز ایران اسلامی جهت پیدا کردن گمشده های خودویارساندن مطالب پیداشده به صاحبان اصلی خود بوجود آمده که تاثیر به سزائی در هزینه، وقت و جلوگیری از اسراف در امور مردم و دولت عزیز می باشد که نیازمند همکاری شما هموطنان عزیز در این امر خیرخواهانه هستیم.        \n" +
                "\n" +
                "  الحمدلله به لطف خدا وتوجه خاصه امام عصر(عج) درمیلادسرارنورکوثرآل طه حضرت صدیقه اطهر فاطمه الزهرا(سلام الله علیها)به تاریخ14/11/99شمسی بیست جمادی الثانی1442 این طرح خیرخواهانه شروع به فعالیت خودنمود"
                +"\n"+"1399 © تمامی حقوق برای شرکت همیاران گمگشته دلها محفوظ می باشد";
        binding.setAboutme(s);
    }
}