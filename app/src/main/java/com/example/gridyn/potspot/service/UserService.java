package com.example.gridyn.potspot.service;

import com.example.gridyn.potspot.query.EnableHostQuery;
import com.example.gridyn.potspot.query.FeedbackQuery;
import com.example.gridyn.potspot.query.UserUpdateQuery;
import com.example.gridyn.potspot.response.UserCommentCreateResponse;
import com.example.gridyn.potspot.response.UserCommentDeleteResponse;
import com.example.gridyn.potspot.response.UserCommentsResponse;
import com.example.gridyn.potspot.response.UserCreateResponse;
import com.example.gridyn.potspot.response.UserEnableHostResponse;
import com.example.gridyn.potspot.response.UserFeedbackResponse;
import com.example.gridyn.potspot.response.UserInfoResponse;
import com.example.gridyn.potspot.response.UserLoginResponse;
import com.example.gridyn.potspot.response.UserUpdateResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface UserService {

    @POST("user/create")
    Call<UserCreateResponse> createUser(@Body Map<String, String> user);

    @POST("user/{token}")
    Call<UserInfoResponse> getUserInfo(@Path("token") String token);

    @POST("user/login")
    Call<UserLoginResponse> loginUser(@Body Map<String, String> data);

    @POST("user/update")
    Call<UserUpdateResponse> updateUser(@Body UserUpdateQuery dataForUpdate);

    @POST("user/{token}/comments")
    Call<UserCommentsResponse> getComments(@Path("token") String token);

    @POST("user/{token}/comment/create")
    Call<UserCommentCreateResponse> createComment(@Path("token") String token,
                                                  @Body Map<String, String> comment);

    @POST("user/{token}/comment/delete")
    Call<UserCommentDeleteResponse> deleteComment(@Path("token") String token);

    @POST("user/comment/{comment_id}")
    Call<Object> somthingDoComment(@Path("comment_id") String commentId);

    @POST("user/enable_host")
    Call<UserEnableHostResponse> enableHost(@Body EnableHostQuery picture);

    @POST("feedback/add")
    Call<UserFeedbackResponse> sendFeedback(@Body FeedbackQuery feedback);
}
