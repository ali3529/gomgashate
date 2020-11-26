package com.utabpars.gomgashteh.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

public interface PassDataCallBack {
    public void passUri(Uri uri, MultipartBody.Part partList);

}
