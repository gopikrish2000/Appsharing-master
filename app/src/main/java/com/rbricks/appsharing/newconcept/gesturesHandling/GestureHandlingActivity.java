package com.rbricks.appsharing.newconcept.gesturesHandling;

import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.rbricks.appsharing.R;

public class GestureHandlingActivity extends AppCompatActivity {

    private SimpleDraweeView gifDrawee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_handling);

        initViews();
        initLogic();
    }

    private void initLogic() {

    }

    private void initViews() {
        gifDrawee = (SimpleDraweeView) findViewById(R.id.fresco_simpleDrawee);
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (anim != null) {
                    // app-specific logic to enable animation starting
                    anim.start();
                }
            }
        };
        DraweeController controller =
                Fresco.newDraweeControllerBuilder()
                        .setUri("http://i.imgur.com/jLBIIZg.gif")  //   //"https://dummyimage.com/100x100/9e419e/fff"
                        .setAutoPlayAnimations(true)
                        .setControllerListener(controllerListener)
                        .build();


        gifDrawee.setController(controller);
    }
}
