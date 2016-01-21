package com.msf.pinlibrary;

import android.os.Bundle;

/**
 * Created by akshayas on 1/14/2016.
 */
public abstract class SetPin extends BasePin {

    private String firstPin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLabel(TEXT_NEW_PIN);
        disableForgotButton();
    }

    @Override
    public final void onCompleted(String pin) {
        resetStatus();
        if ("".equals(firstPin)) {
            firstPin = pin;
            setLabel(TEXT_CONFIRM_PIN);
        } else {
            if (pin.equals(firstPin)) {
                onPinSet(pin);
                setResult(SUCCESS);
                finish();
            } else {
                setLabel(TEXT_PIN_MISMATCH);
                firstPin = "";
            }
        }
        resetStatus();
    }

    public abstract void onPinSet(String pin);

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(CANCELLED);
        finish();
    }
}
