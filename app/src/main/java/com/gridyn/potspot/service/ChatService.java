package com.gridyn.potspot.service;

import com.gridyn.potspot.query.SendMessageQuery;
import com.gridyn.potspot.response.MessageHistoryResponse;
import com.gridyn.potspot.response.MessageLastResponse;
import com.gridyn.potspot.response.MessageSendResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ChatService {

    @POST("user/message/new/{id}")
    Call<MessageSendResponse> sendMessage(@Path("id") String id,
                                          @Body SendMessageQuery sendMessageQuery);

    @POST("user/messages/show/last")
    Call<MessageLastResponse> showLastMessage(@Body Map<String, String> token);

    @POST("user/messages/{id}/history")
    Call<MessageHistoryResponse> showHistoryMessage(@Path("id") String id,
                                                    @Body Map<String, String> token);
}


/*
/API/user/message/new/@id - Новое сообщение (указываешься куда и в посте поле message)
/API/user/messages/new/ - показывает только новые
/API/user/messages/show/last - показывает последние
/API/user/messages/@id/history - показывает все
*/