package com.rbricks.appsharing.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.ExifInterface;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.common.GoogleApiAvailability;
import com.rbricks.appsharing.concept.Application.AppSharingApplication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by gopi on 18/10/17.
 */

public class Utils {

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            }
            while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String getImageOrientation(String filePath) {
        ExifInterface exif;   // for storing attributes of file use ExifInterface
        try {
            exif = new ExifInterface(filePath);
            return exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        } catch (IOException e) {
            Logger.getLogger("").log(Level.SEVERE, "Utils Error while opening file", e);
            return null;
        }
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = AppSharingApplication.getInstance().getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = AppSharingApplication.getInstance().getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getNavigationBarHeight() {
        return getBottomNavBarHeight(AppSharingApplication.getInstance().getApplicationContext());
    }

    public static int getBottomNavBarHeight(Context context) {
        try {
            if (hasBottomNavBar(context)) {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Point realPoint = new Point();
                Display display = wm.getDefaultDisplay();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    display.getRealSize(realPoint);
                }
                DisplayMetrics metrics = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(metrics);

                return Math.abs(metrics.heightPixels - realPoint.y);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static boolean hasBottomNavBar(Context context) {
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Point realPoint = new Point();
            Display display = wm.getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(realPoint);
            }
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);

            return (metrics.heightPixels != realPoint.y) || (metrics.widthPixels != realPoint.x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Can return any of the following :
     * SUCCESS, SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED, SERVICE_DISABLED, SERVICE_INVALID
     *
     * @param context
     * @return
     */
    public static int getPlayServicesAvailableCode(Context context) {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
//		Logger.d(TAG, "Is PlayService Available ? : " + resultCode);
        return resultCode;
    }

    public static void setClipboardText(String str, Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", str);
        clipboard.setPrimaryClip(clip);
    }

    public static String getClipboardText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            String text = item.getText().toString();
            return text;
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();

        }
        return null;
    }

    public static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public static ArrayList<ActivityInfo> getistOfAllActivities(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);

            return new ArrayList<>(Arrays.asList(pi.activities));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public static int getKeyboardHeight() {
        int storedKeyboardHeight = AppSharingApplication.getInstance().getDataSafe(HikeConstants.SP_KEYBOARD_PORTRAIT_HEIGHT, 0);
        if (storedKeyboardHeight == 0) {
            storedKeyboardHeight = HikeSharedPreferenceUtil.getInstance().getDataSafe(HikeConstants.SP_KEYBOARD_HEIGHT, 0);
        }
        return storedKeyboardHeight;
    }*/

    /**
     * Used for preventing the cursor from being shown initially on the text box in touch screen devices. On touching the text box the cursor becomes visible
     *
     * @param editText
     */
    public static void hideCursor(final EditText editText, Resources resources) {
        if (resources.getConfiguration().keyboard == Configuration.KEYBOARD_NOKEYS || resources.getConfiguration().hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            editText.setCursorVisible(false);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        editText.setCursorVisible(true);
                    }
                    return false;
                }
            });
        }
    }

    public static void hideSoftKeyboard(Context context, View v) {
        if (v == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    // http://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
    public static void hideSoftKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftKeyboard(Context context, View v) {
        if (v == null) {
            return;
        }
        showSoftKeyboard(v, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void showSoftKeyboard(View v, int flags) {
        if (v == null) {
            return;
        }
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, flags);
    }



    public static void setBrightness(Activity activity, int brightness){
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
        WindowManager.LayoutParams layoutpars = activity.getWindow().getAttributes();
        layoutpars.screenBrightness = brightness / (float) 255;
        activity.getWindow().setAttributes(layoutpars);
    }

    public static String StringToMD5(String input)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			if (input.length() > 0)
				digest.update(input.getBytes(), 0, input.length());
			byte[] md5Bytes = digest.digest();
			return convertHashToString(md5Bytes);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static String convertHashToString(byte[] md5Bytes)
	{
		String returnVal = "";
		for (int i = 0; i < md5Bytes.length; i++)
		{
			returnVal += Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16).substring(1);
		}
		return returnVal;
	}

}
