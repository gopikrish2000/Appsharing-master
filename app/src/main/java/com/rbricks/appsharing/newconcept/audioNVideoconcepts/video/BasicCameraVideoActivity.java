package com.rbricks.appsharing.newconcept.audioNVideoconcepts.video;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.rbricks.appsharing.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.rbricks.appsharing.newconcept.audioNVideoconcepts.video.CameraUtils.MEDIA_TYPE_VIDEO;

//import android.hardware.camera2;

// To take both Picture & Video
public class BasicCameraVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BasicCameraVideoActivity.class.getSimpleName();
    private FrameLayout camPreviewFrame;
    private Camera mCamera;
    CameraPreview cameraPreview;
    private MediaRecorder mMediaRecorder;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_camera_new);
        init();
        doProcess();
    }

    private void init() {
        findViewById(R.id.cam_takepic_btn).setOnClickListener(this);
        findViewById(R.id.video_record_btn).setOnClickListener(this);
        camPreviewFrame = (FrameLayout) findViewById(R.id.cam_preview_frame);
        cameraPreview = new CameraPreview(this, getCameraInstance());
        camPreviewFrame.addView(cameraPreview);
//        mCamera = cameraPreview.mCamera;
    }

    private void doProcess() {
    }

    private void takePhoto() {
//        Camera cameraInstance = mCamera;
//        mCamera.startPreview();
        mCamera.takePicture(null, null, (byte[] data, Camera camInstance) -> {
            File outPutFile = CameraUtils.getOutputMediaFile();
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(outPutFile);
                fileOutputStream.write(data);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            mCamera.stopPreview();
//            mCamera.release();
//            mCamera = null;
        });
    }

    private Camera getCameraInstance() {
        if (!CameraUtils.checkCameraHardware(getApplicationContext())) {
            return null;
        }
        if (mCamera == null) {
           /* int count = Camera.getNumberOfCameras();
                        List<Descriptor> result = new ArrayList<AudioEffect.Descriptor>();
                        Camera.CameraInfo info = new Camera.CameraInfo();
                        for (int cameraId = 0; cameraId < count; cameraId++) {
                            Camera.getCameraInfo(cameraId, info);
                            Descriptor descriptor = new Descriptor(cameraId, info);
                            Camera mCamera = Camera.open(descriptor.getCameraId());*/
            mCamera = Camera.open(0);
        }
        return mCamera;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cam_takepic_btn:
                takePhoto();
                break;
            case R.id.video_record_btn:
                doVideoRecordingProcess();
                break;
        }
    }

    private void doVideoRecordingProcess() {
        if (isRecording) {
            // stop recording and release camera
            mMediaRecorder.stop();  // stop the recording
            releaseMediaRecorder(); // release the MediaRecorder object
            mCamera.lock();         // take camera access back from MediaRecorder

            // inform the user that recording has stopped
//                setCaptureButtonText("Capture");
            Toast.makeText(this, "Capture", Toast.LENGTH_SHORT).show();
            isRecording = false;
        } else {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder.start();

                // inform the user that recording has started
//                    setCaptureButtonText("Stop");
                Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
                isRecording = true;
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                // inform user
            }
        }
    }

    private boolean prepareVideoRecorder() {

        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set mCamera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        mMediaRecorder.setOutputFile(CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());

        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(cameraPreview.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();   // some problem on doing these that after release you shouldn't use the mCamera object . else exception.
        /*try {
            mCamera.release();
            cameraPreview.getHolder().removeCallback(cameraPreview);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            releaseMediaRecorder();       // if you are using MediaRecorder, release it first
            releaseCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*try {
            if (mCamera != null) {
                mCamera = Camera.open();
//                mCamera.reconnect();
                cameraPreview.getHolder().addCallback(cameraPreview);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}