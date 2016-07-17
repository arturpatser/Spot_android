package com.gridyn.potspot.service;

import com.gridyn.potspot.query.CreateSpotQuery;
import com.gridyn.potspot.query.SearchCriteriaQuery;
import com.gridyn.potspot.response.SpotCommentsResponse;
import com.gridyn.potspot.response.SpotCreateResponse;
import com.gridyn.potspot.response.SpotInfoResponse;
import com.gridyn.potspot.response.SpotSearchResponse;
import com.gridyn.potspot.response.SpotUpdateResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SpotService {

    @POST("spot/create")
    Call<SpotCreateResponse> createSpot(@Body CreateSpotQuery spot);

    @POST("spot/{token}/update")
    Call<SpotUpdateResponse> updateSpot(@Path("token") String token, Map<String, String> data);

    @POST("spot/search")
    Call<SpotSearchResponse> searchSpot(@Body SearchCriteriaQuery searchCriteria);

    @POST("spot/{id}")
    Call<SpotInfoResponse> getSpot(@Path("id") String id);

    @POST("spot/{id}/comments")
    Call<SpotCommentsResponse> getComments(@Path("id") String id);
}
