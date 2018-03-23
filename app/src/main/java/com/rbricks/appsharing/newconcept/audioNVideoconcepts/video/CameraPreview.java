package com.rbricks.appsharing.newconcept.audioNVideoconcepts.video;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements
            SurfaceHolder.Callback {
        private SurfaceHolder mSurfaceHolder;
        private Camera mCamera;

        @SuppressWarnings("deprecation")
        public CameraPreview(Context context, Camera camera) {
            super(context);
            this.mCamera = camera;
            this.mSurfaceHolder = this.getHolder();
            this.mSurfaceHolder.addCallback(this);
            this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
//                mCamera = camera;
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
            } catch (Exception e) {
//                mCamera = null;
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            try {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format,
                                   int width, int height) {
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }