package com.dp.hellowife.interfaces;

import android.graphics.Typeface;

/**
 * Created by akshayas on 2/2/2016.
 */
public interface ConfigBuilder {
    public ConfigBuilder width(int width);

    public ConfigBuilder height(int height);

    public ConfigBuilder textColor(int color);

    public ConfigBuilder withBorder(int thickness);

    public ConfigBuilder useFont(Typeface font);

    public ConfigBuilder fontSize(int size);

    public ConfigBuilder bold();

    public ConfigBuilder toUpperCase();

    public ShapeBuilder endConfig();
}
