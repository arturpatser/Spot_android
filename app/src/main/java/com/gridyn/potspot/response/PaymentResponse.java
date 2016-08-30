package com.gridyn.potspot.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dmytro_vodnik on 8/30/16.
 * working on potspot project
 */
public class PaymentResponse {

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("payment")
    @Expose
    List<PaymentModel> paymentModelList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PaymentModel> getPaymentModelList() {
        return paymentModelList;
    }

    public void setPaymentModelList(List<PaymentModel> paymentModelList) {
        this.paymentModelList = paymentModelList;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "success=" + success +
                ", paymentModelList=" + paymentModelList +
                '}';
    }

    private class PaymentModel {

        @SerializedName("payment")
        @Expose
        String paymentMessage;

        public String getPaymentMessage() {
            return paymentMessage;
        }

        public void setPaymentMessage(String paymentMessage) {
            this.paymentMessage = paymentMessage;
        }

        @Override
        public String toString() {
            return "PaymentModel{" +
                    "paymentMessage='" + paymentMessage + '\'' +
                    '}';
        }
    }
}
