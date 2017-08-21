package com.rbricks.appsharing.newconcept.hikecameraconcepts.basiccamera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
//import android.hardware.camera2;
import android.hardware.camera2.CameraManager;
import android.media.audiofx.AudioEffect;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.rbricks.appsharing.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.commonsware.cwac.cam2.ClassicCameraEngine.*;

public class BasicCameraActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout camPreviewFrame;
    private Camera camera;
    CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_camera);
        init();
        doProcess();
    }

    private void init() {
        findViewById(R.id.cam_takepic_btn).setOnClickListener(this);
        camPreviewFrame = (FrameLayout) findViewById(R.id.cam_preview_frame);
        cameraPreview = new CameraPreview(this, getCameraInstance());
        camPreviewFrame.addView(cameraPreview);
//        camera = cameraPreview.mCamera;
    }

    private void doProcess() {
    }

    private void takePhoto() {
//        Camera cameraInstance = camera;
//        camera.startPreview();
        camera.takePicture(null, null, (byte[] data, Camera camInstance) -> {
            File outPutFile = getOutputMediaFile();
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(outPutFile);
                fileOutputStream.write(data);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            camera.stopPreview();
//            camera.release();
//            camera = null;
        });
    }

    private Camera getCameraInstance() {
        if (!checkCameraHardware(getApplicationContext())) {
            return null;
        }
        if (camera == null) {
           /* int count = Camera.getNumberOfCameras();
                        List<Descriptor> result = new ArrayList<AudioEffect.Descriptor>();
                        Camera.CameraInfo info = new Camera.CameraInfo();

                        for (int cameraId = 0; cameraId < count; cameraId++) {
                            Camera.getCameraInfo(cameraId, info);
                            Descriptor descriptor = new Descriptor(cameraId, info);

                            Camera camera = Camera.open(descriptor.getCameraId());*/
            camera = Camera.open();
        }
        return camera;
    }

    private boolean checkCameraHardware(Context context) {
    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
        // this device has a camera
        return true;
    } else {
        // no camera on this device
        return false;
    }
}

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_GOPI_" + timeStamp + ".jpg");

        return mediaFile;
    }

    class CameraPreview extends SurfaceView implements
            SurfaceHolder.Callback {
        private SurfaceHolder mSurfaceHolder;
        private Camera mCamera;

        // Constructor that obtains context and camera
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
                mCamera = camera;
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
            } catch (IOException e) {
                // left blank for now
//                mCamera = null;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//            if( mCamera != null )
            try {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
//            mCamera.release();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format,
                                   int width, int height) {
            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (Exception e) {
                // intentionally left blank for a test
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cam_takepic_btn:
                takePhoto();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();   // some problem on doing these that after release you shouldn't use the camera object . else exception.
       /* try {
            camera.release();
            cameraPreview.getHolder().removeCallback(cameraPreview);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*try {
            if (camera != null) {
                camera = Camera.open();
//                camera.reconnect();
                cameraPreview.getHolder().addCallback(cameraPreview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
