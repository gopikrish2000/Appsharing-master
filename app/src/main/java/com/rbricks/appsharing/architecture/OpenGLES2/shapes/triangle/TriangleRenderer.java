package com.rbricks.appsharing.architecture.OpenGLES2.shapes.triangle;

import android.opengl.GLES20;

import com.rbricks.appsharing.architecture.OpenGLES2.basic.GLGenericRenderer;

/**
 * Created by gopi on 24/10/17.
 */

public class TriangleRenderer extends GLGenericRenderer {
    TriangleShape triangleShape;

    @Override
    public void surfaceChanged(int width, int height) {
        GLES20.glClearColor(1f, 0f, 0f, 1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
                | GLES20.GL_DEPTH_BUFFER_BIT);
        triangleShape.draw();
    }

    @Override
    public void onDraw(boolean isFirstDraw) {

    }

    @Override
    public void surfaceCreated() {
        triangleShape = new TriangleShape();
    }
}
