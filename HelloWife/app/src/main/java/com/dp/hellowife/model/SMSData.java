package com.dp.hellowife.model;

/**
 * Created by pradeepd on 08-01-2016.
 */
public class SMSData {

    String body;
    String date;
    String number;

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
