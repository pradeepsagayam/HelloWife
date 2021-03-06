package com.dp.hellowife.login;

import android.content.Intent;
import android.content.SharedPreferences;

import com.dp.hellowife.activity.HiddenActivity;
import com.msf.pinlibrary.PrefHelper;
import com.msf.pinlibrary.SetPin;

/**
 * Created by akshayas on 1/28/2016.
 */
public class SetUserPin extends SetPin {
    @Override
    public void onPinSet(String pin) {
//        SharedPreferences.Editor edit = MainActivity.pinLockPrefs.edit();
        SharedPreferences.Editor edit = PrefHelper.getPref(getApplicationContext(), "PinLockPrefs").edit();
        edit.putString("pin", pin);
        edit.putBoolean("isPinSet", true);
        edit.commit();

        Intent intent = new Intent(SetUserPin.this, HiddenActivity.class);
        startActivity(intent);
//        setResult(SUCCESS);
        finish();
    }
}
