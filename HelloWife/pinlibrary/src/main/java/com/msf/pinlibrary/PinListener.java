package com.msf.pinlibrary;

/**
 * Created by akshayas on 1/14/2016.
 */
public interface PinListener {

    int SUCCESS = 0;
    int CANCELLED = -1;
    int INVALID = 3;
    int FORGOT = 4;

    String TEXT_ENTER_PIN = "ENTER PIN";
    String TEXT_PIN_INVALID = "INVALID PIN. TRY AGAIN";
    String TEXT_NEW_PIN = "ENTER NEW PIN";
    String TEXT_CONFIRM_PIN = "RE-ENTER PIN TO CONFIRM";
    String TEXT_PIN_MISMATCH = "PIN DOESN'T MATCH. TRY AGAIN";

    void onPinValueChange(int length);

    void onCompleted(String pin);

    void onForgotPin();
}
