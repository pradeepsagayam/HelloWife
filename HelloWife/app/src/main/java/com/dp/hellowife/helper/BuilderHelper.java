package com.dp.hellowife.helper;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;

import com.dp.hellowife.interfaces.ConfigBuilder;
import com.dp.hellowife.interfaces.ShapeBuilder;

/**
 * Created by akshayas on 2/2/2016.
 */
public class BuilderHelper implements ConfigBuilder, ShapeBuilder, com.dp.hellowife.interfaces.Builder {

    protected String text;
    protected int color;
    public int textColor;
    protected int borderThickness;
    protected int width;
    protected int height;
    protected RectShape shape;
    protected Typeface font;
    protected int fontSize;
    protected boolean isBold;
    protected boolean toUpperCase;

    protected BuilderHelper() {
        text = "";
        color = Color.GRAY;
        textColor = Color.WHITE;
        borderThickness = 0;
        width = -1;
        height = -1;
        shape = new RectShape();
        font = Typeface.create("sans-serif-light", Typeface.NORMAL);
        fontSize = -1;
        isBold = false;
        toUpperCase = false;
    }

    @Override
    public TextDrawableHelper build(String text, int color) {
        this.color = color;
        this.text = text;
        return new TextDrawableHelper(this);
    }

    @Override
    public ConfigBuilder width(int width) {
        this.width = width;
        return this;
    }

    @Override
    public ConfigBuilder height(int height) {
        this.height = height;
        return this;
    }

    @Override
    public ConfigBuilder textColor(int color) {
        this.textColor = color;
        return this;
    }

    @Override
    public ConfigBuilder withBorder(int thickness) {
        this.borderThickness = thickness;
        return this;
    }

    @Override
    public ConfigBuilder useFont(Typeface font) {
        this.font = font;
        return this;
    }

    @Override
    public ConfigBuilder fontSize(int size) {
        this.fontSize = size;
        return this;
    }

    @Override
    public ConfigBuilder bold() {
        this.isBold = true;
        return this;
    }

    @Override
    public ConfigBuilder toUpperCase() {
        this.toUpperCase = true;
        return this;
    }

    @Override
    public ShapeBuilder endConfig() {
        return this;
    }

    @Override
    public ConfigBuilder beginConfig() {
        return this;
    }

    @Override
    public com.dp.hellowife.interfaces.Builder round() {
        this.shape = new OvalShape();
        return this;
    }

    @Override
    public TextDrawableHelper buildRound(String text, int color) {
        round();
        return build(text, color);
    }
}
