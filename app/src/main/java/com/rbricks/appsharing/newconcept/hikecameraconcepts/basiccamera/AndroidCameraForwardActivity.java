package com.rbricks.appsharing.newconcept.hikecameraconcepts.basiccamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.rbricks.appsharing.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class AndroidCameraForwardActivity extends AppCompatActivity implements View.OnClickListener {
    Bitmap bitmap;
    private ImageView camForwIv;
    private int CAMERA_REQ_CODE = 4;
    private int GALLERY_REQ_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_camera_forward);

        camForwIv = (ImageView) findViewById(R.id.cam_forw_iv);
        findViewById(R.id.cam_forw_takepic).setOnClickListener(this);
        findViewById(R.id.cam_forw_gallerybtn).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;
        if ((requestCode == GALLERY_REQ_CODE || requestCode == CAMERA_REQ_CODE) && resultCode == Activity.RESULT_OK)
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
