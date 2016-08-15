package com.gridyn.potspot.service;

import com.gridyn.potspot.query.SendMessageQuery;
import com.gridyn.potspot.response.SendMessageResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ChatService {

    @POST("user/message/new/{id}")
    Call<SendMessageResponse> sendMessage(@Path("id") String id, @Body SendMessageQuery sendMessageQuery);
}


/*
/API/user/message/new/@id - Новое сообщение (указываешься куда и в посте поле message)
/API/user/messages/new/ - показывает только новые
/API/user/messages/show/last - показывает последние
/API/user/messages/@id/history - показывает все
*/