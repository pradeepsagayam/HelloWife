package com.dp.hellowife.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by pradeepd on 08-01-2016.
 */
public class Utils {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
