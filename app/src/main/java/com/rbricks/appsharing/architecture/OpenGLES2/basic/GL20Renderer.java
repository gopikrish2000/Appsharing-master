package com.rbricks.appsharing.architecture.OpenGLES2.basic;

import android.opengl.GLES20;

/**
 * Created by gopi on 24/10/17.
 */

public class GL20Renderer extends GLGenericRenderer {
    @Override
    public void surfaceChanged(int width, int height) {
        GLES20.glClearColor(1f, 0f, 0f, 1f);
    }

    @Override
    public void onDraw(boolean isFirstDraw) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
                | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void surfaceCreated() {

    }
}
