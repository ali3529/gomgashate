package com.utabpars.gomgashteh.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Utils {

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

}


