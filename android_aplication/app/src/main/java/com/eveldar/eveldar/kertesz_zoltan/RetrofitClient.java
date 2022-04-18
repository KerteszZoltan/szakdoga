package com.eveldar.eveldar.kertesz_zoltan;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.*;

public class RetrofitClient {
    private static String BASE_URL = "192.168.185.106";
    private static String EXTENDED_BASE_URL="http://"+BASE_URL+":80/eveldar/public/api/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;

    private RetrofitClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(EXTENDED_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }

}
