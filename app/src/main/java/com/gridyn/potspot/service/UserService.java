package com.gridyn.potspot.service;

import com.gridyn.potspot.query.BookQuery;
import com.gridyn.potspot.query.EnableHostQuery;
import com.gridyn.potspot.query.FeedbackQuery;
import com.gridyn.potspot.query.LoginQuery;
import com.gridyn.potspot.query.PhoneConfirmQuery;
import com.gridyn.potspot.query.PhoneVerifyQuery;
import com.gridyn.potspot.query.UserUpdateQuery;
import com.gridyn.potspot.response.BookResponse;
import com.gridyn.potspot.response.MySpotResponse;
import com.gridyn.potspot.response.PaymentResponse;
import com.gridyn.potspot.response.PhoneConfirmResponse;
import com.gridyn.potspot.response.PhoneVerifyResponse;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.response.UserCommentCreateResponse;
import com.gridyn.potspot.response.UserCommentDeleteResponse;
import com.gridyn.potspot.response.UserCommentsResponse;
import com.gridyn.potspot.response.UserCreateResponse;
import com.gridyn.potspot.response.UserEnableHostResponse;
import com.gridyn.potspot.response.UserFeedbackResponse;
import com.gridyn.potspot.response.UserInfoResponse;
import com.gridyn.potspot.response.UserLoginResponse;
import com.gridyn.potspot.response.UserUpdateResponse;
import com.gridyn.potspot.response.friendResponse.FriendsResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface UserService {

    @POST("user/create")
    Call<UserCreateResponse> createUser(@Body Map<String, String> user);

    @POST("user/{id}")
    Call<UserInfoResponse> getUserInfo(@Path("id") String id, @Body Map<String, String> token);

    @POST("user/login")
    Call<UserLoginResponse> loginUser(@Body LoginQuery data);

    @POST("user/update")
    Call<UserUpdateResponse> updateUser(@Body UserUpdateQuery dataForUpdate);

    @POST("user/{id}/comments")
    Call<UserCommentsResponse> getComments(@Path("id") String token);

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

    @POST("user/{id}/spots")
    Call<MySpotResponse> getSpots(@Path("id") String idOfUser, @Body Map<String, String> token);

    @POST("user/phone/verify")
    Call<PhoneVerifyResponse> verifyPhone(@Body PhoneVerifyQuery phoneVerifyQuery);

    @POST("user/phone/confirm")
    Call<PhoneConfirmResponse> confirmPhone(@Body PhoneConfirmQuery phoneConfirmQuery);

    @POST("user/friends")
    Call<FriendsResponse> getFriends(@Body Map<String, String> token);

    @POST("booking/create/{id}")
    Call<BookResponse> bookSpot(@Path("id") String id, @Body BookQuery bookQuery);

    @POST("booking/{id}/host/confirm")
    Call<SuccessResponse> confirmBooking(@Path("id") String requestId, @Body Map<String, String> tokenMap);

    @POST("booking/{id}/host/decline")
    Call<SuccessResponse> declineBooking(@Path("id") String requestId, @Body Map<String, String> tokenMap);

    @POST("booking/{id}/confirm")
    Call<PaymentResponse> startPaying(@Path("id") String requestId, @Body Map<String, String> tokenMap);

    @POST("booking/{id}/cancel")
    Call<PaymentResponse> cancelPaying(@Path("id") String requestId, @Body Map<String, String> tokenMap);

    @POST("booking/{id}/friend/{fr_id}/add")
    Call<SuccessResponse> addFriendToBooking(@Path("id") String bookingId, @Path("fr_id") String friendId,
                                             @Body Map<String, String> token);
}