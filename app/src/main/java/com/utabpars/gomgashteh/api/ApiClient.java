package com.utabpars.gomgashteh.api;

import java.io.IOException;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit=null;
    private static String BASE_URL="http://chidashop.ir/api/";

    private static Retrofit getInstance(){
        if (retrofit==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request orginial=chain.request();
                    Request request=orginial.newBuilder()
                            .header("X-Authorization","Ad5cVc4gPglCT9AGHzmOoPqXgY0lCMh9duQQt6ePSZ1OoWrSX7IMOEFjiGu8ff3a")
                            .method(orginial.method(),orginial.body())
                            .build();
                    return chain.proceed(request);
                }
            });
            OkHttpClient client=httpClient.build();
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getApiClient(){
        getInstance();
        return retrofit.create(ApiInterface.class);
    }

}
