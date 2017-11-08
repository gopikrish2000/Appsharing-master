package com.rbricks.appsharing.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;

import com.rbricks.appsharing.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtils {

    private static final float BITMAP_SCALE = 0.75f;
    private static final float BLUR_RADIUS = 15f;
    private static final String TAG = BitmapUtils.class.getName();

    /**
     * Returns a BitmapDrawable object with R.drawable.user_profile_filler_background as background
     * and passed String stamped in the middle.
     *
     * @param gContext
     * @param gText
     * @return
     */
    public static BitmapDrawable writeInitialsOnBitmap(Context gContext, String gText) {
        gText = gText.toUpperCase();
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = null;
        /*Bitmap bitmap = BitmapFactory.decodeResource(AppSharingApplication.getInstance().getResources(), R.drawable
                .user_profile_filler_background);*/

        if (bitmap == null) {
            return null;
        }

        Bitmap.Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


        paint.setColor(Color.WHITE);

        paint.setTextSize((int) (40 * scale));
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
//        paint.setTypeface(AppSharingApplication.getInstance().getRegularTypeface());

        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawText(gText, x, y, paint);
        return new BitmapDrawable(resources, bitmap);
    }


    public static BitmapDrawable getRoundedBitmapOfDesiredColor
            (Context gContext, String text, String colorHashString) {

        int size = CommonUtils.dpToPx(30f);
        int halfSize = size / 2;

        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Canvas canvas = new Canvas(bitmap);

        Paint paintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBackground.setColor(Color.parseColor(colorHashString));

        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextSize((int) (16 * scale));
        paintText.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        paintText.setColor(Color.WHITE);
        Rect bounds = new Rect();
        paintText.getTextBounds(text, 0, text.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawCircle(halfSize, halfSize, halfSize, paintBackground);
        canvas.drawText(text, x, y, paintText);

        return new BitmapDrawable(resources, bitmap);

    }


    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        Bitmap resizedBitmap = getResizedBitmap(bitmap, 45, 45); //pick you default size

        return resizedBitmap;
    }


    private static Bitmap getResizedBitmap(Bitmap bitmap, int newHeight, int newWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    public static Bitmap scalingBitmap(Bitmap loadedImage, Resources resources) {
        if (loadedImage == null)
            return null;

        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int width;
        int height;
        if (loadedImage.getWidth() > loadedImage.getHeight()) {
            width = (int) ((float) 24 * displayMetrics.density);
            height = (loadedImage.getHeight() * width) / loadedImage.getWidth();
        } else {
            height = (int) ((float) 24 * displayMetrics.density);
            width = (loadedImage.getWidth() * height) / loadedImage.getHeight();
        }

        return Bitmap.createScaledBitmap(loadedImage, width, height, false);
    }

    public static Bitmap maskingOperation(Bitmap original, int maskResourceId, Resources resources) {
        Bitmap mask = BitmapFactory.decodeResource(resources, maskResourceId);
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        int startPointHeight = ((mask.getHeight() - original.getHeight()) / 2);
        int startPointWidth = ((mask.getWidth() - original.getWidth()) / 2);
        if (maskResourceId == R.drawable.ic_launcher) {
            startPointHeight -= (2 * resources.getDisplayMetrics().density);
            startPointWidth -= (2 * resources.getDisplayMetrics().density);
        }
        mCanvas.drawBitmap(original, startPointWidth, startPointHeight, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        return result;
    }

//    public static Bitmap getCircularPicassoBitmap(String companyName, String bitmapUrl, Context context) {
//        BitmapDrawable bmpDefaultDrawable;
//        Bitmap circularBitmap;
//        if (!TextUtils.isEmpty(companyName)) {
//            bmpDefaultDrawable = BitmapUtils.writeInitialsOnBitmap(context,
//                    String.valueOf(companyName.charAt(0)));
//        } else {
//            bmpDefaultDrawable = BitmapUtils.writeInitialsOnBitmap(context, "");
//        }
//        try {
//            circularBitmap = Picasso.with(context).load(bitmapUrl).transform(new RoundedTransformation()).placeholder(bmpDefaultDrawable)
//                    .get();
////            circularBitmap = Glide.with(context).load(bitmapUrl).placeholder(bmpDefaultDrawable).transform(new GlideRoundedTransformation(context)).asBitmap().into(-1, -1).get();
//        } catch (IOException e) {
//            circularBitmap = bmpDefaultDrawable.getBitmap();
//
//        }
//
//        return circularBitmap;
//    }

    public static BitmapDrawable getNotificationBitmapB
            (Context gContext, String text, String colorHashString, boolean unread) {

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Canvas canvas = new Canvas(bitmap);

        Paint paintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);

        try {
            paintBackground.setColor(Color.parseColor(colorHashString));
        } catch (Exception e) {

            paintBackground.setColor(Color.parseColor("#7B1FA2"));
        }


        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextSize((int) (25 * scale));
        //paintText.setShadowLayer(1f, 0f, 1f, Color.GRAY);
        if (unread) {
            paintText.setColor(Color.WHITE);
        } else {
            paintText.setColor(Color.BLACK);

        }
        Rect bounds = new Rect();
        paintText.getTextBounds(text, 0, text.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawRect(0, 100, 100, 0, paintBackground);

        canvas.drawText(text, x, y, paintText);

        return new BitmapDrawable(resources, bitmap);

    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242; // #FF0000 424242
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    public static Bitmap getCroppedBitmap2(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242; // #FF0000 424242
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final Rect des = new Rect(rect);
        des.top += 10;
        des.left += 10;
        des.right -= 10;
        des.bottom -= 10;


        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, des, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }


    public static Bitmap blur(Context context, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            RenderScript rs = RenderScript.create(context);
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            theIntrinsic.setRadius(BLUR_RADIUS);
            theIntrinsic.setInput(tmpIn);
            theIntrinsic.forEach(tmpOut);
            tmpOut.copyTo(outputBitmap);

            return outputBitmap;
        }
        return outputBitmap;
    }


    public static Bitmap downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
//            Logger.logException(TAG, "Unable to download image.", e);
        }
        return null;
    }
}