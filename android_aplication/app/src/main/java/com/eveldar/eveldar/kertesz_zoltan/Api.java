package com.eveldar.eveldar.kertesz_zoltan;

import com.eveldar.eveldar.kertesz_zoltan.Responses.CheckTokenResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.FetchEventsResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LoginResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.LogoutResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.ProfileResponse;
import com.eveldar.eveldar.kertesz_zoltan.Responses.ResponseEvent;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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

    @FormUrlEncoded
    @POST("checkToken")
    Call<CheckTokenResponse> checkToken(
            @Header("Authorization") String authHeader,
            @Field("id") Integer id
    );

    @GET("active")
    Call<FetchEventsResponse> activeEvents(
            @Header("Authorization") String authHeader
    );
    @GET("complete")
    Call<FetchEventsResponse> completeEvents(
            @Header("Authorization") String authHeader
    );
    @GET("expired")
    Call<FetchEventsResponse> expiredEvent(
            @Header("Authorization") String authHeader
    );

    @FormUrlEncoded
    @POST("specified")
    Call<ResponseEvent> specifiedEvent(
            @Header("Authorization") String authHeader,
            @Field("event_id") Integer eventId
    );

    @FormUrlEncoded
    @POST("update")
    Call<ResponseEvent> updateEvent(
            @Header("Authorization") String authHeader,
            @Field("event_id") Integer eventId,
            @Field("topic") String topic,
            @Field("description") String description,
            @Field("start") String start,
            @Field("end") String end,
            @Field("complete") Integer complete

    );

    @FormUrlEncoded
    @POST("delete")
    Call<ResponseEvent> deleteEvent(
            @Header("Authorization") String authHeader,
            @Field("event_id") String eventId
    );
}
