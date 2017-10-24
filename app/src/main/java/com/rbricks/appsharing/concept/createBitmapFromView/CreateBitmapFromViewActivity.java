package com.rbricks.appsharing.concept.createBitmapFromView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.rbricks.appsharing.R;

public class CreateBitmapFromViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bitmap_from_view);
    }

    private Bitmap fromView(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    // Second Way
    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    // Second Way
    public static Bitmap loadBitmapFromViewMeasured(View v) {

        if (v.getMeasuredHeight() <= 0) {
            v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }
}
