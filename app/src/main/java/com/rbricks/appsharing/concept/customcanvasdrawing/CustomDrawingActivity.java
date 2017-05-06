package com.rbricks.appsharing.concept.customcanvasdrawing;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rbricks.appsharing.R;

public class CustomDrawingActivity extends AppCompatActivity {

    private ImageView firstIv;
    private ImageView secondIv;
    private CustomDrawLine customDrawLine;
    private RelativeLayout parentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drawing_relativelayout);
        initViews();
    }

    private void initViews() {
        firstIv = (ImageView) findViewById(R.id.first_iv);
        secondIv = (ImageView) findViewById(R.id.second_iv);
        parentView = ((RelativeLayout) findViewById(R.id.parent_layout));
        firstIv.postDelayed(() -> drawLineBetweenCenters(), 50);
    }

    private void drawLine() {
        float x = firstIv.getX();
        float y = (firstIv.getY() + firstIv.getHeight());
        Pair<Float, Float> secondViewCoordinates = Pair.create(secondIv.getX(), secondIv.getY());
        customDrawLine = new CustomDrawLine(this, x, y, secondViewCoordinates.first, secondViewCoordinates.second, new CustomDrawLine.LineConfiguration("#FF0000", 20, true));
        parentView.addView(customDrawLine);
    }

    private void drawLineBetweenCenters() {
//        RectF firstRect = calculeRectOnScreen(firstIv);
//        RectF secondRect = calculeRectOnScreen(secondIv);
        float startX, startY, endX, endY;
        startX = firstIv.getX() + firstIv.getWidth() / 2;
        startY = firstIv.getBottom();
        endX = startX;
        endY = secondIv.getY();
//        customDrawLine = new CustomDrawLine(this, firstRect.centerX(), firstRect.bottom, firstRect.centerX(), secondRect.top, new CustomDrawLine.LineConfiguration("#FF0000", 20, true));
        customDrawLine = new CustomDrawLine(this, startX, startY, endX, endY, new CustomDrawLine.LineConfiguration("#FF0000", 20, true));
        parentView.addView(customDrawLine);
    }

    // don't use this , not working fine
    /*public static RectF calculeRectOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }*/
}
