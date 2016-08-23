package com.gridyn.potspot.model;

/**
 * Created by dmytro_vodnik on 8/23/16.
 * working on potspot project
 */
public class PaymentHistoryItem {

    String date;
    String value;

    public PaymentHistoryItem(String date, String value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public String toString() {
        return "PaymentHistoryItem{" +
                "date='" + date + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
