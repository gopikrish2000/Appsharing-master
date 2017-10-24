package com.rbricks.appsharing.architecture.OpenGLES2.basic;

import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by gopi on 24/10/17.
 */

public abstract class GLGenericRenderer implements GLSurfaceView.Renderer {


    private static final String TAG = GLGenericRenderer.class.getSimpleName();
    private long lastUsedTime;
    private boolean isSurfaceCreated;
    private int mWidth, mHeight, mFps;
    private boolean isFirstDraw;

    public GLGenericRenderer() {
        lastUsedTime = System.currentTimeMillis();
    }

    @Override
    public void onSurfaceCreated(GL10 notUsed, EGLConfig eglConfig) {
        Log.d(TAG, "GLGenericRenderer surface created**");
        isSurfaceCreated = true;
        mHeight = -1;
        mWidth = -1;
    }

    @Override
    public void onSurfaceChanged(GL10 notUsed, int width, int height) {
        if (!isSurfaceCreated && mWidth == width && mHeight == height) {
            Log.d(TAG, "surface change already handled");
            return;
        }

        mWidth = width;
        mHeight = height;
        surfaceChanged(width, height);
        isSurfaceCreated = false;
    }

    @Override
    public void onDrawFrame(GL10 notUsed) {
        onDraw(isFirstDraw);
        long cur = System.currentTimeMillis();
        if (lastUsedTime - cur > 1000) {
            mFps = 0;
            lastUsedTime = cur;
        } else {
            mFps++;
        }
        if (isFirstDraw) {
            isFirstDraw = false;
        }
    }

    public int getFps() {
        return mFps;
    }

    public abstract void surfaceChanged(int width, int height);

    public abstract void onDraw(boolean isFirstDraw);

    public abstract void surfaceCreated();
}
