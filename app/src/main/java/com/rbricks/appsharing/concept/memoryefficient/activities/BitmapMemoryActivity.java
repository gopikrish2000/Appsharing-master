package com.rbricks.appsharing.concept.memoryefficient.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.memoryefficient.utils.BitmapMemoryCache;

import rx.Observable;

import static com.rbricks.appsharing.concept.notesapp.utils.RxApiUtil.build;

/**
 * 1. do processing in Background thread.
 * 2. cache bitmaps in Memory Cache using LruCache<String, Bitmap> & Disk Cache DiskLruCache
 * 3. U can store the LRU cache in a seperate fragment n make it setRetainInstance(true) in OnCreate
 * so onDestroy, onCreate willnot be called
 * but onCreateView , onAttach , onActivityCreated will still be called.
 */

public class BitmapMemoryActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_memory);
        mImageView = ((ImageView) findViewById(R.id.bitmap_iv));
        BitmapMemoryCache bitmapMemoryCache = BitmapMemoryCache.getInstance();
        Bitmap bitmapFromMemCache = bitmapMemoryCache.getBitmapFromMemCache(String.valueOf(R.drawable.ic_launcher));
        if (bitmapFromMemCache != null) {
            mImageView.setImageBitmap(bitmapFromMemCache);
        } else {
            loadMemoryEfficientImage(mImageView, R.drawable.ic_launcher, 100, 100);
        }
    }

    // Does processing in Background , thats it.
    public void loadMemoryEfficientImage(ImageView imageView, int resId, int reqWidth, int reqHeight) {
        decodeSampledBitmapFromResource(getResources(), resId, reqWidth, reqHeight).subscribe((bm) -> {
            imageView.setImageBitmap(bm);
            BitmapMemoryCache.getInstance().putBitmapToMemoryCache(resId + "", bm);
        });
    }

    public Observable<Bitmap> decodeSampledBitmapFromResource(Resources res, int resId,
                                                              int reqWidth, int reqHeight) {
        return build(Observable.create(subscriber -> {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            // First decode with inJustDecodeBounds=true to check dimensions without creating memory
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
            subscriber.onNext(bitmap);
        }));
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
