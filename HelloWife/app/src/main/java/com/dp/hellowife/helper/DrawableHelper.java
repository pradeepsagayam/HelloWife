package com.dp.hellowife.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by akshayas on 2/2/2016.
 */
public class DrawableHelper {

    public static final int ROUND = 1;
    public static final int ROUND_WITH_BORDER = 2;

    private final ColorGeneratorHelper mColorGenerator;
    private final Context mContext;

    public DrawableHelper(Context mContext) {
        this.mColorGenerator = ColorGeneratorHelper.DEFAULT;
        this.mContext = mContext;
    }

    public TextDrawableHelper getRound(String text){
        return TextDrawableHelper.builder().buildRound(text, mColorGenerator.getColor(text));
    }

    public TextDrawableHelper getRoundWithBorder(String text){
        return TextDrawableHelper.builder().beginConfig().withBorder(toPixel(2)).endConfig().buildRound(text, mColorGenerator.getColor(text));
    }

    private int toPixel(int dp) {
        Resources resources = mContext.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
