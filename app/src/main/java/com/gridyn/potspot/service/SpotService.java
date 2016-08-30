package com.gridyn.potspot.service;

import com.gridyn.potspot.query.CreateSpotQuery;
import com.gridyn.potspot.query.SearchCriteriaQuery;
import com.gridyn.potspot.query.UpdateSpotQuery;
import com.gridyn.potspot.response.SpotCommentsResponse;
import com.gridyn.potspot.response.SpotCreateResponse;
import com.gridyn.potspot.response.SpotDeleteResponse;
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

    @POST("spot/update")
    Call<SpotUpdateResponse> updateSpot(@Body UpdateSpotQuery data);

    @POST("spot/search")
    Call<SpotSearchResponse> searchSpot(@Body SearchCriteriaQuery searchCriteria);

    @POST("spot/{id}")
    Call<SpotInfoResponse> getSpot(@Path("id") String id);

    @POST("spot/{id}")
    Call<SpotInfoResponse> getSpot(@Path("id") String id, @Body Map<String, String> token);

    @POST("spot/{id}/comments")
    Call<SpotCommentsResponse> getComments(@Path("id") String id, @Body Map<String, String> token);

    @POST("spot/delete")
    Call<SpotDeleteResponse> deleteSpot(@Body Map<String, String> token);
}
