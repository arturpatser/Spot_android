package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dmytro_vodnik on 8/30/16.
 * working on potspot project
 */
public class BookResponse {

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("message")
    @Expose
    List<BookMessage> bookMessage;

    public List<BookMessage> getBookMessage() {
        return bookMessage;
    }

    public void setBookMessage(List<BookMessage> bookMessage) {
        this.bookMessage = bookMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BookResponse{" +
                "success=" + success +
                ", bookMessage=" + bookMessage +
                '}';
    }

    private class BookMessage {

        @SerializedName("id")
        @Expose
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "BookMessage{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}
