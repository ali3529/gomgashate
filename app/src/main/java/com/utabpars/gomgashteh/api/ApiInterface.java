package com.utabpars.gomgashteh.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.utabpars.gomgashteh.chat.ChatStatusModel;
import com.utabpars.gomgashteh.chat.StatusModel;
import com.utabpars.gomgashteh.chat.TicketResponseModel;
import com.utabpars.gomgashteh.managerAnnouncement.ManageModel;
import com.utabpars.gomgashteh.markannouncment.MarkModel;
import com.utabpars.gomgashteh.model.AnoncmentModel;
import com.utabpars.gomgashteh.model.AppVersionModel;
import com.utabpars.gomgashteh.model.BlockModel;
import com.utabpars.gomgashteh.model.CategoryModel;
import com.utabpars.gomgashteh.model.ChatsModel;
import com.utabpars.gomgashteh.model.DetailModel;
import com.utabpars.gomgashteh.model.RegisterModel;
import com.utabpars.gomgashteh.model.RmModel;
import com.utabpars.gomgashteh.model.SaveAnnouncementModel;
import com.utabpars.gomgashteh.model.AddImageModel;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers(
            "X-Authorization: Ad5cVc4gPglCT9AGHzmOoPqXgY0lCMh9duQQt6ePSZ1OoWrSX7IMOEFjiGu8ff3a"
    )

    @POST("getAnnouncement")
    Single<AnoncmentModel> getAnnouncement(@Query("page") int page);

    @POST("detailAnnouncement")
    @FormUrlEncoded
    Single<DetailModel> getDetail(@Field("id") int id,@Field("user_id") String user_id);

    @POST("splash")
    Single<AppVersionModel> update();

    @POST("categories")
    Single<CategoryModel> getcategories();


    @POST("collections")
    @FormUrlEncoded
    Single<CategoryModel> getcallection(@Field("category_id") String category_id,@Field("type") String type);

    @POST("collectionFilter")
    @FormUrlEncoded
    Single<AnoncmentModel> getFilterAnnouncment(@Field("collection_id") String collection_id);

    @POST("keySearch")
    @FormUrlEncoded
    Single<AnoncmentModel> Search(@Field("key") String key);

    @POST("provinces")
    Single<CategoryModel> Provinces();

    @POST("cities")
    @FormUrlEncoded
    Single<CategoryModel> cities(@Field("province") String province_id);


    @POST("insertAnnounce")
    @Multipart
    Single<SaveAnnouncementModel> insertAnnouncment(@PartMap Map<String,RequestBody> requestBody,
                                                    @Part List<MultipartBody.Part> file);

    @POST("userAuthentication")
    @FormUrlEncoded
    Single<RmModel> userAuthentication(@Field("phone_num") String phone_num);

    @POST("validateCode")
    @FormUrlEncoded
    Single<RegisterModel> validateOtp(@Field("phone_num") String phone_num,
                                      @Field("code") String code);


    @POST("userRegistration")
    @FormUrlEncoded
    Single<RegisterModel> registerUser(@Field("phone_num") String phone_num,
                                @Field("code") String code,
                               @Field("first_name")  String name,
                                @Field("last_name")   String lastName);

    @POST("categoriesInHeader")
    Single<RmModel> getTopFilter();

    @POST("categoriesFilterInHeader")
    @FormUrlEncoded
    Single<AnoncmentModel> getTopFilterAnnouncement(@Query("page") int page,
                                                    @Field("id") String id);

    @POST("provinceFilter")
    @FormUrlEncoded
    Single<AnoncmentModel> filterByProvince(@Field("province_id") String province_id);


    @POST("statusUser")
    @FormUrlEncoded
    Single<ChatStatusModel> getChatStatus(@Field("user_id") String user_id,@Field("announcement_id") String announcement_id);

    @POST("insertTicket")
    @Multipart
    Single<StatusModel> sendFirstAnnouncementMassage(@Part List<MultipartBody.Part> parts,
                                                     @PartMap Map<String,RequestBody> requestBody);

    @POST("showTicket")
    @FormUrlEncoded
    Single<TicketResponseModel> getTicketInfo(@Field("ticket_id") String ticket_id,@Field("user_id") String user_id);

    @POST("myTickets")
    @FormUrlEncoded
    Single<ChatsModel> getTickets(@Field("user_id") String user_id);

    @POST("myAnnounce")
    @FormUrlEncoded
    Single<AnoncmentModel> getMyAnnouncment(@Field("user_id") String user_id);


    @POST("blockUser")
    @FormUrlEncoded
    Single<BlockModel> blockUeer(@Field("blocker") String blocker,@Field("blocked") String blocked  );


    @POST("mark")
    @FormUrlEncoded
    Single<MarkModel> markAnnouncement(@Field("user_id") String user_id,@Field("announce_id") String announce_id);


    @POST("myMark")
    @FormUrlEncoded
    Single<AnoncmentModel> mymarkAnnouncment(@Field("user_id") String user_id);


    @POST("tabs")
    @FormUrlEncoded
    Single<ManageModel> getTabs(@Field("announce_id") String announce_id);

    @POST("editAnnounce")
    @Multipart
    Single<SaveAnnouncementModel> edittAnnouncment(@PartMap Map<String,RequestBody> requestBody,
                                                    @Part List<MultipartBody.Part> file);
}

