package biz.melamart.www.cov19.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import biz.melamart.www.cov19.COVApplication;


public class ResourceUtils {

    private static int screenHeightInPx = 0, screenWidthInPx = 0;

    /**
     * Taking application context to get resources of the entire app
     */
    private static Resources getResources() {
        return getContext().getResources();
    }

    private static Context getContext() {
        return COVApplication.getContext();
    }

    /**
     * START
     * ##### Methods related to Dimensions
     * #####
     */

    /**
     * Method to convert pixel to density
     */
    public static int pixelsToDp(int pixels) {
        return (int) (pixels / getResources().getDisplayMetrics().density);
    }

    /**
     * Method to convert density to pixel
     */
    public static int dpToPixels(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    public static float spToPixels(int sp) {
        return (getResources().getDisplayMetrics().scaledDensity * sp);
    }

    /**
     * Get the device screen height in pixels.
     *
     * @return screenHeight in PX.
     */
    public static int getScreenHeight() {
        if (screenHeightInPx == 0) {
            WindowManager windowManager = (WindowManager) COVApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            screenHeightInPx = displayMetrics.heightPixels;
        }
        return screenHeightInPx;
    }

    /**
     * Get the device screen width in pixels.
     *
     * @return screenWidth in PX.
     */
    public static int getScreenWidth() {
        if (screenWidthInPx == 0) {
            WindowManager windowManager = (WindowManager) COVApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            screenWidthInPx = displayMetrics.widthPixels;

        }
        return screenWidthInPx;

    }

    public static int dpToPx(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, getResources().getDisplayMetrics());
    }

    public static int getImageWidth() {
        return ResourceUtils.getScreenWidth() - ResourceUtils.dpToPx(32);
    }

    public static int getImageHeight() {
        int height;
        if (ResourceUtils.getScreenHeight() <= 480) {
            height = ResourceUtils.getScreenHeight() / 4;
        } else if (ResourceUtils.getScreenHeight() <= 640 && ResourceUtils.getScreenHeight() > 480) {
            height = ResourceUtils.getScreenHeight() / 5;
        } else {
            height = ResourceUtils.getScreenHeight() / 6;

        }
        return height;
    }

}

