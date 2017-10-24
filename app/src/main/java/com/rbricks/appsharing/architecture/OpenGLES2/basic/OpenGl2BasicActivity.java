package com.rbricks.appsharing.architecture.OpenGLES2.basic;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.utils.AppUtils;
import com.rbricks.appsharing.utils.CommonUtils;

public class OpenGl2BasicActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gl2_basic);

        init();
    }

    private void init() {
        if (!AppUtils.hasGLES20()) {
            CommonUtils.showToast("Open GL 2.0 not found quitting");
            return;
        }

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setPreserveEGLContextOnPause(true);
        glSurfaceView.setRenderer(new GL20Renderer());
        setContentView(glSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}
