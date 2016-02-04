package com.dp.hellowife.interfaces;


import com.dp.hellowife.helper.TextDrawableHelper;

/**
 * Created by akshayas on 2/2/2016.
 */
public interface ShapeBuilder {
    public ConfigBuilder beginConfig();

    public Builder round();

    public TextDrawableHelper buildRound(String text, int color);
}
