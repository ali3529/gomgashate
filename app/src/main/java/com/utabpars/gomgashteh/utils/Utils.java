package com.utabpars.gomgashteh.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void toast(Context context,String massage){
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show();
    }

}


