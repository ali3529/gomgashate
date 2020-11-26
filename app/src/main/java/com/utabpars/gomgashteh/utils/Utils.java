package com.utabpars.gomgashteh.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static final int ReadExternalRequestCode = 200;

    public static void toast(Context context,String massage){
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show();
    }

    public static void showimage(ImageView imageView, String link, Drawable drawable){
        Picasso.get().load(link).placeholder(drawable).into(imageView);
    }

    public static String versionCode(Activity activity) {
        int versionNumber=0;
        try {
            PackageInfo info=activity.getPackageManager().getPackageInfo(activity.getPackageName(),0);
            versionNumber=info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.valueOf(versionNumber);
    }


    public static boolean checkPermission(Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) { // ابتدا کنترل می کند تا اندروید بالاتر M باشد
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { // اگر دسترسی خواندن مموری را نداشته باشد دستورات داخل شرط اجرا می شود.
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ReadExternalRequestCode);

                return false;// دسترسی تایید نشده باشد جواب منفی داده می شود.
            } else {
                return true;// دسترسی تایید شده جواب مثبت می شود.
            }
        } else {
            return true;// دسترسی تایید شده جواب مثبت می شود.
        }
    }



    public static byte[] getByts(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff=new ByteArrayOutputStream();

        int buffSize=1024;
        byte[] buff=new byte[buffSize];

        int len=0;
        while ((len=is.read(buff)) != -1 ){
            byteBuff.write(buff,0,len);
        }
        return byteBuff.toByteArray();
    }

     public static String getFileExtantion(Context context,Uri uri){
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}


