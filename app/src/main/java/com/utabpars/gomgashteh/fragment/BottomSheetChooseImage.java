package com.utabpars.gomgashteh.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.utabpars.gomgashteh.R;

import com.utabpars.gomgashteh.databinding.FragmentBottomsheetAddimageBinding;
import com.utabpars.gomgashteh.databinding.FragmentBottomsheetAddimageBindingImpl;
import com.utabpars.gomgashteh.interfaces.PassDataCallBack;
import com.utabpars.gomgashteh.utils.Utils;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


import static android.app.Activity.RESULT_OK;
import static com.utabpars.gomgashteh.utils.Utils.ReadExternalRequestCode;
import static com.utabpars.gomgashteh.utils.Utils.getByts;
import static com.utabpars.gomgashteh.utils.Utils.getFileExtantion;


public class BottomSheetChooseImage extends BottomSheetDialogFragment {
    PassDataCallBack passDataCallBack;

    FragmentBottomsheetAddimageBinding binding;
    Intent intent;
     Uri uriList;
     MultipartBody.Part partList;
    RelativeLayout sheetLayout;
    BottomSheetBehavior sheetBehavior;
    TextView camera,gallery;


    public void passData(PassDataCallBack passDataCallBack){
        this.passDataCallBack=passDataCallBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_bottomsheet_addimage,container,false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        sheetLayout=binding.sheeet;
        camera=binding.camera;
        gallery=binding.gallery;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setTest(this);

        sheetBehavior=BottomSheetBehavior.from(sheetLayout);

        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.checkPermissionCamera(getContext())){
                    intent=new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent,200);
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("image/*");
                startActivityForResult(intent,100);

                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==100){
            if (resultCode==RESULT_OK){

                try {
                    InputStream inputStream=getActivity().getContentResolver().openInputStream(data.getData());
                    String tyoe=getFileExtantion(getContext(),data.getData());
                    sendUploadRequest(getByts(inputStream),tyoe);



                    passDataCallBack.passUri(data.getData(),partList);




                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        else if (requestCode==200) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "sdg", Toast.LENGTH_SHORT).show();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                String f = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null).toString();
                Uri uri = Uri.parse(f.toString());

                passDataCallBack.passUri(uri,partList);
                Log.d("tfhtrfhd", "onActivityResult: "+uri.toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ReadExternalRequestCode) { // با کلید مربوط به خواندن مموری نتیجه را می خوانیم
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // اگر از درخواست دسترسی جواب مثبت برگشت دستورات شرط اجرا می شود.
                intent=new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,200);

                Toast.makeText(getContext(), "permision granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "دسترسی داده نشد!", Toast.LENGTH_LONG).show(); // در صورتی که جواب منفی گرفتیم پیام دسترسی داده نشد را نمایش می دهیم.
            }
        }
    }


    private void sendUploadRequest(byte[] bytes,String type) {

        RequestBody requestFile=RequestBody.create(MediaType.parse("image/*") , bytes);
        MultipartBody.Part file=MultipartBody.Part.createFormData("file[]","myImage."+type,requestFile);

        partList=file;


    }




}