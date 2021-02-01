package com.utabpars.gomgashteh.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class Utils {

    public static final int ReadExternalRequestCode = 200;
    public static final int WriteExternalRequestCode = 300;

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


    public static boolean checkPermissionStorage(Context context) {
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

    public static boolean checkPermissionCamera(Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) { // ابتدا کنترل می کند تا اندروید بالاتر M باشد
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) { // اگر دسترسی خواندن مموری را نداشته باشد دستورات داخل شرط اجرا می شود.
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, ReadExternalRequestCode);

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


    public static List<String> getSHaredList(Activity activity){
        Gson gson=new Gson();
        SharedPreferences sharedPreferences=activity.getSharedPreferences("other_city",Context.MODE_PRIVATE);
        String s=sharedPreferences.getString("otherCityList","w");
        Type type=new TypeToken<List<String>>(){

        }.getType();
        List<String>  j=gson.fromJson(s,type);

        Log.d("sfesfsef", "getCategoryId: "+j.get(0));
        return j;
    }


    public static boolean checkPermissionStorageWrite(Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) { // ابتدا کنترل می کند تا اندروید بالاتر M باشد
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { // اگر دسترسی خواندن مموری را نداشته باشد دستورات داخل شرط اجرا می شود.
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WriteExternalRequestCode);

                return false;// دسترسی تایید نشده باشد جواب منفی داده می شود.
            } else {
                return true;// دسترسی تایید شده جواب مثبت می شود.
            }
        } else {
            return true;// دسترسی تایید شده جواب مثبت می شود.
        }
    }

    public static String versionName(Activity activity) {
        String versionNumber="0";
        try {
            PackageInfo info=activity.getPackageManager().getPackageInfo(activity.getPackageName(),0);
            versionNumber=info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionNumber;
    }
}


