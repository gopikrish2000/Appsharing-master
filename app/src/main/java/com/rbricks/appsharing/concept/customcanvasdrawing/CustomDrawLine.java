package com.rbricks.appsharing.concept.customcanvasdrawing;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Gopi Krishna on 13/03/17.
 */

public class CustomDrawLine extends View {
    private Paint paint;
    private Path path;
    float startX, startY, endX, endY;

    public static class LineConfiguration {
        String colorHexaDecimal = "#0000RR";  // Format should be starting with #
        int width = 10;
        boolean isDottedLine = true;

        public LineConfiguration() {
        }

        public LineConfiguration(String colorHexaDecimal, int width, boolean isDottedLine) {
            if (!TextUtils.isEmpty(colorHexaDecimal) && colorHexaDecimal.startsWith("#")) {
                this.colorHexaDecimal = colorHexaDecimal;
            }
            this.width = width;
            this.isDottedLine = isDottedLine;
        }
    }

    public CustomDrawLine(Context context, float startX, float startY, float endX, float endY, LineConfiguration lineConfiguration) {
        super(context);
        paintConfiguration(context, startX, startY, endX, endY, lineConfiguration);
    }

    public CustomDrawLine(Context context, @Nullable AttributeSet attrs, float startX, float startY, float endX, float endY, LineConfiguration lineConfiguration) {
        super(context, attrs);
        paintConfiguration(context, startX, startY, endX, endY, lineConfiguration);
    }

    public CustomDrawLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr, float startX, float startY, float endX, float endY, LineConfiguration lineConfiguration) {
        super(context, attrs, defStyleAttr);
        paintConfiguration(context, startX, startY, endX, endY, lineConfiguration);
    }

    private void paintConfiguration(Context context, float startX, float startY, float endX, float endY, LineConfiguration lineConfiguration) {
        if (lineConfiguration == null) {
            lineConfiguration = new LineConfiguration();
        }
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        paint = new Paint();
        path = new Path();
        int color = Color.parseColor(lineConfiguration.colorHexaDecimal);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineConfiguration.width);
        if (lineConfiguration.isDottedLine) {
            paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);
        canvas.drawPath(path, paint);
    }
}
