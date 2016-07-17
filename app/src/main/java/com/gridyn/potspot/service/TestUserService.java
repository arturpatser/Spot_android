package com.gridyn.potspot.service;

import com.gridyn.potspot.response.UserLoginResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface TestUserService {

    @POST("user/login")
    Call<UserLoginResponse> loginUser(@Body Map<String, String> data);

    @POST("user/{token}")
    Call<Response> getUserInfo(@Path("token") String token);
}
