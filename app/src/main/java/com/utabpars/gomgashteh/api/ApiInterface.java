package com.utabpars.gomgashteh.api;

import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.AppVersionModel;
import com.utabpars.gomgashteh.model.DetailModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers(
            "X-Authorization: Ad5cVc4gPglCT9AGHzmOoPqXgY0lCMh9duQQt6ePSZ1OoWrSX7IMOEFjiGu8ff3a"
    )

    @POST("getAnnouncement")
    Single<AnoncmentModel> getAnnouncement(@Query("page") int page);

    @POST("detailAnnouncement")
    @FormUrlEncoded
    Single<DetailModel> getDetail(@Field("id") int id);

    @POST("splash")
    Single<AppVersionModel> update();

}
