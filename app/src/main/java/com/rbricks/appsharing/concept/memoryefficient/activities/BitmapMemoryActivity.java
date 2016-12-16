package com.rbricks.appsharing.concept.memoryefficient.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Build;
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

// check url https://developer.android.com/training/displaying-bitmaps/manage-memory.html#recycle
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

            // Calculate inSampleSize ; if value is 4 it gives image of width/4 & height/4.
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

   /* See basic code for reference.
    Set<SoftReference<Bitmap>> mReusableBitmaps;
    private LruCache<String, BitmapDrawable> mMemoryCache;

    private static void addInBitmapOptions(BitmapFactory.Options options,
                                           ImageCache cache) {
        // inBitmap only works with mutable bitmaps, so force the decoder to
        // return mutable bitmaps.
        options.inMutable = true;

        if (cache != null) {
            // Try to find a bitmap to use for inBitmap.
            Bitmap inBitmap = cache.getBitmapFromReusableSet(options);

            if (inBitmap != null) {
                // If a suitable bitmap has been found, set it as the value of
                // inBitmap.
                options.inBitmap = inBitmap;
            }
        }
    }

    // This method iterates through the reusable bitmaps, looking for one
// to use for inBitmap:
    protected Bitmap getBitmapFromReusableSet(BitmapFactory.Options options) {
        Bitmap bitmap = null;

        if (mReusableBitmaps != null && !mReusableBitmaps.isEmpty()) {
            synchronized (mReusableBitmaps) {
                final Iterator<SoftReference<Bitmap>> iterator
                        = mReusableBitmaps.iterator();
                Bitmap item;

                while (iterator.hasNext()) {
                    item = iterator.next().get();

                    if (null != item && item.isMutable()) {
                        // Check to see it the item can be used for inBitmap.
                        if (canUseForInBitmap(item, options)) {
                            bitmap = item;

                            // Remove from reusable set so it can't be used again.
                            iterator.remove();
                            break;
                        }
                    } else {
                        // Remove from the set if the reference has been cleared.
                        iterator.remove();
                    }
                }
            }
        }
        return bitmap;
    }*/

    // use existing bitmap only when sizes of both r equal & targetMemory required is less than equal to sourceBitmap
    static boolean canUseForInBitmap(
            Bitmap candidate, BitmapFactory.Options targetOptions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // From Android 4.4 (KitKat) onward we can re-use if the byte size of
            // the new bitmap is smaller than the reusable bitmap candidate
            // allocation byte count.
            int width = targetOptions.outWidth / targetOptions.inSampleSize;
            int height = targetOptions.outHeight / targetOptions.inSampleSize;
            int byteCount = width * height * getBytesPerPixel(candidate.getConfig());
            return byteCount <= candidate.getAllocationByteCount();
        }

        // On earlier versions, the dimensions must match exactly and the inSampleSize must be 1
        return candidate.getWidth() == targetOptions.outWidth
                && candidate.getHeight() == targetOptions.outHeight
                && targetOptions.inSampleSize == 1;
    }

    static int getBytesPerPixel(Config config) {
        if (config == Config.ARGB_8888) {
            return 4;
        } else if (config == Config.RGB_565) {
            return 2;
        } else if (config == Config.ARGB_4444) {
            return 2;
        } else if (config == Config.ALPHA_8) {
            return 1;
        }
        return 1;
    }

}
