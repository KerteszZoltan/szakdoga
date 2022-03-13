package com.eveldar.eveldar.kertesz_zoltan;


import retrofit2.Retrofit;
import retrofit2.converter.gson.*;

public class RetrofitClient {
    private static String BASE_URL="http://192.168.0.107:80/eveldar/public/api/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;


    private RetrofitClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    Api getApi(){
        return retrofit.create(Api.class);
    }

}
