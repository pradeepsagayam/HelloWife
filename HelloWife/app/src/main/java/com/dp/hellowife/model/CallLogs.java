package com.dp.hellowife.model;


import android.net.Uri;

/**
 * Created by pradeepd on 07-01-2016.
 */
public class CallLogs {

    String callLogID;
    String name;
    String number;
    String contactId;
    String callType;
    int Type;
    String numberType;
    int callNumberType;
    String callDate;
    String Duration;
    Uri contactPhotoUri;

    public String getCallLogID() {
        return callLogID;
    }

    public void setCallLogID(String callLogID) {
        this.callLogID = callLogID;
    }

    public Uri getContactPhotoUri() {
        return contactPhotoUri;
    }

    public void setContactPhotoUri(Uri contactPhotoUri) {
        this.contactPhotoUri = contactPhotoUri;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public int getCallNumberType() {
        return callNumberType;
    }

    public void setCallNumberType(int callNumberType) {
        this.callNumberType = callNumberType;
    }


    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

}
