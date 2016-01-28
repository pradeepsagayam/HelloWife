package com.dp.hellowife;

import android.content.SharedPreferences;

import com.msf.pinlibrary.SetPin;

/**
 * Created by akshayas on 1/28/2016.
 */
public class SetUserPin extends SetPin {
    @Override
    public void onPinSet(String pin) {
        SharedPreferences.Editor edit = MainActivity.pinLockPrefs.edit();
        edit.putString("pin", pin);
        edit.commit();
//        System.out.println("AAAA pin : " + pin);
        setResult(SUCCESS);
        finish();
    }
}
