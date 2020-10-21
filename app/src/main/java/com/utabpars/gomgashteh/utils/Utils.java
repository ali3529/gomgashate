package com.utabpars.gomgashteh.utils;

import android.content.Context;
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

}


