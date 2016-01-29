package com.msf.pinlibrary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akshayas on 1/29/2016.
 */
public class PrefHelper {


    public static SharedPreferences getPref(Context context, String fileName){
        return context.getSharedPreferences(fileName, context.MODE_PRIVATE);
    }
}
