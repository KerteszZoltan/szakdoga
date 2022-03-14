package com.eveldar.eveldar.kertesz_zoltan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<LoginResponse> updateProfilWithoutPassword(
            @Header("Authorization") String authHeader,
            @Field("name") String name,
            @Field("email") String email
    );

    @GET("profile")
    Call<ProfileResponse> profilData(
            @Header("Authorization") String authHeader
    );
    
    @POST("logout")
    Call<LogoutResponse> logout(
            @Header("Authorization") String authHeader
    );
}
