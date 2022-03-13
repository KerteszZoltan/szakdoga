package com.eveldar.eveldar.kertesz_zoltan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("update_profil")
    @Headers("Autho")
    Call<LoginResponse> updateProfilWithoutPassword(
            @Field("id") Integer id,
            @Field("name") String name,
            @Field("email") String email
    );
}
