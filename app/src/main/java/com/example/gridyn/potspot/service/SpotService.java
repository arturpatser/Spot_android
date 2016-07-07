package com.example.gridyn.potspot.service;

import com.example.gridyn.potspot.response.SpotCommentsResponse;
import com.example.gridyn.potspot.response.SpotCreateResponse;
import com.example.gridyn.potspot.response.SpotResponse;
import com.example.gridyn.potspot.response.SpotSearchResponse;
import com.example.gridyn.potspot.response.SpotUpdateResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SpotService {

    @POST("spot/create")
    Call<SpotCreateResponse> createSpot(@Body Map<String, String> data);

    @POST("spot/{token}/update")
    Call<SpotUpdateResponse> updateSpot(@Path("token") String token, Map<String, String> data);

    @POST("spot/search")
    Call<SpotSearchResponse> searchSpot();

    @POST("spot/{id}")
    Call<SpotResponse> showSpot(@Path("id") String id);

    @POST("spot/{id}/comments")
    Call<SpotCommentsResponse> showComments(@Path("id") String id);
}
