package com.rbricks.appsharing.newconcept.hikecameraconcepts.basiccamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.rbricks.appsharing.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.rbricks.appsharing.concept.notesapp.utils.ViewUtils.setGone;


public class AndroidCameraForwardActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int VIDEO_REQ_CODE = 15;
    Bitmap bitmap;
    private ImageView camForwIv;
    private int CAMERA_REQ_CODE = 4;
    private int GALLERY_REQ_CODE = 10;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_camera_forward);

        camForwIv = (ImageView) findViewById(R.id.cam_forw_iv);
        mVideoView = ((VideoView) findViewById(R.id.cam_for_videoview));
        findViewById(R.id.cam_forw_takepic).setOnClickListener(this);
        findViewById(R.id.cam_forw_gallerybtn).setOnClickListener(this);
        findViewById(R.id.cam_forw_takevideo).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;
        if ((requestCode == GALLERY_REQ_CODE || requestCode == CAMERA_REQ_CODE) && resultCode == Activity.RESULT_OK) {
            try {
                // recyle unused bitmaps
                /*if (bitmap != null) {
                    bitmap.recycle();
                }*/

                if (requestCode == GALLERY_REQ_CODE) {
                    stream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                } else if (requestCode == CAMERA_REQ_CODE) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                }
                camForwIv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } else if (requestCode == VIDEO_REQ_CODE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            mVideoView.setVideoURI(videoUri);
//            setGone(mVideoView);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cam_forw_takepic:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQ_CODE);
                break;
            case R.id.cam_forw_gallerybtn:
                forwardToPhotoGalleryAndroid();
                break;
            case R.id.cam_forw_takevideo:
                mVideoView.setVisibility(View.VISIBLE);
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, VIDEO_REQ_CODE);
                }
                break;
        }
    }

    public void forwardToPhotoGalleryAndroid() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, GALLERY_REQ_CODE);
    }
}
