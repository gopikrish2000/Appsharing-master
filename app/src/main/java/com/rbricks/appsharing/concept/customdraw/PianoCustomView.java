package com.rbricks.appsharing.concept.customdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rbricks.appsharing.R;

import static com.rbricks.appsharing.utils.CommonUtils.setAllNull;

/**
 * Created by gopi on 23/05/17.
 */

public class PianoCustomView extends View {

    private Paint whiteCKeyPaint, blackCKeyPaint;
    private Rect whiteCRect, blackCRect;

    public PianoCustomView(Context context) {
        super(context);
    }

    public PianoCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PianoCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        whiteCRect = new Rect(20, 20, 200, 200);
        blackCRect = new Rect(10, 10, 100, 100);
        whiteCKeyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blackCKeyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whiteCKeyPaint.setStrokeWidth(2);
        whiteCKeyPaint.setColor(getResources().getColor(R.color.red));
        whiteCKeyPaint.setStyle(Paint.Style.FILL);
        blackCKeyPaint.setStrokeWidth(1);
        blackCKeyPaint.setColor(getResources().getColor(R.color.black));
        blackCKeyPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setAllNull(whiteCKeyPaint, blackCKeyPaint, whiteCRect, blackCRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(whiteCRect, whiteCKeyPaint);
        canvas.drawRect(blackCRect, blackCKeyPaint);
        canvas.drawLine(100,100,500,500, blackCKeyPaint);
    }
}
