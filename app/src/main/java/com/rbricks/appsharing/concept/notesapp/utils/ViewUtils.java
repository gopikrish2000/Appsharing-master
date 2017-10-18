package com.rbricks.appsharing.concept.notesapp.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.rbricks.appsharing.utils.CommonUtils;

/**
 * Created by gopikrishna on 12/11/16.
 */

public class ViewUtils {

    public static void enableVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public static void setGone(View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            enableVisibility(view, View.GONE);
        }
    }

    public static void setVisibleView(View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            enableVisibility(view, View.VISIBLE);
        }
    }

    public static void increaseClickAreaOfView(final View delegate, final float top, final float bottom, final float left, final float right) {
        delegate.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (delegate.getHeight() > 0) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        delegate.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        delegate.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    final View parent = (View) delegate.getParent();
                    final Rect r = new Rect();
                    delegate.getHitRect(r);
                    r.top -= CommonUtils.dpToPx(top);
                    r.bottom += CommonUtils.dpToPx(bottom);
                    r.left -= CommonUtils.dpToPx(left);
                    r.right += CommonUtils.dpToPx(right);
                    parent.setTouchDelegate(new TouchDelegate(r, delegate));

                }
            }
        });

    }

    public static boolean isDeviceX86Architecture() {
        String arch = System.getProperty("os.arch");
        String arc = arch.substring(0, 3).toUpperCase();
        if (!TextUtils.isEmpty(arc) && arc.equals("X86"))
            return true;
        return false;
    }

    public static void setBackgroundDrawableColor(View view, int color, int percentAlpha) {
        Drawable background = view.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(color);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(color);
        }
        background.setAlpha((int) (percentAlpha * 255 / 100));
    }

    public static void startImmersiveMode(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            final View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exitImmersiveMode(Activity activity) {
        if (activity != null) {
            try {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap viewToBitmap(View view) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
