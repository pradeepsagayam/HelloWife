package com.dp.hellowife;

import android.os.Bundle;

import com.msf.pinlibrary.ConfirmPin;

/**
 * Created by akshayas on 1/28/2016.
 */
public class EnterPin extends ConfirmPin{
    private String currentPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPin = MainActivity.pinLockPrefs.getString("pin", "");
    }

    @Override
    public boolean isPinCorrect(String pin) {
        return pin.equals(currentPin);
    }

    @Override
    public void onForgotPin() {

    }
}
