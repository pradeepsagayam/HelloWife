package com.dp.hellowife.login;

import android.content.Intent;
import android.os.Bundle;

import com.dp.hellowife.activity.HiddenActivity;
import com.msf.pinlibrary.ConfirmPin;
import com.msf.pinlibrary.PrefHelper;

/**
 * Created by akshayas on 1/28/2016.
 */
public class EnterPin extends ConfirmPin{
    private String currentPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentPin = PrefHelper.getPref(getApplicationContext(), "PinLockPrefs").getString("pin", "");
//        currentPin = MainActivity.pinLockPrefs.getString("pin", "");
    }

    @Override
    public boolean isPinCorrect(String pin) {
        if (pin.equals(currentPin)) {
            Intent intent = new Intent(EnterPin.this, HiddenActivity.class);
            startActivity(intent);
        }
        return pin.equals(currentPin);
//        return false;
    }

    @Override
    public void onForgotPin() {

    }
}
