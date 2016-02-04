package com.dp.hellowife.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;

import com.dp.hellowife.interfaces.ShapeBuilder;


/**
 * Created by akshayas on 2/2/2016.
 */
public class TextDrawableHelper extends ShapeDrawable{

    private Paint textPaint;
    private Paint borderPaint;
    private static final float SHADE_FACTOR = 0.9f;
    private String text;
    private int color;
    private RectShape shape;
    private int height;
    private int width;
    private int fontSize;
    private int borderThickness;

    protected TextDrawableHelper(BuilderHelper builder){
        super(builder.shape);

        shape = builder.shape;
        height = builder.height;
        width = builder.width;

        text = builder.toUpperCase ? builder.text.toUpperCase() : builder.text;
        color = builder.color;

        fontSize = builder.fontSize;
        textPaint = new Paint();
        textPaint.setColor(builder.textColor);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(builder.isBold);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(builder.font);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(builder.borderThickness);

        // border paint settings
        borderThickness = builder.borderThickness;
        borderPaint = new Paint();
        borderPaint.setColor(getDarkerShade(color));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderThickness);

        Paint paint = getPaint();
        paint.setColor(color);
    }

    private int getDarkerShade(int color) {
        return Color.rgb((int) (SHADE_FACTOR * Color.red(color)),
                (int) (SHADE_FACTOR * Color.green(color)),
                (int) (SHADE_FACTOR * Color.blue(color)));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Rect rect = getBounds();

        if (borderThickness > 0){
            drawBorder(canvas);
        }

        int count = canvas.save();
        canvas.translate(rect.left, rect.top);

        int width = this.width < 0 ? rect.width() : this.width;
        int height = this.height < 0 ? rect.height() : this.height;
        int fontSize = this.fontSize < 0 ? (Math.min(width,height)) : this.fontSize;
        textPaint.setTextSize(fontSize);
        canvas.drawText(text, width / 2, height / 2 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);

        canvas.restoreToCount(count);

    }

    private void drawBorder(Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        rectF.inset(borderThickness / 2, borderThickness / 2);

        if (shape instanceof OvalShape){
            canvas.drawOval(rectF, borderPaint);
        } else {
            canvas.drawRect(rectF, borderPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        textPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    public static ShapeBuilder builder(){
        return new BuilderHelper();
    }
}
